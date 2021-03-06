package view;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.ExecuteCase;
import model.DefaultSettings;
import controller.AbelController;
import controller.DefaultSettingsController;
import controller.ExecutePageController;
import controller.MainPageController;
import controller.RemoteController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.JTextField;

public class ExecutionPage {

	public JFrame frame;
	private JButton btnDelete;
	private RemoteController remoteController;
	private JButton btnExecuteConstraints;
	private JButton btnCancel;
	private ExecuteCase executeCase;

	public MainPageController mainPageController;
	public ExecutePageController executePageController;
	private AbelController abelController;
	private DefaultSettingsController defaultSettingsController;
	private DefaultSettings defaultSettings;
	
	private JButton btnExit;
	private JButton btnExecuteFile;
	
	private String folder,executionFile, execFolderName;

	/**
	 * Create the application.
	 */
	public ExecutionPage() {
		initComponents();		
		initialize();
		setUpListeners();
	}

	public void initComponents(){
		remoteController= new RemoteController();
		mainPageController = new MainPageController();
		executeCase = new ExecuteCase();
		executePageController = new ExecutePageController();
		abelController = new AbelController();
		defaultSettingsController = new DefaultSettingsController();
		defaultSettings = new DefaultSettings();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnDelete = new JButton("Delete files in Notur");
		
		btnDelete.setBounds(193, 173, 230, 23);
		frame.getContentPane().add(btnDelete);
		
		btnExecuteConstraints = new JButton("Execute file with constraints");
		
		btnExecuteConstraints.setBounds(193, 106, 230, 23);
		frame.getContentPane().add(btnExecuteConstraints);
		
		btnCancel = new JButton("Cancel");
		
		btnCancel.setBounds(193, 236, 230, 23);
		frame.getContentPane().add(btnCancel);
		
		btnExit = new JButton("Exit");
		
		btnExit.setBounds(193, 297, 238, 29);
		frame.getContentPane().add(btnExit);
		
		btnExecuteFile = new JButton("Execute file");
		
		btnExecuteFile.setBounds(193, 47, 230, 29);
		frame.getContentPane().add(btnExecuteFile);
	}
	
	public void setUpListeners(){	
		btnExecuteConstraints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					defaultSettings = defaultSettingsController.readFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Folder is"+defaultSettings.getExecutionFolder());
				if (defaultSettings.getExecutionFolder() == null || defaultSettings.getExecutionFolder().isEmpty()){
					 JOptionPane.showMessageDialog(frame,
						        "Execution folder was not defined in the default settings. Please select the execution folder first");
					folder = askDir();
				}
				else
					folder = defaultSettings.getExecutionFolder();
				
				execFolderName= folder.substring(folder.lastIndexOf("/")+1);
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(folder));
				int returnVal=chooser.showOpenDialog(null);
				
				 if(returnVal == JFileChooser.APPROVE_OPTION) {
					 executionFile =  chooser.getSelectedFile().getAbsolutePath();
				       System.out.println("Check the execution file now" +
				            chooser.getSelectedFile().getName());
				       
				       try {
						System.out.println("Check the execution file now" +
								   chooser.getSelectedFile().getAbsolutePath() + "and " + chooser.getSelectedFile().getCanonicalPath());
				       } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				       }
				     
				    
					 executionFile = execFolderName+"/"+new File(folder).toURI().relativize(new File(executionFile).toURI()).getPath();
					 
					 System.out.println(executionFile);
					 
					 //Need to do this:
					 String choice= JOptionPane.showInputDialog("Please enter how many test cases you want to execute ");
					 
					 copyAndExecute(choice);
				 }else if (returnVal == JFileChooser.CANCEL_OPTION) {
					    System.out.println("Cancel was selected");
				 }
			}
		
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remoteController.deleteAllFiles("App");
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		btnExecuteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					defaultSettings = defaultSettingsController.readFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Folder is"+defaultSettings.getExecutionFolder());
				if (defaultSettings.getExecutionFolder() == null || defaultSettings.getExecutionFolder().isEmpty()){
					 JOptionPane.showMessageDialog(frame,
						        "Execution folder was not defined in the default settings. Please select the execution folder first");
					folder = askDir();
				}
				else
					folder = defaultSettings.getExecutionFolder();
				
				System.out.println("This is a test");
				
				execFolderName= folder.substring(folder.lastIndexOf("/")+1);
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(folder));
				int returnVal=chooser.showOpenDialog(null);
				
				 if(returnVal == JFileChooser.APPROVE_OPTION) {
					 executionFile =  chooser.getSelectedFile().getAbsolutePath();
				       System.out.println("Check the execution file now" +
				            chooser.getSelectedFile().getName());
				       
				       try {
						System.out.println("Check the execution file now" +
								   chooser.getSelectedFile().getAbsolutePath() + "and " + chooser.getSelectedFile().getCanonicalPath());
				       } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				       }
				     
				  
					 executionFile = execFolderName+"/"+new File(folder).toURI().relativize(new File(executionFile).toURI()).getPath();
					 
					 System.out.println(executionFile);
					 copyAndExecute("9");
				 }else if (returnVal == JFileChooser.CANCEL_OPTION) {
					    System.out.println("Cancel was selected");
				 }
			}
		});
	}
	
	public String askDir(){
		String folder = null;
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int response = folderChooser.showOpenDialog(null);			
		if (response == JFileChooser.APPROVE_OPTION){
			System.out.println("You chose to open this file: " 
					+ folderChooser.getSelectedFile().toString());		
			folder = folderChooser.getSelectedFile().toString();	
			
		} else {
			System.out.println("The file operation was cancelled.");
		}
		JOptionPane.showMessageDialog(frame,
		        "Now select the execution file");
		return folder;
	}
	
	//for executing test cases directly
	public void execute(String number){
		URL location = ExecutionPage.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
		//String pythonScriptPath = "C:/Users/Dipesh.DIPSYPC/workspace/Test cases/src/Main/__init__.py";
		String pythonScriptPath = "Test cases/src/Main/__init__.py";

	//System.out.println(sys.path);
		ProcessBuilder pb = new ProcessBuilder("python",pythonScriptPath,number);
		try {
			Process p = pb.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void copyAndExecute(String choice){
		try {
			executePageController.createJar();
			abelController.writeJobParam(AbelController.getFileName(), executionFile, choice);

			executePageController.copyProgram(folder);
			remoteController.delete("App",AbelController.getDirName());
			remoteController.sendFiles("App",AbelController.getDirName());
			
			long jobId= remoteController.executeJob("App", AbelController.getDirName());
			abelController.addInfoExecution(AbelController.getDirName(), AbelController.getExecutionTime(), jobId);
			AbelController.setDirCreated(false);

		    // show a joptionpane dialog using showMessageDialog
		    JOptionPane.showMessageDialog(frame,
		        "Results for this execution is written in: " + AbelController.getDirName() + ".txt");
		    frame.dispose();
			mainPageController.runMainPage();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public void createJar() throws IOException{		
//		ExecuteCase.setNumber(number);
//		executeCase.createJarFile();
		executePageController.createJar();
	}

}
