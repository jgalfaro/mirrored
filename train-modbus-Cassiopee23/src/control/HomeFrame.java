package control;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

/*
*
@author : Laurent CETIN & Antoine FROMENTIN
@version : 1.0
* Télécom SudParis - 2016
* Projet Cassiopée 23
*
* Système Scada - Train Mindstorm - Lejos EV3
*
* */


public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container content;
	private JTextArea messageLog;
	private SysFerrPanel panel;
	HomeFrame() {
		super();
		build();
	}
	
	private void build(){
		//Window properties
		this.setTitle("Control Center - System Scada - Train"); 
		this.setSize(1200,800);
		this.setLocationRelativeTo(null); 				//Center
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.buildMenu();
		this.buildContentPanel();

	}
	
	public void afficherSysFerrPanel(){
		panel.removeAll();
	    panel.principalPanel();
	    panel.controlePanel();
		panel.repaint();
	}
	
	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		 
		JMenu menu1 = new JMenu("Menu");
  
	    JMenuItem mnuAddEV3 = new JMenuItem("Add Ev3's");
	    mnuAddEV3.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	        	Train_Scada_EV3_Cassiopee23.addCircuitCentre();
	        	Train_Scada_EV3_Cassiopee23.addCircuitExtern();
	        	Train_Scada_EV3_Cassiopee23.addCircuitExtern();
	        	Train_Scada_EV3_Cassiopee23.addEmetteur_IR();
	        	Train_Scada_EV3_Cassiopee23.addEmetteur_IR();
	          }
	        });
		menu1.add(mnuAddEV3);
		/*
	    JMenuItem mnuAddGo = new JMenuItem("Go/Stop");
	    mnuAddEV3.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	        	Train_Scada_EV3_Cassiopee23.topGo(true);
	          }
	        });
		menu1.add(mnuAddGo);*/
		
	    JMenuItem mnuExit = new JMenuItem("Exit");
	    mnuExit.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	        	//Train_Scada_EV3_Cassiopee23.topExit();
	        	Train_Scada_EV3_Cassiopee23.exit();
	          }
	        });
	    
		menu1.add(mnuExit);

		menuBar.add(menu1);		
		
		JMenuItem menuAbout = new JMenuItem("?");
		//show doc's
		menuBar.add(menuAbout);

		setJMenuBar(menuBar);	
				
	}
	
	private void buildContentPanel () {
		content = getContentPane();
		content.setLayout(null);

		messageLog = new JTextArea();
		messageLog.setBounds(5, 5, 195, 725);
		messageLog.setBackground(Color.white);
		messageLog.setEditable(false);
		
		panel = new SysFerrPanel();
		panel.setLayout(null);
		panel.setBounds(200,0,1000,800);
		
		content.add(panel);
		content.add(messageLog);
	}
	
	public Container getContent() {
		return content;
	}

	public void addMessage(String message) {
		
		String currentText = messageLog.getText();
		if (currentText.length() > 200) {
			currentText = currentText.substring(0, 200);
		}
		messageLog.setText(message + "\n" + currentText);
	}
}