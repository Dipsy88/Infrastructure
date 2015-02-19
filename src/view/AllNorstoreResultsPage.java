package view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import com.jcraft.jsch.JSchException;

import controller.MainPageController;
import controller.NorStoreController;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllNorstoreResultsPage {

	public JFrame frame;
	private NorStoreController norStoreController;
	private List<String> taskLists = new ArrayList<String>();
	private JButton btnCancel, btnHome, btnExit;
	private ArrayList<JButton> btnListTasks;
	
	private MainPageController mainPageController;
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public AllNorstoreResultsPage() {
		initComponents();
		initialize();
		setUpListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void initComponents(){
		norStoreController = new NorStoreController();
		mainPageController = new MainPageController();
		
		try {
			norStoreController.getFiles("AppResult");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taskLists = norStoreController.getFiles();
		
		btnListTasks = new ArrayList<JButton>();
	}
	
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
		
		JPanel panelGrid = new JPanel( new GridLayout(5, 5, 4, 4) );

		for(int i=0;i<taskLists.size();i++)
	        {
	            for(int j=1;j<=1;j++)
	            {
	            	JButton task=new JButton(String.valueOf(taskLists.get(i).substring(0, taskLists.get(i).lastIndexOf('.')))); // for removing extension
	            	btnListTasks.add(task);
	                panelGrid.add(btnListTasks.get(i));
	                
//	                btnListTasks.get(i).addActionListener(new ActionListener() {
//	                	
//	                	
//	        			public void actionPerformed(ActionEvent e) {
//	        				frame.dispose();
//	        			//	mainPageController.runNorstoreResultPage(taskLists.get(i));;
//	        			}
//	        		});
	                
//	                
//	                btnListTasks.get(i).addMouseListener(new MouseAdapter() {
//	        			@Override
//	        			public void mouseClicked(MouseEvent e) {
//	        				//frame.dispose();
//	        		            doPop(e);
//	        			}
//	        		});
	            }
	        }
		
		JScrollPane scrollPane = new JScrollPane(panelGrid);	
		scrollPane.setBounds(0, 0, 628, 316);
		frame.getContentPane().add(scrollPane);
		
	}

	//To do
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
		
		for (int i = 0; i < btnListTasks.size(); i++) {		
			btnListTasks.get(i).addActionListener(new ActionListener() {  	
				private int id;
	    		public void actionPerformed(ActionEvent e) {
	    			frame.dispose();
	    			mainPageController.runNorstoreResultPage(taskLists.get(id));
	    			
	    		}
	    		private ActionListener init(int var){
	    			id = var;
	    			return this;
	    		}
	    	}.init(i));
			
			btnListTasks.get(i).addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent e) {
    				//frame.dispose();
    		            doPop(e);
    			}
    		});
			
			
		}

	}
	
	public void popUpListeners(){
		
		
	}
	
	private void doPop(MouseEvent e){
        PopUpDemo menu = new PopUpDemo();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

}

class PopUpDemo extends JPopupMenu {
    JMenuItem delItem, copyItem, viewItem;
    public PopUpDemo(){
    	delItem = new JMenuItem("Delete");
    	copyItem = new JMenuItem("Copy");
    	viewItem = new JMenuItem("View");
        add(delItem);
        add(copyItem);
        add(viewItem);
    }
}
