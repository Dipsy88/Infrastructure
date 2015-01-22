package controller;

import java.awt.EventQueue;

import view.ExecutionPage;
import view.JobPage;
import view.MainPage;

public class MainPageController {
	public MainPage mainPage;
	public ExecutionPage executionPage;
	public JobPage jobPage;
	
	
	
	public void runMainPage(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				   
					mainPage = new MainPage();
					mainPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void runExecutionPage(){		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		   
					executionPage = new ExecutionPage();
					executionPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void runJobPage(){		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jobPage = new JobPage();
					jobPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
