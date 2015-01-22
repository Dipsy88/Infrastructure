package model;

import java.io.BufferedReader;
import java.io.File;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
	  private int intTimeOut;
	  

	
	  private void doCommonConstructorActions(String userName, 
		       String password, String connectionIP, String knownHostsFileName)
		  {
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
			     String connectionIP)
			  {
			     doCommonConstructorActions(userName, password, 
			                connectionIP);
			     intConnectionPort = 22;
			     intTimeOut = 60000;
			  }
	  
	  
	  public SSHManager(String userName, String password, 
		     String connectionIP, String knownHostsFileName)
		  {
		     doCommonConstructorActions(userName, password, 
		                connectionIP, knownHostsFileName);
		     intConnectionPort = 22;
		     intTimeOut = 60000;
		  }
	
	  public SSHManager(String userName, String password, String connectionIP, 
		     String knownHostsFileName, int connectionPort)
		  {
		     doCommonConstructorActions(userName, password, connectionIP, 
		        knownHostsFileName);
		     intConnectionPort = connectionPort;
		     intTimeOut = 60000;
		  }
	
	  public SSHManager(String userName, String password, String connectionIP, 
		      String knownHostsFileName, int connectionPort, int timeOutMilliseconds)
		  {
		     doCommonConstructorActions(userName, password, connectionIP, 
		         knownHostsFileName);
		     intConnectionPort = connectionPort;
		     intTimeOut = timeOutMilliseconds;
		  }

	  public String connect()
		  {
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
	  
	  public void sendJob(String appFolder, String task) throws SftpException, IOException, InterruptedException{
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
//			   
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
		            System.out.print(str);


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
	  }
	  public void copyFiles(ChannelSftp channelSftp, File sourcePath, String destination, String destDir) throws Exception{	  
		  File[] files = iterateFiles(sourcePath);
		  
		  int dirLevel =0;
			//File[] files = localDirectory.listFiles();			
			for (File file : files){
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
//				sftpChannel.rmdir("a");
		  } catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	  }

	  public File[] iterateFiles(File dir){
		  File[] dirFiles = dir.listFiles();  		  
		  return dirFiles;	  
	  }
	  
	
	  public void close()
	  {
	     sesConnection.disconnect();
	  }

	  
}
