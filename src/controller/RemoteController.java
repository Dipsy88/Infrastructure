package controller;

import java.io.File;
import java.util.ArrayList;

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
	String passwordNorStore = "Dipesh@77";
	String connectionIPNorStore = "129.240.188.214";	


	String errorMessageNorStore;
	private NorStoreController norStoreController;
	
	
	public void connectBoth(){
		instance= new SSHManager(userName, password, connectionIP);
		this.errorMessageNorStore = instance.connectNorStore(userNameNorStore, passwordNorStore, connectionIPNorStore );
		this.errorMessage = instance.connect();

	}
	
	public void connect(){
		instance = new SSHManager(userName, password, connectionIP);
		this.errorMessage = instance.connect();
		
	}
	
	
	public void sendFiles(String parent, String dirName ){
		connect();
		File localDir= new File("temp/jar/"+dirName);
		if (errorMessage==null && errorMessageNorStore == null){
			try {
				instance.execute(localDir, parent, dirName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
			instance.close();
		}
	}
	
	public long executeJob(String appFolder, String task){
		long jobId=0;
		connect();
		if (errorMessage==null){
			try {
				jobId= instance.sendJob(appFolder, task);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			instance.close();
		
		}
		return jobId;
	}
	
	//delete individual folder
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
	
	//delete all files
	public void deleteAllFiles(String parent){
		connect();
		ArrayList<String> dirs = new ArrayList<String>();
		if (errorMessage==null){
			try {
				dirs= instance.getDir(parent);
				for (String s : dirs){
					instance.delete(parent, s);
					
				}
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			instance.close();
		}
	}
	
	public void copyFileNorStore(String root, String fileName){
		connectBoth();
		if (errorMessage==null && errorMessageNorStore==null){
			try {
				
				instance.copyFileToHome(root, fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			instance.close();
		}
		
	}
	
	public boolean checkFile(String destParent, String task){
		connect();	
		boolean found= false;
		if (errorMessage==null){
			try {
				found=instance.checkFile(destParent, task);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		}
		return found;
	}
	
	public boolean checkJobCompleted (String jobId){
		connect();	
		boolean found= false;
		if (errorMessage==null){
			try {
				found=instance.checkJobCompleted(jobId);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		}
		return found;
		
	}
	

	
}
