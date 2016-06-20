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

public class Emetteur_IR extends Device {
	
	
  	// BOOL registre
	private static final int TEST_CONNECT = 0; // ordi -> EV3
	private static final int QUITTER = 1;

  	// INT registre
	private static final int TRAIN_1_VITESSE=0; // ordi -> EV3
	private static final int TRAIN_2_VITESSE=1;
	private static final int TRAIN_1_AVAR=2;
	private static final int TRAIN_2_AVAR=3;

	public Emetteur_IR (String address, int port) throws UnknownHostException {
    	super(address, port);
	}

	public Emetteur_IR (String address) throws UnknownHostException {
    	super(address);
	}

	public Emetteur_IR () {
		super();
	}

	@Override
	int getUnitId() {
		return 0;
	}
	
	public void setTestConnect(boolean value) {
		this.setBool(Emetteur_IR.TEST_CONNECT, value);
	}
	
	public void setQuit(boolean value) {
		this.setBool(Emetteur_IR.QUITTER, value);
	}
	
	public void setVitesseInt(int i, int v , int avAr) {
		
		switch(i){
		case 0 :
			this.setInt(Emetteur_IR.TRAIN_1_VITESSE, v);
			this.setInt(Emetteur_IR.TRAIN_1_AVAR, avAr);
		    break;
		case 1 :
			this.setInt(Emetteur_IR.TRAIN_2_VITESSE, v);
			this.setInt(Emetteur_IR.TRAIN_2_AVAR, avAr);
		    break;
		default :
		}
	}

}
