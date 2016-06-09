package control;


/*
*
@author : Laurent CETIN & Antoine FROMENTIN
@version : 1.0
* T�l�com SudParis - 2016
* Projet Cassiop�e 23
*
* Syst�me Scada - Train Mindstorm - Lejos EV3
*
* */


public class Train {
	private int id;           // pour le num�ro de train, couleur 
	private int vitesse; 
	private boolean sensCircuit;
	private boolean directionMoteur;
	private int priorite ;  // permet de d�finir un code de circulation
	private int position ;  // donn�e par les capteurs sur le circuit
		
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
