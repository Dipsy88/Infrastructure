package main;

import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.jar.*;

import org.w3c.dom.Attr;

import view.MainPage;

public class ExecuteCase {
	
	private static int random;
	public static MainPage mainPage;
	public static String getNumber() {
		return number;
	}
	public static void setNumber(String number) {
		ExecuteCase.number = number;
	}

	public static String number;
	
	
	public static void main(String[] args) throws IOException{
		number = args[0];
		execute(number);
	}
	
	
	 private static byte[] getFileBytes(File file) throws IOException, 
	     FileNotFoundException{
		long fileSize = file.length();
		byte[] arr = new byte[(int)fileSize];
		
		FileInputStream fileIn = new FileInputStream(file);
		fileIn.read(arr);
		
		return arr;
	}

	 private static void addClass(Class c, JarOutputStream jarOutputStream) throws IOException
	    {
	        String path = c.getName().replace('.', '/') + ".class";
	        jarOutputStream.putNextEntry(new JarEntry(path));
	        jarOutputStream.write(toByteArray(c.getClassLoader().getResourceAsStream(path)));
	        jarOutputStream.closeEntry();
	        jarOutputStream.close();
	    }
	 
	 public static byte[] toByteArray(InputStream in) throws IOException {
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buf = new byte[0x1000];
	        while (true) {
	            int r = in.read(buf);
	            if (r == -1) {
	                break;
	            }
	            out.write(buf, 0, r);
	        }
	        return out.toByteArray();
	    }
	
	public static void runMainPage(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage = new MainPage();
					mainPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void createJarFile(File dirPath) throws IOException{
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, ExecuteCase.class.getName());
	    manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, ".");
	   
	    
	   // random = (int )(Math.random() * 50 + 1);
		 
	  //  JarOutputStream target = new JarOutputStream(new FileOutputStream(("temp/jar/task1/jar")+random+".jar"), manifest);

	    JarOutputStream target = new JarOutputStream(new FileOutputStream((dirPath+"/jar1.jar")), manifest);
	    
	    target.putNextEntry(new ZipEntry("/main/ExecuteCase.class"));	
			  
	    File file1 = new File("bin/main/ExecuteCase.class");
	    byte[] fileBytes1 = getFileBytes(file1);
	    target.write(fileBytes1);	
	    target.close();
	}
	
	 public static void unzip(String zipFilePath, String destDirectory) throws IOException {
	        File destDir = new File(destDirectory);
//	        if (!destDir.exists()) {
//	            destDir.mkdir();
//	        }
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	        ZipEntry entry = zipIn.getNextEntry();
	        // iterates over entries in the zip file
	        while (entry != null) {
	            String filePath = destDirectory + File.separator + entry.getName();
	            if (!entry.isDirectory()) {
	                // if the entry is a file, extracts it
	                extractFile(zipIn, filePath);
	            } else {
	                // if the entry is a directory, make the directory
	                File dir = new File(filePath);
	                dir.mkdir();
	            }
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
	    }
	 
	 private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	        byte[] bytesIn = new byte[4096];
	        int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) {
	            bos.write(bytesIn, 0, read);
	        }
	        bos.close();
	    }
	
	 public static void extractJar(File dirname) throws IOException{
			String dir = dirname.toString();
			String jarPath = dir+"/jar1.jar";
			File jarFile = new File(jarPath);
			JarFile jar = new JarFile(jarFile);
			Enumeration enumEntries = jar.entries();
		
			 while (enumEntries.hasMoreElements()) {
				 Object objectFile = enumEntries.nextElement();
		         String ent=proc(objectFile);
		         if(ent.indexOf("/")>0)
		         {
		         String fil=ent.substring(0,ent.indexOf("/"));
		       //  System.out.println(fil);
		         String dirNew = dir + "/" + fil;
		         File local=new File(dirNew);
		         if(!local.exists())
		        	 local.mkdirs();
		         }
		         if(ent.indexOf("/")==0)
		         {
		        	 
		        	 int index = ent.indexOf("/",1);
			         String fil=ent.substring(1,index);
			    //     System.out.println(fil);
			         String dirNew = dir + "/" + fil;
			         File local=new File(dirNew);
			         if(!local.exists())
			        	 local.mkdirs();
		         }
		         if(ent.indexOf(".")>0)
		         {
		         int n=ent.length();
		         String fil1=ent.substring(ent.lastIndexOf("/")+1,n);
		      //   System.out.println(fil1);
		         JarEntry file = (JarEntry) objectFile;
//		          extract(jar,ent);  
		         
		         java.io.File f = new java.io.File(dir + java.io.File.separator + file.getName());
		         if (file.isDirectory()) { // if its a directory, create it
		             f.mkdir();
		             continue;
		         }
		         java.io.InputStream is = jar.getInputStream(file); // get the input stream
		         java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
		         while (is.available() > 0) {  // write contents of 'is' to 'fos'
		             fos.write(is.read());
		         }
		         fos.close();
		         is.close();
		         }
		          
		        }
			
		}
	 
