package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.MainPageController;

public class NorstoreResultPage {

	public JFrame frame;
	private JButton btnCancel, btnHome, btnExit;
	private JTextArea textArea;

	private MainPageController mainPageController;
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public NorstoreResultPage() {
		initComponents();
		initialize();
		setUpListeners();
	}

	public void initComponents(){
		mainPageController = new MainPageController();
		
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
		
		textArea = new JTextArea();
		textArea.setBounds(0, 0, 628, 316);
		
		
		JPanel panel2 = new JPanel();
		JScrollPane scrollPane = new JScrollPane(textArea);	
		panel2.add(scrollPane);
	}
	
	public void displayText(){
		
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
