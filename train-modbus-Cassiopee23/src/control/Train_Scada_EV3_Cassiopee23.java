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

	public static Iterator<CircuitCentre> iterCC = null;
	public static Iterator<CircuitExtern> iterCE = null;
	public static Iterator<Emetteur_IR> iterIR = null;
	
	public static void main(String[] args) throws Exception {


		window = new HomeFrame();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				window.setVisible(true);
			}
		});
		
			
for(;;){
	linker();
		// ATTENDRE TOP
		// linker variable ev3 aux variables du fichier train
		// BOUCLE INFINIE - ARRET SI UN QUIT ?!
			//ALGO
	window.afficherSysFerrPanel();
    		Delay.msDelay(250);
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
	
	
	private static void linker(){
		linkerMot();
		linkerCol();
		linkerV();
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
		mySys.setEtat_Moteurs(circuitsExtern.get(0).getEtatMot(1),5);
		circuitsExtern.get(0).setConsMot(2,mySys.getCons_Moteurs(5));
		
	}
	
	private static void linkerCol(){
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(1),0);
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(2),1);
		mySys.setCouleurs(circuitsExtern.get(0).getCouleurInt(3),2);
		
		mySys.setCouleurs(circuitsCentre.get(0).getCouleurInt(1),3);
		mySys.setCouleurs(circuitsCentre.get(0).getCouleurInt(2),4);
		
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(1),5);
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(2),6);
		mySys.setCouleurs(circuitsExtern.get(1).getCouleurInt(3),7);
	}
	
	
	private static void linkerV(){
		int i=0;
		int tmp1=0;
		for(i=0;i<2;i++){
			tmp1=mySys.train[i].getV();
			if (mySys.train[i].getDir()){
				emetteurs_IR.get(0).setVitesseInt(i+1, tmp1, 1);
				emetteurs_IR.get(1).setVitesseInt(i+1, tmp1, 1);
			} else {
				emetteurs_IR.get(0).setVitesseInt(i+1, tmp1, 2);
				emetteurs_IR.get(1).setVitesseInt(i+1, tmp1, 2);
			}
		}
	}

	/*private static void prioriteTrains(){
		
		
		
	}*/
}
