package controller;

import java.io.File;

import com.jcraft.jsch.ChannelSftp;

import model.SSHManager;

public class RemoteController {
	//Abel
	String userName = "dipeshpr";
	String password = "dipsY.77";
	String connectionIP ="129.240.189.141";	
	SSHManager instance;
	String errorMessage;
	
	//Norstore
	String userNameNorStore= "dipesh";
	String passwordNorStore = "dipsY.77";
	SSHManager instanceNorStore;
	String errorMessageNorStore;
	
	
	public void connectNorStore(){
		instanceNorStore = new SSHManager(userName, password, connectionIP);
		this.errorMessage = instanceNorStore.connectNorStore(userNameNorStore, passwordNorStore);

	}
	
	public void connect(){
		instance = new SSHManager(userName, password, connectionIP);
		this.errorMessage = instance.connect();
		
	}
	
	
	public void sendFiles(String parent, String dirName ){
		connect();
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
		connect();
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
		connect();
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
	
	public void copyFileNorStore(){
		connectNorStore();
		connect();
		if (errorMessage==null){
			try {
				instance.copyFileToHome();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			instance.close();
		}
		
	}
	
	public void checkFile(String destParent, String task){
		connect();	
		if (errorMessage==null){
			try {
				instance.checkFile(destParent, task);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		}
	}

	
}
