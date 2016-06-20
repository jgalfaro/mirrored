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

package control;



import java.net.UnknownHostException;
import java.util.*;

import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;
import device.read.CircuitCentre;
import device.read.CircuitExtern;
import device.read.Emetteur_IR;
import lejos.utility.Delay;
import net.wimpi.modbus.ModbusDeviceIdentification;

public class Train_Scada_EV3_Cassiopee23 {
	

	public static SystemFerroviaire mySys = new SystemFerroviaire();

	public static HomeFrame window;
	
	public static List<CircuitCentre> circuitsCentre = new ArrayList<CircuitCentre>();
	public static List<CircuitExtern> circuitsExtern = new ArrayList<CircuitExtern>();
	public static List<Emetteur_IR> emetteurs_IR = new ArrayList<Emetteur_IR>();

	 public static Emetteur_IR testEm = null;
	public static Iterator<CircuitCentre> iterCC = null;
	public static Iterator<CircuitExtern> iterCE = null;
	public static Iterator<Emetteur_IR> iterIR = null;
	
	
	//private static boolean topExit=false;
	//private static boolean topGo=false;
	
	public static void main(String[] args) throws Exception {


		window = new HomeFrame();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				window.setVisible(true);
			}
		});

    	Train_Scada_EV3_Cassiopee23.addCircuitCentre();
    	Train_Scada_EV3_Cassiopee23.addCircuitExtern();
    	Train_Scada_EV3_Cassiopee23.addCircuitExtern();
    	Train_Scada_EV3_Cassiopee23.addEmetteur_IR();
    	Train_Scada_EV3_Cassiopee23.addEmetteur_IR();
    	
    	mySys.initSystem(7, 3);
    	
			for(;;){	

				//window.afficherAlgoPanel();
				//testAlgoForRun();
				linker(false);

				Delay.msDelay(25);
			}

