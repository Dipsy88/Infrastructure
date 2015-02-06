package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jcraft.jsch.JSchException;

import controller.MainPageController;
import controller.NorStoreController;

public class NorstoreResultPage {

	public JFrame frame;
	private JButton btnCancel, btnHome, btnExit;
	private JTextArea textArea;

	private MainPageController mainPageController;
	private NorStoreController norStoreController;
	
	private String fileName;
	
	/**
	 * Launch the application.
	 */


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Create the application.
	 */
	public NorstoreResultPage(String fileName) {
		this.fileName = fileName;
		initComponents();
		initialize();
		setUpListeners();	
		displayText();
	}

	public void initComponents(){
		mainPageController = new MainPageController();
		norStoreController = new NorStoreController();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 313, 628, 35);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		btnCancel = new JButton("Cancel");
		
		
		btnCancel.setBounds(36, 6, 117, 29);
		panel.add(btnCancel);
		
		btnHome = new JButton("Home");
		
		btnHome.setBounds(243, 6, 117, 29);
		panel.add(btnHome);
		
		btnExit = new JButton("Exit");
		
		btnExit.setBounds(445, 6, 117, 29);
		panel.add(btnExit);
		
		textArea = new JTextArea(18,50);
		
		JPanel panel2 = new JPanel();
		
		//JScrollPane scrollPane = new JScrollPane(textArea);	
		textArea.setBounds(6, 22, 616, 288);
		panel2.setBounds(0, 0, 628, 316);
		panel2.setLayout(null);
		panel2.add(textArea);
		frame.getContentPane().add(panel2);
		
//		JScrollPane scroll = new JScrollPane (textArea, 
//				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		panel2.add(scroll);
	}
	
	public void displayText(){
		ArrayList<String> result = new ArrayList<String>();
		try {
			result=norStoreController.readFile("App", fileName);
		} catch (JSchException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		for (String temp : result) {
			textArea.append(temp);
			textArea.append("\n");
			System.out.println(temp);		
		}
	}
	
	public void setUpListeners(){
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runNorStorePage();
			}
		});
		
		btnHome.addActionListener(new ActionListener() {
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
}
