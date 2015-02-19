package model;

import java.io.BufferedReader;
import java.io.File;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import controller.AbelController;


public class SSHManager {

	 private JSch jschSSHChannel;
	  private String strUserName;
	  private String strConnectionIP;
	  private int intConnectionPort;
	  private String strPassword;
	  private Session sesConnection;
	  private Session sesConnection2;
	  private int intTimeOut;

	  private void doCommonConstructorActions(String userName, 
		       String password, String connectionIP, String knownHostsFileName){
		     jschSSHChannel = new JSch();

		     try
		     {
		        jschSSHChannel.setKnownHosts(knownHostsFileName);
		     }
		     catch(JSchException jschX)
		     {
		    	 jschX.printStackTrace();
		     }

		     strUserName = userName;
		     strPassword = password;
		     strConnectionIP = connectionIP;
		  }
	  
	  private void doCommonConstructorActions(String userName, 
		       String password, String connectionIP)
		  {
		     jschSSHChannel = new JSch();

		    
		     strUserName = userName;
		     strPassword = password;
		     strConnectionIP = connectionIP;
		  }
	
	  public SSHManager(String userName, String password, 
			     String connectionIP){
			     doCommonConstructorActions(userName, password, 
			                connectionIP);
			     intConnectionPort = 22;
			     intTimeOut = 60000;
			  }
	  
	  
	  public SSHManager(String userName, String password, 
		     String connectionIP, String knownHostsFileName){
		     doCommonConstructorActions(userName, password, 
		                connectionIP, knownHostsFileName);
		     intConnectionPort = 22;
		     intTimeOut = 60000;
		  }
	
	  public SSHManager(String userName, String password, String connectionIP, 
		     String knownHostsFileName, int connectionPort){
		     doCommonConstructorActions(userName, password, connectionIP, 
		        knownHostsFileName);
		     intConnectionPort = connectionPort;
		     intTimeOut = 60000;
		  }
	
	  public SSHManager(String userName, String password, String connectionIP, 
		      String knownHostsFileName, int connectionPort, int timeOutMilliseconds){
		     doCommonConstructorActions(userName, password, connectionIP, 
		         knownHostsFileName);
		     intConnectionPort = connectionPort;
		     intTimeOut = timeOutMilliseconds;
		  }

	  public String connect(){
		     String errorMessage = null;
		   
		     try
		     {
		        sesConnection = jschSSHChannel.getSession(strUserName, 
		            strConnectionIP, intConnectionPort);
		        sesConnection.setPassword(strPassword);
		        // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
		        // sesConnection.setConfig("StrictHostKeyChecking", "no");
		        
		        sesConnection.setConfig("StrictHostKeyChecking", "no");
		        sesConnection.connect(intTimeOut);
		        
		     }
		     catch(JSchException jschX)
		     {
		        errorMessage = jschX.getMessage();
		     }

		     return errorMessage;
		  }
	  
	  public String connectNorStore(String userName2, String password2, String connectionIP2){
	     String errorMessage = null;	   
	     try
	     {
	        sesConnection2 = jschSSHChannel.getSession(userName2, 
	        		connectionIP2, intConnectionPort);
	        sesConnection2.setPassword(password2);
	        // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
	        // sesConnection.setConfig("StrictHostKeyChecking", "no");
	        
	        sesConnection2.setConfig("StrictHostKeyChecking", "no");
	        sesConnection2.connect(intTimeOut);
	        
	     }
	     catch(JSchException jschX)
	     {
	        errorMessage = jschX.getMessage();
	     }

	     return errorMessage;
	  }

