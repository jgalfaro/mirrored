package device.read;
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

import java.net.UnknownHostException;

public class CircuitCentre extends Device {

	// BOOL registre
	private static final int TEST_CONNECT = 0; // ordi -> EV3
	private static final int QUITTER = 1; 
	
	private static final int ETAT_MOT_1 = 2 ;  // EV3 -> ordi
	private static final int ETAT_MOT_2 = 3 ;
	
	private static final int CONS_MOT_1 = 4 ; // ordi -> EV3
	private static final int CONS_MOT_2 = 5 ;
	  	// INT registre
	private static final int COULEUR_1=0;  // EV3 -> ordi
	private static final int COULEUR_2=1;

	public CircuitCentre (String address, int port) throws UnknownHostException {
    	super(address, port);
	}

	public CircuitCentre (String address) throws UnknownHostException {
    	super(address);
	}

	public CircuitCentre () {
		super();
	}

	@Override
	int getUnitId() {
		return 0;
	}
	
	public boolean getEtatMot(int i) {
		
		switch(i){
		case 1 :
			return this.getBoolRW(CircuitCentre.ETAT_MOT_1);
		case 2 :
			return this.getBoolRW(CircuitCentre.ETAT_MOT_2);
		default : 
			return false;
		}
	}
	
	public void setConsMot(int i , boolean value) {
		switch(i){
		case 1 :
			this.setBool(CircuitCentre.CONS_MOT_1, value);
		case 2:
			this.setBool(CircuitCentre.CONS_MOT_2, value);		
		default : 
		}
	}
	
	public void setTestConnect(boolean value) {
		this.setBool(CircuitCentre.TEST_CONNECT, value);
	}
	
	public void setQuit(boolean value) {
		this.setBool(CircuitCentre.QUITTER, value);
	}
	
	public int getCouleurInt(int i) {
		
		switch(i){
		case 1 :
			return this.getIntRW(COULEUR_1);
		case 2 :
			return this.getIntRW(COULEUR_2);
		default :
			return 0;
		}
	}

}
