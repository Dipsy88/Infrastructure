package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.rmi.CORBA.Util;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;




import controller.AbelController;
import controller.MainPageController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class MainPage {

	public JFrame frame;

	private JButton btnModelbasedTestingEnvironment, btnNotur, btnNorStore, btnCancel, btnChangeDefaultSettings;
	
	private AbelController abelController;
	private MainPageController mainPageController;


	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
		initComponents();	
		setUpListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void initComponents(){
		abelController = new AbelController();
		mainPageController= new MainPageController();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel background=new JLabel(new ImageIcon("images/masthead.jpg"));
		background.setLayout(null);
		
		frame.setContentPane(background);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Home page");
		
		btnNotur = new JButton("Notur setting up jobs");
		
		btnNotur.setBounds(183, 122, 237, 23);
		frame.getContentPane().add(btnNotur);
		
		btnNorStore = new JButton("Norstore getting results");	
		btnNorStore.setBounds(183, 179, 237, 23);
		frame.getContentPane().add(btnNorStore);
		
		btnModelbasedTestingEnvironment = new JButton("Model-Based Testing");		
		btnModelbasedTestingEnvironment.setBounds(183, 62, 237, 23);
		frame.getContentPane().add(btnModelbasedTestingEnvironment);
		
		btnCancel = new JButton("Cancel");	
		btnCancel.setBounds(183, 291, 237, 23);
		frame.getContentPane().add(btnCancel);
		
		btnChangeDefaultSettings = new JButton("Change default settings");
		btnChangeDefaultSettings.setBounds(183, 232, 237, 29);
		frame.getContentPane().add(btnChangeDefaultSettings);
		
		
		frame.setResizable(true);
	}
	
	public void setUpListeners(){
		btnModelbasedTestingEnvironment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runExecutionPage();					
			}
		});
		
		btnNotur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				mainPageController.runJobPage();
			}
		});
		
		btnNorStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runNorStorePage();
			}
		});
		
		btnChangeDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runDefaultSettingsPage();
			}
		});
		
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		
		
		
	}

	public void run() throws IOException
	{
	  Manifest manifest = new Manifest();

	  manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
      manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, main.ExecuteCase.class.getName());
      manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, ".");
	  
	 // manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
	  
	  JarOutputStream jarOut = new JarOutputStream(new FileOutputStream("temp/jar/jar1.jar"), manifest);
	  
	  
	  jarOut.putNextEntry(new ZipEntry("/main/ExecuteCase.class"));	
	  
	  File file1 = new File("bin/main/ExecuteCase.class");
	  byte[] fileBytes1 = getFileBytes(file1);
	  jarOut.write(fileBytes1);	
	 

	  jarOut.closeEntry();
	  
	 // add(new File("src"), target);
	  // Add the main class
      
	  jarOut.close();
	  
	  
	}
	 private static byte[] getFileBytes(File file) throws IOException, 
	     FileNotFoundException{
		 long fileSize = file.length();
		 byte[] arr = new byte[(int)fileSize];
		
		 FileInputStream fileIn = new FileInputStream(file);
		 fileIn.read(arr);
		
		 return arr;
	}


	
	private void add(File source, JarOutputStream target) throws IOException
	{
	  BufferedInputStream in = null;
	  try
	  {
	    if (source.isDirectory())
	    {
	      String name = source.getPath().replace("\\", "/");
	      if (!name.isEmpty())
	      {
	        if (!name.endsWith("/"))
	          name += "/";
	        JarEntry entry = new JarEntry(name);
	        entry.setTime(source.lastModified());
	        target.putNextEntry(entry);
	        target.closeEntry();
	      }
	      for (File nestedFile: source.listFiles())
	        add(nestedFile, target);
	      return;
	    }

	    JarEntry entry = new JarEntry(source.getPath().replace("\\", "/"));
	    entry.setTime(source.lastModified());
	    target.putNextEntry(entry);
	    in = new BufferedInputStream(new FileInputStream(source));

	    byte[] buffer = new byte[1024];
	    while (true)
	    {
	      int count = in.read(buffer);
	      if (count == -1)
	        break;
	      target.write(buffer, 0, count);
	    }
	    target.closeEntry();
	  }
	  finally
	  {
	    if (in != null)
	      in.close();
	  }
	}
}
