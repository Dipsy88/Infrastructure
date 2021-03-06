package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import main.ExecuteCase;

public class ExecutePageController {

	
	private ExecuteCase executeCase;

	public ExecutePageController(){
		executeCase = new ExecuteCase();
	
	}
	
	public void createJar() throws IOException{		
		if (!AbelController.isDirCreated()){
			AbelController abelController = new AbelController();
			try {			
				abelController.copyJob("App");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		executeCase.createJarFile(AbelController.getDirPath());
		executeCase.extractJar(AbelController.getDirPath());
	}
	
	public void copyProgram(String executionFolder) throws IOException{
		String lastWord = executionFolder.substring(executionFolder.lastIndexOf("/")+1);
		File source = new File(executionFolder);
		String destTemp = AbelController.getDirPath().toString()+"/"+lastWord;
		File dest = new File(destTemp);
		
		copyDirectory(source, dest);	
	}
	
	
	public static final void copyDirectory( File source, File destination ) throws IOException {
	    if( !source.isDirectory() ) {
	      throw new IllegalArgumentException( "Source (" + source.getPath() + ") must be a directory." );
	    }
	    
	    if( !source.exists() ) {
	      throw new IllegalArgumentException( "Source directory (" + source.getPath() + ") doesn't exist." );
	    }

	    boolean check= destination.mkdirs();
	    File[] files = source.listFiles();
	    
	    for( File file : files ) {
	      if( file.isDirectory() ) {
	        copyDirectory( file, new File( destination, file.getName() ) );
	      } else {
	        copyFile( file, new File( destination, file.getName() ) );
	      }
	    }
	  }
	
	 public static final void copyFile( File source, File destination ) throws IOException {
		    FileChannel sourceChannel = new FileInputStream( source ).getChannel();
		    FileChannel targetChannel = new FileOutputStream( destination ).getChannel();
		    sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
		    sourceChannel.close();
		    targetChannel.close();
	}
	
}
