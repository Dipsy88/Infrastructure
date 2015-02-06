package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AllNorstoreResultsPage;
import view.ExecutionPage;
import view.JobPage;
import view.MainPage;
import view.NorstorePage;
import view.NorstoreResultPage;

public class MainPageController {
	public MainPage mainPage;
	public ExecutionPage executionPage;
	public JobPage jobPage;
	public NorstorePage norstore;
	public AllNorstoreResultsPage allNorstoreResultsPage;
	public NorstoreResultPage norstoreResultPage;
	
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
	
	public void runNorStorePage(){		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		   
					norstore = new NorstorePage();
					norstore.frame.setVisible(true);

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
	
	public void runAllNorstoreResultsPage(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					allNorstoreResultsPage = new AllNorstoreResultsPage();
					allNorstoreResultsPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	
	public void runNorstoreResultPage(String fileName){
		EventQueue.invokeLater(new Runnable() {
			private String file;
			private Runnable init(String var){
    			file = var;
    			return this;
    		}
			public void run() {				
				try {
					norstoreResultPage = new NorstoreResultPage(file);
					norstoreResultPage.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.init(fileName));	
	}
}
