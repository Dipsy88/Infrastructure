package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.ExecuteCase;
import controller.AbelController;
import controller.ExecutePageController;
import controller.MainPageController;
import controller.RemoteController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class ExecutionPage {

	public JFrame frame;
	private JButton btnDelete;
	private RemoteController remoteController;
	private JButton btnRun1;
	private JButton btnRun2;
	private JButton btnCancel;
	private ExecuteCase executeCase;

	public MainPageController mainPageController;
	public ExecutePageController executePageController;
	private AbelController abelController;
	private JButton btnExit;

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
		
		btnDelete.setBounds(214, 173, 161, 23);
		frame.getContentPane().add(btnDelete);
		
		btnRun1 = new JButton("Execute one case");
		
		btnRun1.setBounds(214, 46, 161, 23);
		frame.getContentPane().add(btnRun1);
		
		btnRun2 = new JButton("Execute two cases");
		
		btnRun2.setBounds(214, 106, 161, 23);
		frame.getContentPane().add(btnRun2);
		
		btnCancel = new JButton("Cancel");
		
		btnCancel.setBounds(214, 236, 161, 23);
		frame.getContentPane().add(btnCancel);
		
		btnExit = new JButton("Exit");
		
		btnExit.setBounds(214, 293, 161, 29);
		frame.getContentPane().add(btnExit);
	}
	
	public void setUpListeners(){
		
		btnRun1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String number = "1";
				try {
					executePageController.createJar();
					abelController.writeJobParam(AbelController.getFileName(), "1");

					executePageController.copyProgram();
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
				//execute (number);
			}
		});
		
		btnRun2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String number = "2";
				try {
					executePageController.createJar();
					abelController.writeJobParam(AbelController.getFileName(), "2");
		
					executePageController.copyProgram();
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

			}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//execute (number);
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
	

	public void createJar() throws IOException{		
//		ExecuteCase.setNumber(number);
//		executeCase.createJarFile();
		executePageController.createJar();
	}
	
}
