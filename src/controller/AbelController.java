package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import view.JobPage;

public class AbelController {

	private static boolean dirCreated = false;
	public static void setDirCreated(boolean dirCreated) {
		AbelController.dirCreated = dirCreated;
	}

	public static boolean isDirCreated() {
		return dirCreated;
	}



	private File executionFile;
	private boolean defaultFile = false;
	private int dirNumber;
	private static File dirPath;
	private static String dirName;
	public static String getDirName() {
		return dirName;
	}

	private static double executionTime;
	
	public static void setDirName(String dirName) {
		AbelController.dirName = dirName;
	}

	public static File getDirPath() {
		return dirPath;
	}

	public static void setDirName(File dirPath) {
		AbelController.dirPath = dirPath;
	}

	private File jobCount;
	private static File fileName;
	public static File getFileName() {
		return fileName;
	}
	
	public static double getExecutionTime(){
		return executionTime;
	}

	public static void setFileName(File fileName) {
		AbelController.fileName = fileName;
	}

	private Job job;
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	private JobPage jobPage;
	
	
	public AbelController(){
		jobCount = new File("template/count.txt");
		job = new Job();
		try {
			dirNumber=readDirNum();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dirPath= new File("temp/jar/task"+dirNumber);
		dirName = "task"+dirNumber;
		
		
	}
	
	public void copyJob(int dirNumber) throws Exception{
			
		createDir(dirPath);

		File source = new File("template/job");
		File dest = new File("temp/jar/task"+dirNumber+"/jobTemp");
		
		if (dest.exists()) {
			dest.delete();
		}
		
		try {
			Files.copy(source.toPath(), dest.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkFile() throws Exception{
		File dirPath = new File("temp/jar/task"+dirNumber);	
		if (!dirPath.exists())
			createDir(dirPath);
	
		fileName = new File("temp/jar/task"+dirNumber+"/jobTemp");
		

		// if file does not exists, then create it
		if (fileName.exists())			
			return false;
		else
			return true;

		
	}
	
	private void createDir(File dirName) throws Exception{
		if (!dirName.exists())
			dirName.mkdir();
		
	}
	
	public void run(){
		jobPage = new JobPage();

	}
	

	
	public int readDirNum() throws NumberFormatException, IOException{
		int counter=0;
		try{
			BufferedReader in = new BufferedReader(new FileReader(jobCount));
			String line;
			while ((line = in.readLine()) != null){
				String line2 = line;
		    	String[] details = line.split(" ");
		    	counter = Integer.parseInt(details[1]);
			}	
			 in.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return counter;	
	}
	
	public void changeDirNum() throws NumberFormatException, IOException{
		FileWriter changeJob= new FileWriter(jobCount.getAbsoluteFile(),false);
		System.out.println(dirNumber);
		dirNumber++;
		changeJob.write("counter: "+ dirNumber);

		changeJob.close();
			
	}
	
	public Job readJob() throws Exception{
		try {
			//FileWriter changeNum= new FileWriter(fileName.getAbsoluteFile(),true);
			File source = new File("template/defaultJob");
			BufferedReader in = new BufferedReader(new FileReader(source));
			String line;
			while ((line = in.readLine()) != null){
				StringBuilder result = new StringBuilder();
				result.append(line);
			    //String[] details = line.split("\t");
			    if (line.contains("job-name"))
			    	job.setJobName(line.substring(line.lastIndexOf("=") + 1));
			    
			    else if (line.contains("account"))
			    	job.setAccount(line.substring(line.lastIndexOf("=") + 1));
			    else if (line.contains("time")){
			    	String time = line.substring(line.lastIndexOf("=") + 1);
			    	String[] timeDetails = time.split(":");
			    	job.setExecutionTimeHour(Integer.parseInt(timeDetails[0]));
			    	job.setExecutionTimeMinute(Integer.parseInt(timeDetails[1]));
			    	job.setExecutionTimeSecond(Integer.parseInt(timeDetails[2]));
			    }	
			    else if (line.contains("mem-per-cpu")){
			    	String memory = line.substring(line.lastIndexOf("=") + 1);
			    	String[] memoryDetails = memory.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
			    	job.setMemory(Integer.parseInt(memoryDetails[0]));
			    	job.setMemoryUnit(memoryDetails[1]);
			    	
			    }
			 
			}	
			 in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return job;
	}
	
	//Read and write in different files
//	public void writeJob(String projectFolder) throws Exception{
//		if (!defaultFile)
//			changeDirNum();
//		//File dirName = new File("temp/jar/task"+dirNumber);
//		dirName = "task"+dirNumber;
//		boolean check = checkFile();
//		
//		fileName.createNewFile();
//		FileWriter changeJob= new FileWriter(fileName.getAbsoluteFile(),true);
//		
//		try {
//			//BufferedReader in = new BufferedReader(new FileReader("template/job.txt"));
//			BufferedReader in = new BufferedReader(new FileReader(fileName));
//			String line;
//			
//			while ((line = in.readLine()) != null){
//				StringBuilder result = new StringBuilder();
//				result.append(line);
//			    //String[] details = line.split("\t");
//   	
//			    if (line.contains("job-name"))
//			    	result.append(job.getJobName());
//			    else if (line.contains("account"))
//			    	result.append(job.getAccount());
//			    else if (line.contains("time"))
//			    	result.append((job.getExecutionTimeHour())+ ":" + job.getExecutionTimeMinute() + ":" + job.getExecutionTimeSecond()); 
//			    else if (line.contains("mem-per-cpu"))
//			    	result.append(job.getMemory()+ job.getMemoryUnit());
//			    else if (line.contains("cp -R /usit/abel/"))
//			    	result.replace(0, line.length(), "cp -R /usit/abel/u1/dipeshpr/"+projectFolder +"/"+dirName +  " $SCRATCH");
//			    else if (line.contains("cd task"))
//			    	result.replace(0, line.length(), "cd "+dirName);
//			    else if (line.contains("cp -R Abdel /usit/abel/"))
//			    	result.replace(0, line.length(), "cp -R "+ dirName + " /usit/abel/u1/dipeshpr/"+projectFolder +"/"+dirName +"/result.txt");
//			    
//			    
//			   result.append("\n") ;	
//			   changeJob.write(result.toString());
//			   changeJob.flush();    	
//			}	 
//			changeJob.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		    
//		
//	}
	
	//to have file immediately after pressing execute
	public void copyJob(String projectFolder) throws Exception{
		if (!defaultFile)
			changeDirNum();
		//File dirName = new File("temp/jar/task"+dirNumber);
		dirName = "task"+dirNumber;
		
		dirPath = new File("temp/jar/task"+dirNumber);	
		if (!dirPath.exists())
			createDir(dirPath);

		

		
		File source = new File("template/defaultJob");
		fileName = new File("temp/jar/"+dirName+"/jobTemp");
		
		if (fileName.exists()) {
			fileName.delete();
		}
		
		try {
			Files.copy(source.toPath(), fileName.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dirCreated = true;
	}
	
	
	//Write and read the same file
	public void writeJob(String projectFolder) throws Exception{
		if (!defaultFile)
			changeDirNum();
		//File dirName = new File("temp/jar/task"+dirNumber);
		dirName = "task"+dirNumber;
		boolean check = checkFile();
		
		fileName.createNewFile();
		
		List<String> lines = new ArrayList<String>();
	    String line = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader("template/defaultJob"));
			//BufferedReader in = new BufferedReader(new FileReader(fileName));
			
			
			while ((line = in.readLine()) != null){

			    //String[] details = line.split("\t");
   	
				if (line.contains("#SBATCH --job-name"))
					line =line.replace("jobTemp",job.getJobName());				    	
			    else if (line.contains("#SBATCH --account"))
			    	line =line.replace("uio",job.getAccount());		
			    else if (line.contains("SBATCH --time")){
			    	line =line.replace("0:10:0",job.getExecutionTimeHour()+ ":" + job.getExecutionTimeMinute() + ":" + job.getExecutionTimeSecond());	
			    	setExecutionTime(job.getExecutionTimeHour(), job.getExecutionTimeMinute(), job.getExecutionTimeSecond());
			    }
			    else if (line.contains("#SBATCH --mem-per-cpu"))
			    	line =line.replaceAll("700M",job.getMemory()+ job.getMemoryUnit());	
			    else if (line.contains("cp -R /usit/abel/"))
			    	line =line.replace("App/", projectFolder +"/"+dirName);
			    else if (line.contains("cd task"))
			    	line =line.replace("task", dirName);
			
			    else if (line.contains("mv result.txt out.txt"))
			    	line = line.replace("out.txt", "result"+dirNumber+".txt");
			    else if (line.contains("cp out.txt /usit/abel/u1/dipeshpr/AppResult"))
		    	line =line.replace("out.txt","result"+dirNumber+".txt");
			    
			    lines.add(line);
			    lines.add("\n");
			    
			}	 
			in.close();
			
			FileWriter changeJob= new FileWriter(fileName.getAbsoluteFile(),false);
			BufferedWriter out = new BufferedWriter(changeJob);
			for(String s : lines)
	        	 out.write(s);
			out.flush();
	        out.close();
			
			changeJob.close();
			dirCreated = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    
		
	}
	
	//Get execution time for the job
	public void setExecutionTime(int i, int j, int k){
		executionTime = i * 60d + j + k/60d;	
	}
	
	
	//Write and read the same file
		public void writeJobParam(File fileName,String param) throws Exception{		
			List<String> lines = new ArrayList<String>();
		    String line = null;
			try {
				//BufferedReader in = new BufferedReader(new FileReader("template/job.txt"));
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				
				
				while ((line = in.readLine()) != null){

				    //String[] details = line.split("\t");
	   	
					if (line.contains("java -jar jar1.jar"))
						line =line.replace("jar1.jar","jar1.jar "+param);				    	
		    
				    lines.add(line);
				    lines.add("\n");
				    
				}	 
				in.close();
				
				FileWriter changeJob= new FileWriter(fileName.getAbsoluteFile(),false);
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
			
		}
		
		// write execution info in another file to read
		public void addInfoExecution(String name, double time, long jobId) throws IOException{
			createExecutionFile();
		
			long currentTime = System.currentTimeMillis()/1000;
			long executedTime = (long) (currentTime+ time*60);
			
			System.out.println("current time is " + currentTime);
			System.out.println("executed after "+executedTime);
			
			FileWriter fw = new FileWriter(executionFile.getAbsoluteFile(), true);
			BufferedWriter file = new BufferedWriter(fw);
			file.write(executedTime + "\t" + name + "\t" + jobId + "\n");
			file.flush();
			file.close();
		}
			
		public void createExecutionFile() throws IOException{
			executionFile = new File("temp/execution/executed.txt");			
			// if file does not exists, then create it
			if (!executionFile.exists()) {
				executionFile.createNewFile();
				FileWriter fw = new FileWriter(executionFile.getAbsoluteFile());
				BufferedWriter file = new BufferedWriter(fw);
				file.write("Time \t" + "Filename\t" + "JobId\n");
				file.flush();
				file.close();
			}

		}

}
