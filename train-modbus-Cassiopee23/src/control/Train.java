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
	private int priorite ;  // permet de définir un code de circulation
	private int position ;  // donnée par les capteurs sur le circuit
		
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
}
