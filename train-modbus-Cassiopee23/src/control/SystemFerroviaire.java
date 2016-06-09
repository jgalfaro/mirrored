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

public class SystemFerroviaire {
	
	Train[] train = new Train[2];
	
	private boolean ALGO=true;
	private boolean[] Etat_Moteurs =new boolean[6];
	private boolean[] Cons_Moteurs =new boolean[6];
	
	private int[] Couleurs = new int[8];

	public SystemFerroviaire(){
		train[0]= new Train();
		train[1]= new Train();
		
	}
	public void initSystem(int pos1,int pos2){
		this.train[0].setId(1); // rouge
		this.train[1].setId(2); // bleu
		
		this.train[0].setPos(pos1);
		this.train[1].setPos(pos2);

		this.train[0].setV(0);
		this.train[1].setV(0);
		
	}

	public boolean getEtat_Moteurs(int i) {
		return this.Etat_Moteurs[i];
	}	
	
	public String getEtat_MoteursG(int i) {
		if(this.Etat_Moteurs[i])
		return "fermé";
		else return "ouvert";
	}

	public void setEtat_Moteurs(boolean etat_Moteurs,int i) {
		this.Etat_Moteurs[i] = etat_Moteurs;
	}

	public boolean getCons_Moteurs(int i) {
		return this.Cons_Moteurs[i];
	}
	
	public String getCons_MoteursG(int i) {
		if(this.Cons_Moteurs[i])
		return "fermé";
		else return "ouvert";
	}

	public void setCons_Moteurs(boolean cons_Moteurs, int i) {
		this.Cons_Moteurs[i] = cons_Moteurs;
	}

	public int getCouleurs(int i) {
		return this.Couleurs[i];
	}

	public void setCouleurs(int couleurs, int i) {
		this.Couleurs[i] = couleurs;
	}
	
	public int getIdTrain(int i){
		return this.train[i].getId();
	}
	public void actionSetMoteur(int i) {
		// TODO Auto-generated method stub
		
	}
	public void actionSlider1(int value) {
		
		if (Integer.signum(value)>0){
			this.train[0].setDir(true);
		} else {
			this.train[0].setDir(false);
		}
		this.train[0].setV(Math.abs(value));
		
	}	
	
	public void actionSlider2(int value) {
		if (Integer.signum(value)>0){
			this.train[1].setDir(true);
		} else {
			this.train[1].setDir(false);
		}
		this.train[1].setV(Math.abs(value));
	}
	public void actionSetSens(int i) {
		this.train[i].setSens(!this.train[i].getSens());
		
	}
	public void actionActivAlgo() {
		this.ALGO=(!this.ALGO);
	}
	
	public boolean getAlgo(){
		return this.ALGO;
	}
	public void actionStop() {
		int i=0;
		for(i=0;i<2;i++){
			this.train[i].setV(0);
		}
		
	}
}


