package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import controller.MainPageController;
import controller.NorStoreController;
import controller.RemoteController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Norstore {

	public JFrame frame;
	private JButton btnGetResults, btnCancel, btnExit;
	private MainPageController mainPageController;
	private RemoteController remoteController;
	private NorStoreController norStoreController;


	

	/**
	 * Create the application.
	 */
	public Norstore() {
		initialize();
		initComponents();
		setUpListeners();
	}

	
	public void initComponents(){
		norStoreController = new NorStoreController();
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
		
		btnGetResults.setBounds(222, 92, 160, 23);
		frame.getContentPane().add(btnGetResults);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(222, 177, 160, 29);
		frame.getContentPane().add(btnCancel);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(222, 258, 160, 29);
		frame.getContentPane().add(btnExit);
	}
	
	
	
	public void setUpListeners(){
		btnGetResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					norStoreController.copyFiles();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
}
