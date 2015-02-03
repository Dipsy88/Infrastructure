package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import controller.AbelController;
import controller.MainPageController;
import model.Job;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class JobPage {

	public JFrame frame;
	private JButton btnSave, btnCancel, btnDefault;
	private JTextField textJobName, textAccount, textHour, textMin, textSec, textMemory;
	private JComboBox comboBoxMemoryUnit;
	private AbelController abelController;
	
	private Job job;
	public MainPageController mainPageController;
	

	/**
	 * Create the application.
	 */
	public JobPage() {
		initialize();
		initComponents();
		fillComboBoxes();
		setUpListeners();	
	}
	
	public void initComponents(){
		job = new Job();
		abelController = new AbelController();
		mainPageController = new MainPageController();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(500, 100, 628, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblJobName = new JLabel("Job name");
		lblJobName.setBounds(35, 52, 131, 14);
		frame.getContentPane().add(lblJobName);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setBounds(35, 107, 131, 14);
		frame.getContentPane().add(lblAccount);
		
		JLabel lblExecutionTime = new JLabel("ExecutionPage time");
		lblExecutionTime.setBounds(35, 161, 144, 14);
		frame.getContentPane().add(lblExecutionTime);
		
		JLabel lblMemoryPerCpu = new JLabel("Memory per CPU");
		lblMemoryPerCpu.setBounds(35, 217, 131, 14);
		frame.getContentPane().add(lblMemoryPerCpu);
		
		JLabel lblConfigurationToExecute = new JLabel("Configuration to execute the test in Abel cluster");
		lblConfigurationToExecute.setBounds(35, 11, 375, 14);
		frame.getContentPane().add(lblConfigurationToExecute);
		
		btnSave = new JButton("Save");
		
		btnSave.setBounds(21, 281, 89, 23);
		frame.getContentPane().add(btnSave);
		
		btnCancel = new JButton("Cancel");
		
		btnCancel.setBounds(136, 281, 89, 23);
		frame.getContentPane().add(btnCancel);
		
		btnDefault = new JButton("Default settings");
		
		btnDefault.setBounds(242, 281, 144, 23);
		frame.getContentPane().add(btnDefault);
		
		textJobName = new JTextField();
		textJobName.setBounds(227, 49, 150, 20);
		frame.getContentPane().add(textJobName);
		textJobName.setColumns(10);
		
		textAccount = new JTextField();
		textAccount.setBounds(227, 104, 150, 20);
		frame.getContentPane().add(textAccount);
		textAccount.setColumns(10);
		
		textHour = new JTextField();
		textHour.setBounds(227, 158, 37, 20);
		frame.getContentPane().add(textHour);
		textHour.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("H");
		lblNewLabel.setBounds(265, 161, 21, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textMin = new JTextField();
		textMin.setBounds(279, 158, 37, 20);
		frame.getContentPane().add(textMin);
		textMin.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("M");
		lblNewLabel_1.setBounds(315, 161, 15, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		textSec = new JTextField();
		textSec.setBounds(328, 158, 37, 20);
		frame.getContentPane().add(textSec);
		textSec.setColumns(10);
		
		textMemory = new JTextField();
		textMemory.setBounds(225, 214, 67, 20);
		frame.getContentPane().add(textMemory);
		textMemory.setColumns(10);
		
		comboBoxMemoryUnit = new JComboBox();
		comboBoxMemoryUnit.setBounds(315, 215, 62, 20);
		frame.getContentPane().add(comboBoxMemoryUnit);
		
		JLabel lblS = new JLabel("S");
		lblS.setBounds(367, 161, 21, 14);
		frame.getContentPane().add(lblS);
	}
	
	
	public void fillComboBoxes() {
		comboBoxMemoryUnit.addItem("M");
		comboBoxMemoryUnit.addItem("G");
				
	}
	
	public void setUpListeners(){
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				job=addItem();				
				abelController.setJob(job);
				
				try {
					abelController.writeJob("App");
					frame.dispose();
					mainPageController.runMainPage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mainPageController.runMainPage();
				
			}
		});
		
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					job = abelController.readJob();
					writeDefault();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
	
	public Job addItem(){
		String jobName = textJobName.getText();
		job.setJobName(jobName);
		
		String account = textAccount.getText();
		job.setAccount(account);
		
		String timeHour = textHour.getText();
		if (timeHour.isEmpty())
			timeHour="0";
		job.setExecutionTimeHour(Integer.parseInt(timeHour));
		
		String timeMin = textMin.getText();
		if (timeMin.isEmpty())
			timeMin="0";
		job.setExecutionTimeMinute(Integer.parseInt(timeMin));
		
		String timeSec= textSec.getText();
		if (timeSec.isEmpty())
			timeSec="0";
		job.setExecutionTimeSecond(Integer.parseInt(timeSec));
		
		String memory= textMemory.getText();
		if (memory.isEmpty())
			memory="0";
		job.setMemory(Integer.parseInt(memory));
		
		job.setMemoryUnit(comboBoxMemoryUnit.getSelectedItem().toString());
		
		return job;
	}
	
	public void writeDefault(){
		textJobName.setText(job.getJobName());
		textAccount.setText(job.getAccount());
		textHour.setText(Integer.toString(job.getExecutionTimeHour()));
		textMin.setText(Integer.toString(job.getExecutionTimeMinute()));
		textSec.setText(Integer.toString(job.getExecutionTimeSecond()));
		textMemory.setText(Integer.toString(job.getMemory()));
		comboBoxMemoryUnit.setSelectedItem(job.getMemoryUnit());
	
	}
}
