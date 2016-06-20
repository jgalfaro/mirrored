package control;
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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class SysFerrPanel extends JPanel implements ActionListener,ChangeListener {
	//private SystemFerroviaire mySys= new SystemFerroviaire();
	private static final long serialVersionUID = 145L;
	JButton algo = new JButton("Actif");
	JButton[] consMot = new JButton[6];
	JButton[] sensTr = new JButton[2];
	JButton urgence = new JButton("ARRET D'URGENCE");
	
	JSlider slider1 = new JSlider(JSlider.HORIZONTAL, -8, 8, 0);
	JSlider slider2 = new JSlider(JSlider.HORIZONTAL, -8, 8, 0);
	
	JPanel controle= new JPanel();
	
	public SysFerrPanel() {
		principalPanel();
		controlePanel();
	}	
	
	public void controlePanel(){
		int i =0;
		for (i=0;i<6;i++){        
			consMot[i] = new JButton("Moteur " + (i+1));
			consMot[i].addActionListener(this);
			consMot[i].setBounds(450, 480+i*40, 120, 20);
			consMot[i].setVisible(true);
			this.add(consMot[i]);
		}	
		
		/*for(i=0;i<2;i++){
			sensTr[i] = new JButton("SENS ");
			sensTr[i].addActionListener(this);
			sensTr[i].setBounds(550, 130+i*170, 200, 50);
			sensTr[i].setVisible(true);
			this.add(sensTr[i]);
		}*/
		
		
		urgence.addActionListener(this);
		urgence.setBackground(Color.RED);
		urgence.setBounds(700, 20, 200, 100);
		urgence.setVisible(true);
		this.add(urgence);
		
		slider1.setMinorTickSpacing(1);  
		slider1.setMajorTickSpacing(2);
		slider1.setPaintTicks(true);  
		slider1.setPaintLabels(true);
		slider1.setBounds(450,180,400,100);
		slider1.setVisible(true);
		slider1.addChangeListener(this);
		this.add(slider1);

		slider2.setMinorTickSpacing(1);  
		slider2.setMajorTickSpacing(2);
		slider2.setPaintTicks(true);  
		slider2.setPaintLabels(true);
		slider2.setBounds(450,350,400,100);
		slider2.setVisible(true);
		slider2.addChangeListener(this);
		this.add(slider2);
		
		/*algo.addActionListener(this);
		algo.setBounds(450, 50, 120, 20);
		algo.setVisible(true);
		this.add(algo);*/
	}
	
	
	public void principalPanel() {
		int i=0;
		addLabelTrain("Train 1 :", 10,20,0);
		addLabelTrain("Train 2 :",10,220,1);
		
		addLabel("Moteurs :", 10,450);
		for (i=0;i<6;i++){
			addLabel("ETAT_MOT_"+(i+1),30,480+i*40);
			addLabel(Train_Scada_EV3_Cassiopee23.mySys.getEtat_MoteursG(i),130,480+i*40);
			addLabel("CONS_MOT_"+(i+1),270,480+i*40);
			addLabel(Train_Scada_EV3_Cassiopee23.mySys.getCons_MoteursG(i),370,480+i*40);		
		}
		addLabel("Colorimètre :", 620,450);
		for (i=0;i<8;i++){
			addLabel("CAPTEUR_"+(i+1),700,480+i*30);
			addLabel(Train_Scada_EV3_Cassiopee23.mySys.getCouleurs(i),800,480+i*30);
		}
	}
	
	public void addLabelTrain(String s,int x, int y,int i){
		int a=0;
		boolean b=false;
		addLabel(s, x ,y);
		
		addLabel("Portion :", x+20,y+40);
		a=Train_Scada_EV3_Cassiopee23.mySys.train[i].getPortion();
		addLabel(Integer.toString(a), x+100,y+40);
		
		addLabel("Position :", x+20,y+80);		
		a=Train_Scada_EV3_Cassiopee23.mySys.train[i].getPos();
		addLabel(Integer.toString(a), x+100,y+80);
		
		addLabel("Vitesse :", x+20,y+120);
		a=Train_Scada_EV3_Cassiopee23.mySys.train[i].getV();
		addLabel(Integer.toString(a), x+100,y+120);
		
		addLabel("Priorite :", x+220,y+40);
		a=Train_Scada_EV3_Cassiopee23.mySys.train[i].getPrio();
		addLabel(Integer.toString(a), x+320,y+40);
		
		addLabel("Sens circuit:", x+220,y+80);		
		b=Train_Scada_EV3_Cassiopee23.mySys.train[i].getSens();
		addLabel(Boolean.toString(b), x+320,y+80);
		
		addLabel("Av / Ar :", x+220,y+120);
		b=Train_Scada_EV3_Cassiopee23.mySys.train[i].getDir();
		addLabel(Boolean.toString(b), x+320,y+120);
		
		addLabel("Absolu :", x+20,y+160);
		b=Train_Scada_EV3_Cassiopee23.mySys.train[i].getAbsolu();
		addLabel(Boolean.toString(b), x+120,y+160);
	}
	
	public void addLabel(String s,int x, int y){
		JLabel newLabel = new JLabel(s);
		newLabel.setBounds(x, y, 80, 20);
		newLabel.setVisible(true);
		this.add(newLabel);		
	}
	public void addLabel(int i,int x, int y){
		addLabel(Integer.toString(i),x,y);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		int i = 0;
		for (i=0;i<6;i++){
			if(source == consMot[i]){
				 Train_Scada_EV3_Cassiopee23.mySys.actionSetMoteur(i);
			}
		}
		if (source==algo){
			 Train_Scada_EV3_Cassiopee23.mySys.actionActivAlgo();
			 if(Train_Scada_EV3_Cassiopee23.mySys.getAlgo()){
				 this.algo.setText("Actif");
			 } else {
				 this.algo.setText("Inactif");
			 }
		}
		
		if (source==urgence){
			 Train_Scada_EV3_Cassiopee23.mySys.actionStop();
			 slider1.setValue(Train_Scada_EV3_Cassiopee23.mySys.train[0].getV());
			 slider2.setValue(Train_Scada_EV3_Cassiopee23.mySys.train[1].getV());
		}
		
		if (source==sensTr[0]){
			 Train_Scada_EV3_Cassiopee23.mySys.actionSetSens(0);
		}
		
		if (source==sensTr[1]){
			 Train_Scada_EV3_Cassiopee23.mySys.actionSetSens(1);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		if (source == slider1){
			Train_Scada_EV3_Cassiopee23.mySys.actionSlider1(((JSlider) source).getValue());
		}

		if (source == slider2){
			Train_Scada_EV3_Cassiopee23.mySys.actionSlider2(((JSlider) source).getValue());
		}
	}
}