//	public static void extractJar(File dirname) throws IOException{
//		String dir = dirname.toString();
//		String jarPath = dir+"/jar"+random+".jar";
//		File jarFile = new File(jarPath);
//		JarFile jar = new JarFile(jarFile);
//		Enumeration enumEntries = jar.entries();
//	
//		while (enumEntries.hasMoreElements()) {
//			
//			String ent=proc(enumEntries.nextElement());
//	        if(ent.indexOf("/")>0){
//	        	String fil=ent.substring(0,ent.indexOf("/"));
//	        	System.out.println(fil);
//	        	File local=new File(fil);
//	        	if(!local.exists())
//	        		local.mkdirs();
//	         }
//	         if(ent.indexOf(".")>0){
//	        	 int n=ent.length();
//	        	 String fil1=ent.substring(ent.lastIndexOf("/")+1,n);
//	        	 
//	        	 
//	        	 InputStream is = jar.getInputStream(ze)
//	        	 
//	        	 InputStream is = jar.getInputStream(file); // get the input stream
//	        	 FileOutputStream fos = new FileOutputStream(f);
//	        	 while (is.available() > 0) {  // write contents of 'is' to 'fos'
//	        		 fos.write(is.read());
//	        	 }
//	        	    fos.close();
//	        	    is.close();
//	        	 System.out.println(fil1);
//	        	 
//	        	 
//	        	 extract(jarFile.getName(),ent);  
//	         }
//       
//		}
//	}
	
	 public static String proc(Object obj)
     {
	     JarEntry entry = (JarEntry)obj;
	     String name = entry.getName();
	    // System.out.println("\nEntry Name: "+name);
	     return(name);
     }

     public static void extract(JarFile jar,String entryName)throws IOException,ZipException{
    	
    	 //System.out.println(jarName + " opened.");

        try {
           // Get the entry and its input stream.

           JarEntry entry = jar.getJarEntry(entryName);

           // If the entry is not null, extract it. Otherwise, print a 
           // message.

           if (entry != null) {
              // Get an input stream for the entry.
               
              InputStream entryStream = jar.getInputStream(entry);

              try {
                 // Create the output file (clobbering the file if it exists).

                 FileOutputStream file = new FileOutputStream(entry.getName());

                 try {
                    // Allocate a buffer for reading the entry data.

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    // Read the entry data and write it to the output file.

                    while ((bytesRead = entryStream.read(buffer)) != -1) {
                       file.write(buffer, 0, bytesRead);
                    }

                    System.out.println(entry.getName() + " extracted.");
                 }
                 finally {
                    file.close();
                 }
              }
              finally {
                 entryStream.close();
              }
           }
           else {
              System.out.println(entryName + " not found.");
           } // end if
        }
        finally {
           jar.close();
        //   System.out.println(jarName + " closed.");
        }
     }
     
	public static void execute(String number){
		String pythonScriptPath = "Test cases/src/Main/__init__.py";

		ProcessBuilder pb = new ProcessBuilder("python",pythonScriptPath,number);
		try {
			Process p = pb.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
