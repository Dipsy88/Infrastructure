package controller;

import java.io.File;

import model.SSHManager;

public class RemoteController {
	
	String userName = "dipeshpr";
	String password = "dipsY.77";
	String connectionIP ="129.240.189.141";	
	
	public void sendFiles(String parent, String dirName ){
		SSHManager instance = new SSHManager(userName, password, connectionIP);
		String errorMessage = instance.connect();
		File localDir= new File("temp/jar/"+dirName);
		if (errorMessage==null){
			try {
				instance.execute(localDir, parent, dirName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
			instance.close();
		}
	}
	
	public void executeJob(String appFolder, String task){
		SSHManager instance = new SSHManager(userName, password, connectionIP);
		String errorMessage = instance.connect();
		if (errorMessage==null){
			try {
				instance.sendJob(appFolder, task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
			instance.close();
		}
	}
	
	public void delete(String parent, String dirName){
		SSHManager instance = new SSHManager(userName, password, connectionIP);
		String errorMessage = instance.connect();
		if (errorMessage==null){
			try {
				instance.delete(parent,dirName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			instance.close();
		}
	}
	
}
