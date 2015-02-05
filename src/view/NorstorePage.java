package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import controller.MainPageController;
import controller.NorStoreController;
import controller.RemoteController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NorstorePage {

	public JFrame frame;
	private JButton btnGetResults, btnCancel, btnExit;
	private MainPageController mainPageController;
	private RemoteController remoteController;
	private NorStoreController norStoreController;
	private JButton btnCheckAllExecuted;


	

	/**
	 * Create the application.
	 */
	public NorstorePage() {
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
		
		btnGetResults = new JButton("Send results to norstore");
		
		btnGetResults.setBounds(222, 78, 195, 23);
		frame.getContentPane().add(btnGetResults);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(222, 200, 195, 29);
		frame.getContentPane().add(btnCancel);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(222, 258, 195, 29);
		frame.getContentPane().add(btnExit);
		
		btnCheckAllExecuted = new JButton("Check executed results");
		
		btnCheckAllExecuted.setBounds(221, 137, 195, 29);
		frame.getContentPane().add(btnCheckAllExecuted);
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
		
		btnCheckAllExecuted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runAllNorstoreResultsPage();
				
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
