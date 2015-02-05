package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.SSHManager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

public class NorStoreController {

			private List<String> taskList = new ArrayList<String>();
			private RemoteController remoteController;
			private int size;
			private  ArrayList<String> files;
			
			public ArrayList<String> getFiles() {
				return files;
			}

			public void setFiles(ArrayList<String> files) {
				this.files = files;
			}

			public void initComponents(){
				remoteController = new RemoteController();
				files = new ArrayList<String>();
			}
			
			public NorStoreController(){
				initComponents();			
			}
			
			public List<String> getTaskList() {
				return taskList;
			}
		
			public void setTaskList(List<String> taskList) {
				this.taskList = taskList;
			}
	
			//copy files to Norstore
			public void copyFiles() throws Exception{
				checkTasks();
				String [] taskArray = new String [size];
				taskArray = taskList.toArray(taskArray);
				for (int i= 0;i<size;i++){
					boolean found = remoteController.checkFile("AppResult", taskArray[i]+".txt");
				
					if (found == true)
						remoteController.copyFileNorStore("AppResult", taskArray[i]+".txt");	
				}
			}
			
			//Get all results
			  public void getFiles(String parent) throws JSchException, IOException{
				  remoteController.connectNorStore();
				 
					if (remoteController.errorMessageNorStore==null){
						try {
							files= remoteController.instance.getFiles(parent);
							
				
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						remoteController.instance.close2();
					}
		
			  }
			
			
			
			//Write and read the same file
			public void checkTasks() throws Exception{		
				File file = new File("temp/execution/executed.txt");
				List<String> lines = new ArrayList<String>();
			    String line = null;
			    String[] data = new String[100];
			    
			    long currentTime = System.currentTimeMillis()/1000;
			    
				try {
					//BufferedReader in = new BufferedReader(new FileReader("template/job.txt"));
					BufferedReader in = new BufferedReader(new FileReader(file));								 
					while ((line = in.readLine()) != null){

						if (line.contains("Filename")){
							lines.add(line);
							lines.add("\n");
							
						}else if (line.isEmpty())
							continue;
						
						else{
						
					    //String[] details = line.split("\t");
	
							String[] var = line.split("\t");
					
							long taskTime = Long.parseLong(var[0]);
							if (taskTime<=currentTime ){
								boolean completed= remoteController.checkJobCompleted(var[2]);
								if (completed)
									taskList.add(var[1]);
								else {
									lines.add(line);
									lines.add("\n");
								}
							}
							else{
								lines.add(line);
								lines.add("\n");
							}
	
						}
					}	 
					in.close();
					
					FileWriter changeJob= new FileWriter(file.getAbsoluteFile(),false);
					BufferedWriter out = new BufferedWriter(changeJob);
					for(String s : lines)
			        	 out.write(s);
					out.flush();
			        out.close();
					
					changeJob.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		    				
				size = taskList.size();				
			}
			
			
			
			

}
