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


public class Train {
	private int id;           // pour le numéro de train, couleur 
	private int vitesse; 
	private boolean sensCircuit;
	private boolean directionMoteur;
	private boolean sensAbsolu; // vrai pour horaire , ou de capteur 6 à 5
	private int priorite ;  // permet de définir un code de circulation
	private int position ;  // donnée par les capteurs sur le circuit
	private int portion ;
		
	public void setPos(int pos){
		this.position=pos;
	}

	public int getPos(){
		return this.position;
	}
	
	public void setPrio(int prio){
		this.priorite=prio;
	}

	public int getPrio(){
		return this.priorite;
	}
	
	public void setDir(boolean direction){
		this.directionMoteur=direction;
	}
	
	public boolean getDir(){
		return this.directionMoteur;
	}
	
	public void setSens(boolean sens){
		this.sensCircuit=sens;
	}
	
	public boolean getSens(){
		return this.sensCircuit;
	}
	
	public int getV() {
		return this.vitesse;
	}

	public void setV(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setAbsolu(boolean sens, boolean dir){
		if (dir){
			this.sensAbsolu=sens;
		} else {
			this.sensAbsolu= !sens;
		}
	}
	
	public boolean getAbsolu(){
		return this.sensAbsolu;
	}

	public int getPortion() {
		return portion;
	}
	
	public void setPortionInit(int port){
		this.portion=port;
	}

	public void setPortion(int pos, boolean sensAbs, int portionPrec) {
		if (sensAbs){			
			switch(this.position){
			case 0:
				this.portion=2;
			    break;
			case 1:
				//this.portion=;//ne dois pas changer , ne peut pas arriver en direct, on sort en direct du 6, car on fait changement de sens.
			    
			    break;
			case 2:
				this.portion=2;
			    break;		
			case 3:
				this.portion=1;
			    break;
			case 4:
				this.portion=4;
			    break;
			case 5:
				this.portion=1;
				break;
			case 6:
				this.portion=4; // en théorie ca ne change pas
			    break;
			case 7:			
				this.portion=3; // en théorie ca ne change pas
			    break;
			}
		} else {
			switch(this.position){
			case 0:
				this.portion=3;
			    break;
			case 1:
				if(portionPrec==3){
					this.portion=5;
				} else {
					this.portion=3;
				}
			    break;
			case 2:
				this.portion=2;	
			    break;		
			case 3:
				this.portion=2;
			    break;
			case 4:
				this.portion=1;
			    break;
			case 5:
				this.portion=5;
			    break;
			case 6:
				this.portion=4; // en théorie ca ne change pas
			    break;
			case 7:
				this.portion=4; // en théorie ca ne change pas
			    break;
			}			
		}
	}
}