	  public void execute(File localDir, String parent, String dirName) throws Exception{
//		  Session session = null;
//		  JSch jsch = new JSch();
//		  session = jsch.getSession("dipeshpr", "129.240.189.141", 22);
//		  session.setPassword("dipsY.77");
//		  
//		  //add to safe host list later
//		  session.setConfig("StrictHostKeyChecking", "no");
//		  session.connect();
		  try {
			Channel channel = sesConnection.openChannel("sftp");
			channel.connect();
			
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			//sftpChannel.put("C:\\Users\\Dipesh.DIPSYPC\\infrastructure\\jobTemp", sftpChannel.pwd()+"/infra/");

			//For copying from remote server with java
			//http://stackoverflow.com/questions/18831238/copy-all-files-with-specific-extension-from-remote-server-with-java
			
		//	ls(String path, ChannelSftp.LsEntrySelector selector)
			
		//	sftpChannel.mkdir(destDir);
			sftpChannel.cd(parent);
			sftpChannel.mkdir(dirName);
			
			//Path dest = Paths.get(sftpChannel.pwd()+"/"+"infra/");
			String dest =sftpChannel.pwd();

			copyFiles(sftpChannel, localDir, dest, dirName);
			
			sftpChannel.disconnect();
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public long sendJob(String appFolder, String task) throws SftpException, IOException, InterruptedException{
		  long jobId=0;
		  try {
				
//			  Channel channel = sesConnection.openChannel("exec");
//			 
//			  ChannelExec ch = (ChannelExec) channel;
//			  ch.setCommand("cd "+appFolder+";"+"cd "+task+";"+ "sbatch jobTemp");
//			  ch.connect();
//			  
//			  
//			  BufferedReader in=new BufferedReader(new InputStreamReader(ch.getInputStream()));
//			   String msg=null;
//			   while((msg=in.readLine())!=null){
//			    System.out.println(msg);
//			   }			   
//				ch.disconnect();
				ChannelShell channelShell = (ChannelShell) sesConnection.openChannel("shell");
				
				OutputStream ops = channelShell.getOutputStream();
		        PrintStream ps = new PrintStream(ops, true);
		        channelShell.connect();
		        
		        ps.println("cd "+appFolder);
		        ps.println("cd "+task);
		        ps.println("sbatch jobTemp");
		        InputStream in=channelShell.getInputStream();
		        byte[] bt=new byte[1024];

		         while(true)
		         {

		         while(in.available()>0)
		         {
		         int i=in.read(bt, 0, 1024);
		         if(i<0)
		          break;
		            String str=new String(bt, 0, i);
		          //displays the output of the command executed.
		           // System.out.print(str);
		            if (str.contains("batch job")){
		            	String line = str;
				    	String[] details = line.split("\n");
				    	
				    	for (String s:details){
				    		  if (s.contains("Submitted batch job")){
				    			 
				    			  String[] findingId = s.split(" ");
				    			  findingId[3] = findingId[3].replaceAll("\\r|\\n", "");

				    			  findingId[3].trim();
				    			  System.out.println(Long.valueOf(findingId[3]).longValue());
				    			  jobId = Long.valueOf(findingId[3]).longValue();
				    		  }
				    	}
		            }

		         }
		         if(channelShell.isClosed())
		         {

		             break;
		        }
		         Thread.sleep(1000);
		        
		        channelShell.disconnect();
		         }
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return jobId;
	  }
	  
	  //Copy files to Abel
	  public void copyFiles(ChannelSftp channelSftp, File sourcePath, String destination, String destDir) throws Exception{	  
		  File[] files = iterateFiles(sourcePath);
		  
		  int dirLevel =0;
			//File[] files = localDirectory.listFiles();			
			for (File file : files){
				String filename = file.getName();
				String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
				
				String git = "git";
				
				if (!extension.equals(git)) {
					if (file.isDirectory()){	
						dirLevel++;
						if (dirLevel>1){					
							channelSftp.cd("..");
						}
						File newSourcePath = new File(sourcePath+"/"+file.getName());
						//channelSftp.getHome();	
	
						channelSftp.cd(destDir);
						System.out.println(file.getName());
						channelSftp.mkdir(file.getName());
						
						String dest = destination+"/"+destDir;
						destDir = file.getName();				
	
						copyFiles(channelSftp, newSourcePath,dest, destDir);	
						
						channelSftp.getHome();	
						destDir = AbelController.getDirName();
						}else		{		
						
							channelSftp.put(file.getAbsolutePath(), destination+ "/"+ destDir + "/");
						}	
					}	
				}
			}
	  
	  //check the files after execution
	  public boolean checkFile(String destParent, String task) throws SftpException, JSchException, IOException{
		  boolean found = true;
		  ChannelShell channelShell = (ChannelShell) sesConnection.openChannel("shell");
			
		  OutputStream ops = channelShell.getOutputStream();
	      PrintStream ps = new PrintStream(ops, true);
	     
	      channelShell.connect();
	      ps.println("cd "+destParent);
	      ps.println("ls "+task);
		  
		  InputStream in=channelShell.getInputStream();
		  byte[] bt=new byte[1024];

	      while(true)
	      {
	    	  while(in.available()>0)
	    	  {
	    		  int i=in.read(bt, 0, 1024);
	    		  if(i<0)
	    			  break;
	    		  String str=new String(bt, 0, i);
	          //displays the output of the command executed.
	          //  System.out.print(str);
	            if (str.contains("No such file or directory"))
	            		found = false;
	    	  }
	    	  if(channelShell.isClosed())
	    	  {
		             break;
		       }
	    	  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
		        channelShell.disconnect();
		  	}
	      return found;
	  }
	  
	  public void copyFileToHome(String root, String fileName) throws Exception{
		try {
			Channel channelNotur = sesConnection.openChannel("sftp");
			
			Channel channelNorStore = sesConnection2.openChannel("sftp");
			
			channelNotur.connect();
			channelNorStore.connect();
			
			ChannelSftp uploadChannel = null;
		    ChannelSftp downloadChannel = null;
			
		    uploadChannel = (ChannelSftp) channelNotur;
	        downloadChannel = (ChannelSftp) channelNorStore;
	
			InputStream inputStream = uploadChannel.get(root+"/" + fileName);
			
			downloadChannel.put(inputStream,fileName);		
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	    
	  public void deleteFiles(ChannelSftp sftp, String oldestBackup) throws SftpException{
		  if (isDir(sftp, oldestBackup)) {
		        sftp.cd(oldestBackup);
		        //System.out.println(sftp.pwd());
		        Vector < LsEntry > entries = sftp.ls(".");
		        for (LsEntry entry: entries) {
		        	
		        	//Temporary hack to delete files
		        	if (entry.getFilename().equals(".") ||  entry.getFilename().equals(".."))
		        		continue;
		            deleteFiles(sftp, entry.getFilename());
		        }
		        sftp.cd("..");
		        sftp.rmdir(oldestBackup);
		    } else {
		        sftp.rm(oldestBackup);
		    }
		  
	  }
	  
	  //for deleting all the files at parent folder in the remote server
	  
	  public ArrayList<String> getDir(String parent) throws JSchException, IOException{
		  ArrayList<String> files = new  ArrayList<String>();
		  Channel channel = sesConnection.openChannel("exec");
		 
		  ChannelExec ch = (ChannelExec) channel;
		  ch.setCommand("cd "+parent+";"+"ls");
		  
		  ch.connect();

		  BufferedReader in=new BufferedReader(new InputStreamReader(ch.getInputStream()));
		   String msg=null;
		   while((msg=in.readLine())!=null){
			   files.add(msg);
		   }

	      return files;
	  }
  
	  private static boolean isDir(ChannelSftp sftp, String entry) throws SftpException {
		    return sftp.stat(entry).isDir();
		}
	  
	  public void delete(String parent, String name) throws SftpException{	
		  try {
				Channel channel = sesConnection.openChannel("sftp");
				channel.connect();
				
				ChannelSftp sftpChannel = (ChannelSftp) channel;
				
				sftpChannel.cd(parent);
				SftpATTRS attrs=null;
				try {
				    attrs = sftpChannel.stat(name); // For checking if the directory exists
				    deleteFiles(sftpChannel, name);
				} catch (Exception e) {
				    System.out.println(name + " not found");
				}	
//				System.out.println("Path is " + sftpChannel.pwd());
		  } catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	  }

	  public File[] iterateFiles(File dir){
		  File[] dirFiles = dir.listFiles();  		  
		  return dirFiles;	  
	  }
	  
	
	  public void close(){
	     sesConnection.disconnect();
	  }
	  
	  public void close2(){
	     sesConnection2.disconnect();
	  }

	  public boolean checkJobCompleted(String jobId) throws IOException, JSchException{
		  boolean completed= true;
		  int counter =0;
		  ChannelShell channelShell = (ChannelShell) sesConnection.openChannel("shell");
			
		  OutputStream ops = channelShell.getOutputStream();
	      PrintStream ps = new PrintStream(ops, true);
	     
	      channelShell.connect();
	      ps.println("squeue -j "+jobId);

		  InputStream in=channelShell.getInputStream();
		  byte[] bt=new byte[1024];

	      while(true)
	      {

	    	  while(in.available()>0)
	    	  {
	    		  int i=in.read(bt, 0, 1024);
	    		  if(i<0)
	    			  break;
	    		  String str=new String(bt, 0, i);
	          //displays the output of the command executed.
	         //   System.out.print(str);
	            
	            String line = str;
		    	String[] details = line.split("\n");
		    	
		    	for (String s:details){
		    		  if (s.contains(jobId)){		    	
		    			  counter++;
		    		  } 
		    	}
	    	  }
	    	  if(channelShell.isClosed()){
		             break;
		       }
	    	  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  
		        channelShell.disconnect();
		  	}
	      if (counter>=3)
	    	  completed= false;
	      System.out.println("Completed is " + completed);
	      return completed;
	  }
	  
	//Get all results from NorStore
	  public ArrayList<String> getFiles(String parent) throws JSchException, IOException{
		  ArrayList<String> files = new  ArrayList<String>();
		  Channel channel = sesConnection2.openChannel("exec");
		 
		  ChannelExec ch = (ChannelExec) channel;
		  ch.setCommand("cd "+parent+";"+"ls");
		  
		  ch.connect();

		  BufferedReader in=new BufferedReader(new InputStreamReader(ch.getInputStream()));
		  String msg=null;
		  while((msg=in.readLine())!=null){
			   files.add(msg);
		   }

	      return files;
	  }
	  
	  //Read result file in Norstore
	  public ArrayList<String> readFile(String parent, String fileName) throws JSchException, IOException{
		  ArrayList<String> result = new ArrayList<String>();
		  Channel channel = sesConnection2.openChannel("exec");
			 
		  ChannelExec ch = (ChannelExec) channel;
		  ch.setCommand("cat " + fileName);
		  
		  ch.connect();
		  BufferedReader in=new BufferedReader(new InputStreamReader(ch.getInputStream()));
		 
		  String msg= null;
		  while((msg=in.readLine())!=null){
				result.add(msg);
			}	
		  return result;
	  }  
}
