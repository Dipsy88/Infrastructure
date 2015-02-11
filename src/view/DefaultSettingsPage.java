package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DefaultSettingsPage {

	public JFrame frame;
	private JTextField textField;
	private JButton btnChangeFolder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DefaultSettingsPage window = new DefaultSettingsPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DefaultSettingsPage() {
		initialize();
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
		
		textField = new JTextField();
		textField.setBounds(248, 63, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCurrentFolder = new JLabel("Current folder");
		lblCurrentFolder.setBounds(36, 69, 110, 16);
		frame.getContentPane().add(lblCurrentFolder);
		
		btnChangeFolder = new JButton("Change folder");
		
		btnChangeFolder.setBounds(29, 142, 117, 29);
		frame.getContentPane().add(btnChangeFolder);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(29, 279, 117, 29);
		frame.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(241, 279, 117, 29);
		frame.getContentPane().add(btnCancel);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(444, 279, 117, 29);
		frame.getContentPane().add(btnExit);
	}
	
	public void setUpListeners(){
		btnChangeFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
	}
}
