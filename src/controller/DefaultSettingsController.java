package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultSettingsController {

	private File defaultSettingsFile;
	
	public void createExecutionFile() throws IOException{
		defaultSettingsFile = new File("temp/execution/executed.txt");			
		// if file does not exists, then create it
		if (!defaultSettingsFile.exists()) {
			defaultSettingsFile.createNewFile();
			FileWriter fw = new FileWriter(defaultSettingsFile.getAbsoluteFile());
		}

	}
}
