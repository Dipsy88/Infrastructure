package view;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import controller.DefaultSettingsController;
import controller.MainPageController;
import model.DefaultSettings;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class DefaultSettingsPage {

	public JFrame frame;
	private JTextField textExecutionFolder;
	private JButton btnChangeFolder, btnSave, btnCancel, btnExit, btnClearFolder;
	private DefaultSettings defaultSettings;
	private DefaultSettingsController defaultSettingsController;
	private MainPageController mainPageController;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public DefaultSettingsPage() {
		initComponents();
		initialize();
		readDefaulSettings();
		writeDefaultSettings(defaultSettings.getExecutionFolder());

		setUpListeners();	
	}
	
	public void initComponents(){
		mainPageController = new MainPageController();
		defaultSettings = new DefaultSettings();
		defaultSettingsController = new DefaultSettingsController();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Default settings");
		
		textExecutionFolder = new JTextField();
		textExecutionFolder.setBounds(174, 63, 403, 28);
		frame.getContentPane().add(textExecutionFolder);
		textExecutionFolder.setColumns(10);
		
		JLabel lblCurrentFolder = new JLabel("Execution folder:");
		lblCurrentFolder.setBounds(36, 69, 126, 16);
		frame.getContentPane().add(lblCurrentFolder);
		
		btnChangeFolder = new JButton("Change execution folder");		
		btnChangeFolder.setBounds(357, 149, 220, 29);
		frame.getContentPane().add(btnChangeFolder);
		
		btnSave = new JButton("Save");		
		btnSave.setBounds(29, 279, 117, 29);
		frame.getContentPane().add(btnSave);
		
		btnCancel = new JButton("Cancel");		
		btnCancel.setBounds(235, 279, 117, 29);
		frame.getContentPane().add(btnCancel);
		
		btnExit = new JButton("Exit");		
		btnExit.setBounds(460, 279, 117, 29);
		frame.getContentPane().add(btnExit);
		
		btnClearFolder = new JButton("Clear execution folder");
	
		btnClearFolder.setBounds(360, 207, 217, 29);
		frame.getContentPane().add(btnClearFolder);
	}
	
	public void setUpListeners(){
		btnChangeFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				writeDefaultSettings(folder);
				
			}
		});		
		
		btnClearFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					defaultSettingsController.clearFile(defaultSettings);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				mainPageController.runMainPage();
				
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItem();
				try {
					defaultSettingsController.writeFile(defaultSettings);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				mainPageController.runMainPage();
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
	
	
	public void readDefaulSettings(){
		try {
			defaultSettings = defaultSettingsController.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void writeDefaultSettings(String name){
		textExecutionFolder.setText(name);

	}
	
	public DefaultSettings addItem(){
		defaultSettings.setExecutionFolder(textExecutionFolder.getText());
		return defaultSettings;	
	}
}