//exit();
	}
	
	public static void exit() {
		
		iterCC = circuitsCentre.iterator();
	    while (iterCC.hasNext()) {
	    	iterCC.next().setQuit(true);
	    	//del du iter
	    	}

		iterCE = circuitsExtern.iterator();
	    while (iterCE.hasNext()) {
	    	iterCE.next().setQuit(true);
	    	//del du iter
	    	}

		iterIR = emetteurs_IR.iterator();
	    while (iterIR.hasNext()) {
	    	iterIR.next().setQuit(true);
	    	//del du iter
	    	}
		
		System.exit(0);
	}	
	
	public static void addEmetteur_IR() {
		Emetteur_IR myBlock = null;
		
		//We connect
		myBlock = connectNewEmIR();
		if (myBlock == null) {
			return;
		}
		//testEm=myBlock;
		myBlock.setTestConnect(true);
		emetteurs_IR.add(myBlock);
		window.addMessage("new brick added - EmetteurIR");
	}
	
	public static void addCircuitCentre() {
		CircuitCentre myBlock = null;
		
		//We connect
		myBlock = connectNewCirCen();
		if (myBlock == null) {
			return;
		}

		myBlock.setTestConnect(true);
		circuitsCentre.add(myBlock);
		window.addMessage("new brick added - CircuitCentre");
	}	
		
	public static void addCircuitExtern() {
		CircuitExtern myBlock = null;
		
		//We connect
		myBlock = connectNewCirExt();
		if (myBlock == null) {
			return;
		}
		myBlock.setTestConnect(true);
		circuitsExtern.add(myBlock);
		window.addMessage("new brick added - CircuitExtern");
	}
		
	public static void delEmetteur_IR(Emetteur_IR myBlock) {
		myBlock.close();
		window.addMessage("Emetteur_IR removed");
	}
	
	public static void delCircuitCentre(CircuitCentre myBlock) {
		myBlock.close();
		window.addMessage("CircuitCentre removed");
	}
	
	public static void delCircuitExtern(CircuitExtern myBlock) {
		myBlock.close();
		window.addMessage("CircuitExtern removed");
	}
	
	private static Emetteur_IR connectNewEmIR() {
		Emetteur_IR myBlock = null;
		String ipBlock = JOptionPane.showInputDialog("Emetteur_IR - Block IP Address:");
				
		try {
			myBlock = new Emetteur_IR(ipBlock);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(window, "Impossible to set IP");
			return null;
		}
					
		try {
			if (!myBlock.connect()) {
				return null;
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(window, "Impossible to establish connection to " + ipBlock);
			return null;
		}
		
		//Test if the DEVICE_TYPE is good
		ModbusDeviceIdentification ident = myBlock.getDeviceIdentification(1, 0);
		
		if (!ident.getIdentification(1).equals("LEGO TRAIN") && !ident.getIdentification(4).equals("EMETTEUR_IR")) {
			JOptionPane.showMessageDialog(window, "Unattended device");
			return null;			
		}
		return myBlock;
	}
	
	private static CircuitCentre connectNewCirCen() {
		CircuitCentre myBlock = null;
		String ipBlock = JOptionPane.showInputDialog("Circuit Centre - Block IP Address:");
		
		try {
			myBlock = new CircuitCentre(ipBlock);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(window, "Impossible to set IP");
			return null;
		}
					
		try {
			if (!myBlock.connect()) {
				return null;
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(window, "Impossible to establish connection to " + ipBlock);
			return null;
		}
		
		//Test if the DEVICE_TYPE is good
		ModbusDeviceIdentification ident = myBlock.getDeviceIdentification(1, 0);
	
		if (!ident.getIdentification(1).equals("LEGO TRAIN") && !ident.getIdentification(4).equals("CIRCUIT_CENTRE")) {
			JOptionPane.showMessageDialog(window, "Unattended device");
			return null;			
		}
		return myBlock;
	}	
	
	private static CircuitExtern connectNewCirExt() {
		CircuitExtern myBlock = null;
		String ipBlock = JOptionPane.showInputDialog("Circuit Externe - Block IP Address:");
		
		try {
			myBlock = new CircuitExtern(ipBlock);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(window, "Impossible to set IP");
			return null;
		}
					
		try {
			if (!myBlock.connect()) {
				return null;
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(window, "Impossible to establish connection to " + ipBlock);
			return null;
		}
		
		//Test if the DEVICE_TYPE is good
		ModbusDeviceIdentification ident = myBlock.getDeviceIdentification(1, 0);
	
		if (!ident.getIdentification(1).equals("LEGO TRAIN") && !ident.getIdentification(4).equals("CIRCUIT_EXTERN")) {
			JOptionPane.showMessageDialog(window, "Unattended device");
			return null;			
		}
		return myBlock;
	}
	
	
	private static void linker(boolean algo){
		mySys.linkerCouleurPosition();
		mySys.detectEtChangePortion(0);
		mySys.detectEtChangePortion(1);
		mySys.train[0].setAbsolu(mySys.train[0].getSens(), mySys.train[0].getDir());
		mySys.train[1].setAbsolu(mySys.train[1].getSens(), mySys.train[1].getDir());
		linkerMot();
		linkerCol();
		linkerV();
		if(algo){	window.afficherAlgoPanel();
		} else { window.afficherSysFerrPanel();
		}
		Delay.msDelay(25);
	}
	private static void linkerMot(){
		
		//mot1 - ev3 droite
		mySys.setEtat_Moteurs(circuitsExtern.get(0).getEtatMot(1),0);
		circuitsExtern.get(0).setConsMot(1,mySys.getCons_Moteurs(0));
		
		//mot2 - ev3 centre
		mySys.setEtat_Moteurs(circuitsCentre.get(0).getEtatMot(1),1);
		circuitsCentre.get(0).setConsMot(1,mySys.getCons_Moteurs(1));
		
		//mot3 - ev3 gauche
		mySys.setEtat_Moteurs(circuitsExtern.get(1).getEtatMot(1),2);
		circuitsExtern.get(1).setConsMot(1,mySys.getCons_Moteurs(2));	
	
		//mot4 - ev3 gauche
		mySys.setEtat_Moteurs(circuitsExtern.get(1).getEtatMot(2),3);
		circuitsExtern.get(1).setConsMot(2,mySys.getCons_Moteurs(3));
		
		//mot2 - ev3 centre
		mySys.setEtat_Moteurs(circuitsCentre.get(0).getEtatMot(2),4);
		circuitsCentre.get(0).setConsMot(2,mySys.getCons_Moteurs(4));
		
		//mot6 - ev3 droite
		mySys.setEtat_Moteurs(circuitsExtern.get(0).getEtatMot(2),5);
		circuitsExtern.get(0).setConsMot(2,mySys.getCons_Moteurs(5));
		
	}
	
	private static void linkerCol(){
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(1),0); // capteur 1
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(2),1);
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(3),2);
		
		mySys.setCouleurs(circuitsCentre.get(0).getCouleurInt(1),3);
		
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(1),4);
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(2),5);
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(3),6);
		
		mySys.setCouleurs(circuitsCentre.get(0).getCouleurInt(2),7); // capteur 8
	}
	
	
	private static void linkerV(){
		int i=0;
		int tmp1=0;
		for(i=0;i<2;i++){
			tmp1=mySys.train[i].getV();
			
			if (mySys.train[i].getDir()){
				if (tmp1==0){
					emetteurs_IR.get(0).setVitesseInt(i, tmp1, 0);
					emetteurs_IR.get(1).setVitesseInt(i, tmp1, 0);
				} else {
					emetteurs_IR.get(0).setVitesseInt(i, tmp1, 1);
					emetteurs_IR.get(1).setVitesseInt(i, tmp1, 1);
				}
			} else {
				if (tmp1==0) {
					emetteurs_IR.get(0).setVitesseInt(i, tmp1, 0);
					emetteurs_IR.get(1).setVitesseInt(i, tmp1, 0);
				} else {
					emetteurs_IR.get(0).setVitesseInt(i, tmp1, 2);
					emetteurs_IR.get(1).setVitesseInt(i, tmp1, 2);
				}
			}
		}
	}

	/*public static void topExit() {
		topExit=true;
		
	}

	public static void topGo(boolean value) {
		if (value) {topGo=false;}
		else {topGo=true;}
	}*/

	/*private static void prioriteTrains(){
		
		
		
	}*/
	
	
	
	
	
	
	////////////// DEG /////////
	
	
	public static void testAlgoForRun(){
		if (mySys.train[0].getPortion()==mySys.train[1].getPortion()){
			algoCollision(mySys.train[0].getPortion());
		}
	}
	
	public static void algoCollision(int i){// doit prendre le truc d'urgence
		int j=0;
		int k=0;
		

			
		if (mySys.train[0].getAbsolu()==mySys.train[1].getAbsolu()){
			
			if (mySys.train[0].getPrio()==1){ // train j a la priorité
				j=0;
				k=1;
			} else { 
				j=1;
				k=0;
			}
			
			int vk=mySys.train[k].getV();
				while(mySys.train[j].getPortion()== mySys.train[k].getPortion()){
					mySys.train[k].setV(0);
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					linkerV();
				}
				mySys.train[k].setV(vk);
				linkerV();
		} else {
			
			if (mySys.train[0].getAbsolu()==true){ // train j a la priorité
				j=0;
				k=1;
			} else { 
				j=1;
				k=0;
			}
					
					
			if (i==1){			
				
				int vk=mySys.train[k].getV();
				mySys.setCons_Moteurs(true,3);
				linker(true);
				
				while(mySys.getCouleurs(6)!= 0 && mySys.getCouleurs(6)!=7 && mySys.getCouleurs(6)!=2){
					mySys.train[j].setV(0);
					mySys.train[k].setV(-1);
					linker(true);
				}
				
				mySys.train[k].setV(0);
				mySys.setCons_Moteurs(false,3);
				linker(true);
				
				while(mySys.getCouleurs(7)!= 0 && mySys.getCouleurs(7)!= 7 && mySys.getCouleurs(7)!=2){
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					mySys.train[k].setV(0);
					linker(true);
				}
				mySys.train[k].setV(vk);
				linker(true);
				
			}
			if (i==2){

				int vk=mySys.train[k].getV();
				mySys.train[k].setV(0);
				linker(true);
				
				while(mySys.getCouleurs(0)!= 0 && mySys.getCouleurs(0)!= 7 && mySys.getCouleurs(0)!=2){
					mySys.train[j].setV(1);mySys.train[j].setDir(false);
					mySys.train[k].setV(0);
					linker(true);
				}
				

				while(mySys.getCouleurs(0)== 0 || mySys.getCouleurs(0)==2 || mySys.getCouleurs(0)==7){
					mySys.train[j].setV(1);mySys.train[j].setDir(false);
					mySys.train[k].setV(0);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				linker(true);
				////
				while(mySys.getCouleurs(3)!= 0 && mySys.getCouleurs(3)!=2 && mySys.getCouleurs(3)!=7){
					mySys.train[k].setV(-1);
					mySys.train[j].setV(0);
					linker(true);
				}
				

				while(mySys.getCouleurs(3)== 0 || mySys.getCouleurs(3)==2 || mySys.getCouleurs(3)==7){
					mySys.train[k].setV(-1);
					mySys.train[j].setV(0);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				mySys.setCons_Moteurs(true,1);
				mySys.setCons_Moteurs(false,0);
				linker(true);

				while(mySys.getCouleurs(2)!= 0 && mySys.getCouleurs(2)!=2 && mySys.getCouleurs(2)!=7){
					mySys.train[j].setV(0);
					mySys.train[k].setV(1);mySys.train[j].setDir(true);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				linker(true);
				
				while(mySys.getCouleurs(3)!= 0 && mySys.getCouleurs(3)!=2 && mySys.getCouleurs(3)!=7){
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					mySys.train[k].setV(0);
					linker(true);
				}

				mySys.setCons_Moteurs(false,1);
				mySys.train[k].setV(vk);
				linker(true);
				
				
				
				
				
			}
			if (i==3){
				
				int vk=mySys.train[k].getV();
				mySys.setCons_Moteurs(true,0);
				linker(true);
				
				while(mySys.getCouleurs(2)!= 0 && mySys.getCouleurs(2)!=2 && mySys.getCouleurs(2)!=7){
					mySys.train[j].setV(0);
					mySys.train[k].setV(-1);
				}
				
				mySys.train[k].setV(0);
				mySys.setCons_Moteurs(false,0);
				linker(true);
				
				while(mySys.getCouleurs(3)!= 0 && mySys.getCouleurs(3)!=2 && mySys.getCouleurs(3)!=7){
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					mySys.train[k].setV(0);
					linker(true);
				}
				mySys.train[k].setV(vk);
				linker(true);
				
			}
			if (i==4){
				

				int vk=mySys.train[k].getV();
				mySys.train[k].setV(0);
				linker(true);
				
				while(mySys.getCouleurs(4)!= 0 && mySys.getCouleurs(4)!=2 && mySys.getCouleurs(4)!=7){
					mySys.train[j].setV(1);mySys.train[j].setDir(false);
					mySys.train[k].setV(0);
					linker(true);
				}
				

				while(mySys.getCouleurs(4)== 0 || mySys.getCouleurs(4)==2 || mySys.getCouleurs(4)==7){
					mySys.train[j].setV(1);mySys.train[j].setDir(false);
					mySys.train[k].setV(0);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				linker(true);
				////
				while(mySys.getCouleurs(7)!= 0 && mySys.getCouleurs(7)!=2 && mySys.getCouleurs(7)!=7){
					mySys.train[k].setV(-1);
					mySys.train[j].setV(0);
					linker(true);
				}
				

				while(mySys.getCouleurs(7)== 0 || mySys.getCouleurs(7)==2 || mySys.getCouleurs(7)==7){
					mySys.train[k].setV(-1);
					mySys.train[j].setV(0);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				mySys.setCons_Moteurs(true,4);
				mySys.setCons_Moteurs(false,3);
				linker(true);

				while(mySys.getCouleurs(6)!= 0 && mySys.getCouleurs(6)!=2 && mySys.getCouleurs(6)!=7){
					mySys.train[j].setV(0);
					mySys.train[k].setV(1);mySys.train[j].setDir(true);
					linker(true);
				}
				
				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				linker(true);
				
				while(mySys.getCouleurs(6)!= 0 && mySys.getCouleurs(6)!=2 && mySys.getCouleurs(6)!=7){
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					mySys.train[k].setV(0);
					linker(true);
				}

				mySys.setCons_Moteurs(false,4);
				
				mySys.train[k].setV(vk);
				linker(true);
				
				
				
			}
			if (i==5){

				mySys.train[j].setV(0);
				mySys.train[k].setV(0);
				mySys.setCons_Moteurs(true,3);
				linker(true);
				while(mySys.getCouleurs(6)!= 0 && mySys.getCouleurs(6)!=2 && mySys.getCouleurs(6)!=7){

					mySys.train[j].setV(2);
					mySys.train[k].setV(-1);
					linker(true);
				}

				mySys.setCons_Moteurs(false,3);
				mySys.train[k].setV(0);
				linker(true);

				while(mySys.getCouleurs(7)!= 0 && mySys.getCouleurs(7)!=2 && mySys.getCouleurs(7)!=7){

					mySys.train[k].setV(0);
					mySys.train[j].setV(1);mySys.train[j].setDir(true);
					linker(true);
				}

				int vk=mySys.train[k].getV();
				mySys.train[k].setV(vk);
				linker(true);
				
				
				
			}
		}
	}
}
