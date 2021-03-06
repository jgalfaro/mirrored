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

public class SystemFerroviaire {
	
	Train[] train = new Train[2];
	
	private boolean ALGO=false;
	private boolean[] Etat_Moteurs =new boolean[6];
	private boolean[] Cons_Moteurs =new boolean[6];
	
	private int[] Couleurs = new int[8];

	public SystemFerroviaire(){
		train[0]= new Train();
		train[1]= new Train();
	}
	
	public void initSystem(int pos1,int pos2){
		int i=0;
		for(i=0;i<8;i++){
			this.Couleurs[i]=-2;
		}
		this.train[0].setId(0); // rouge
		this.train[1].setId(2); // bleu
		
		this.train[0].setPos(pos1);
		this.train[1].setPos(pos2);

		this.train[0].setV(0);
		this.train[1].setV(0);
		
		this.train[0].setPortionInit(1);
		this.train[1].setPortionInit(3);
		this.train[0].setSens(true);
		this.train[1].setSens(true);
	}

	public boolean getEtat_Moteurs(int i) {
		return this.Etat_Moteurs[i];
	}	
	
	public String getEtat_MoteursG(int i) {
		if(this.Etat_Moteurs[i])
		return "Ouvert";
		else return "Ferm�";
	}

	public void setEtat_Moteurs(boolean etat_Moteurs,int i) {
		this.Etat_Moteurs[i] = etat_Moteurs;
	}

	public boolean getCons_Moteurs(int i) {
		return this.Cons_Moteurs[i];
	}
	
	public String getCons_MoteursG(int i) {
		if(this.Cons_Moteurs[i])
		return "Ouvert";
		else return "Ferm�";
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
		setCons_Moteurs(!getEtat_Moteurs(i), i);		
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
	
	

	
	public void linkerCouleurPosition(){
		int i=0;
		for (i=0;i<8;i++){
			if (getCouleurs(i)==0&&this.train[0].getPos()!=i) {
				this.train[0].setPos(i);
			}			
			if ((getCouleurs(i)==2&&this.train[1].getPos()!=i )||(getCouleurs(i)==7&&this.train[1].getPos()!=i)) {
				this.train[1].setPos(i);
			}	
		}
	}
	
	public void detectEtChangePortion(int i){
		int prec = train[i].getPortion();
		int pos = train[i].getPos();
		boolean s = train[i].getAbsolu();
		
		train[i].setPortion(pos, s, prec);
		int next = train[i].getPortion();
		
		if (prec!=next){
			if(train[0].getPortion()==train[1].getPortion()){
				train[i].setPrio(0);
			} else { 
				train[i].setPrio(1);
				}
			
		}
		
	}
	
}


