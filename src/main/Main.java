package main;

import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import view.MainPage;

public class Main {
	
	public static MainPage mainPage;
	
	public static void main(String[] args) throws IOException{
		runMainPage();	
//		  Manifest manifest = new Manifest();
//
//		  manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
//	      manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, Main.class.getName());
//	      manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, ".");
//		  
//		 // manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
//	      int random = (int )(Math.random() * 50 + 1);
//		 
//			  JarOutputStream target = new JarOutputStream(new FileOutputStream(("jar")+random+"1.jar"), manifest);
//			//  addClass(Main.class, target);
//			
//			  
//			  target.putNextEntry(new ZipEntry("/main/Main.class"));	
//			  
//			  File file1 = new File("bin/main/Main.class");
//			  byte[] fileBytes1 = getFileBytes(file1);
//			  target.write(fileBytes1);	
//			  target.close();
//	
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
	
	public static void createJarFile() throws IOException{
		FileOutputStream fout = new FileOutputStream("temp/jar/jar1.jar");
		JarOutputStream jarOut = new JarOutputStream(fout);
		
	}
	
}
