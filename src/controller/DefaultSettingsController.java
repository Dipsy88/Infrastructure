package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.DefaultSettings;

public class DefaultSettingsController {

	private File defaultSettingsFile;
	private DefaultSettings defaultSettings;
	
	public DefaultSettingsController(){
		defaultSettings = new DefaultSettings();	
	}

	public boolean createDefaultSettingsFile() throws IOException{
		defaultSettingsFile = new File("template/defaultsettings.txt");			
		// if file does not exists, then create it
		if (!defaultSettingsFile.exists()) {
			defaultSettingsFile.createNewFile();
			FileWriter fw = new FileWriter(defaultSettingsFile.getAbsoluteFile());
			BufferedWriter file = new BufferedWriter(fw);
			file.write("execution folder=");
			file.flush();
			file.close();
			return true;
		}else
			return false;

	}
	
	//To display
	public DefaultSettings readFile() throws IOException{
		createDefaultSettingsFile();
		try {
			//FileWriter changeNum= new FileWriter(fileName.getAbsoluteFile(),true);
			File source = new File("template/defaultsettings.txt");
			BufferedReader in = new BufferedReader(new FileReader(source));
			String line;
			while ((line = in.readLine()) != null){
				StringBuilder result = new StringBuilder();
				result.append(line);
			    //String[] details = line.split("\t");
			    if (line.contains("execution folder"))
			    	defaultSettings.setExecutionFolder(line.substring(line.lastIndexOf("=") + 1));
			}	
			 in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultSettings;
	}
	
	
	
	//Write and read the same file
		public void writeFile(DefaultSettings defaultSettings) throws Exception{
			createDefaultSettingsFile();

			List<String> lines = new ArrayList<String>();
		    String line = null;
			try {
				BufferedReader in = new BufferedReader(new FileReader(defaultSettingsFile));

				while ((line = in.readLine()) != null){					
					if (line.contains("execution folder"))					
						line = line.replaceAll("=.*", "=" + defaultSettings.getExecutionFolder());

				    lines.add(line);
				    lines.add("\n");				    
				}	 
				in.close();
				
				FileWriter changeFile= new FileWriter(defaultSettingsFile.getAbsoluteFile(),false);
				BufferedWriter out = new BufferedWriter(changeFile);
				for(String s : lines)
		        	 out.write(s);
				out.flush();
		        out.close();
				
		        changeFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    
			
		}
		
		//Write and read the same file
		public void clearFile(DefaultSettings defaultSettings) throws Exception{
			if (!createDefaultSettingsFile()){

				List<String> lines = new ArrayList<String>();
				String line = null;
					
				try {
					BufferedReader in = new BufferedReader(new FileReader(defaultSettingsFile));
	
					while ((line = in.readLine()) != null){					
						if (line.contains("execution folder"))					
							line = line.replaceAll("=.*", "=");
	
						    lines.add(line);
						    lines.add("\n");				    
						}	 
						in.close();
						
						FileWriter changeFile= new FileWriter(defaultSettingsFile.getAbsoluteFile(),false);
						BufferedWriter out = new BufferedWriter(changeFile);
						for(String s : lines)
				        	 out.write(s);
						out.flush();
				        out.close();
							
				        changeFile.close();
					} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
					}		    
						
				}
		}
	
}
