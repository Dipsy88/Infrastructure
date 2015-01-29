package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import controller.MainPageController;
import controller.RemoteController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Norstore {

	public JFrame frame;
	private JButton btnGetResults;
	private MainPageController mainPageController;
	private RemoteController remoteController;



	/**
	 * Create the application.
	 */
	public Norstore() {
		initialize();
		initComponents();
		setUpListeners();
	}

	
	public void initComponents(){
		mainPageController = new MainPageController();
		remoteController= new RemoteController();
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnGetResults = new JButton("Get all results");
		
		btnGetResults.setBounds(66, 58, 160, 23);
		frame.getContentPane().add(btnGetResults);
	}
	
	
	
	public void setUpListeners(){
		btnGetResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remoteController.checkFile("AppResult","result10.txt");			
				frame.dispose();
				mainPageController.runMainPage();
				
			}
		});
		
	}
}
