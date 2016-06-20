package device.define;
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


import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;
//Modbus imports
import net.wimpi.modbus.ModbusDeviceIdentification;
import net.wimpi.modbus.procimg.SimpleDigitalOut;
import net.wimpi.modbus.procimg.SimpleProcessImage;
import net.wimpi.modbus.procimg.SimpleRegister;

public class CircuitCentre extends Device{


	  public CircuitCentre(String deviceAddr, int modbusPort, int modbusUnitId) {
		super(deviceAddr, modbusPort, modbusUnitId);
	}

	  public EV3 ev3 = null;
	  private float[] sample = new float[2]; // tmp color data
	  private int[] etat_mot = new int[2]; // tmp motor data
	  
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
	  
	  private EV3ColorSensor C1 = null;
	  private EV3ColorSensor C2 = null;
	  private EV3LargeRegulatedMotor M1 = null;
	  private EV3LargeRegulatedMotor M2 = null;
	  
	  
	@Override
	public void initModbusSpi() {
		
		this.spi = new SimpleProcessImage();		
		
		// Bool RO
		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // TEST_CONNECT = 0
		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // QUITTER = 1

		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // CONS_MOT_1 = 2
		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // CONS_MOT_2 = 3
		
		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // ETAT_MOT_1 = 4
		this.spi.addDigitalOut(new SimpleDigitalOut(false)); // ETAT_MOT_2 = 5

		this.spi.addRegister(new SimpleRegister(0)); // COULEUR_1=0;
		this.spi.addRegister(new SimpleRegister(0)); // COULEUR_2=0;
		
	}
	
	@Override
	public void initModbusIdentification() {
		this.mbIdent = new ModbusDeviceIdentification();
		this.mbIdent.setIdentification(0, "TELECOM SUD PARIS");
		this.mbIdent.setIdentification(1, "LEGO TRAIN");
		this.mbIdent.setIdentification(2, "1.0");
		this.mbIdent.setIdentification(3, "http://www.telecom-sudparis.eu");
		this.mbIdent.setIdentification(4, "CIRCUIT_CENTRE");
		
	}
	@Override
	public void initEV3() {	
		this.ev3 = (EV3) BrickFinder.getDefault();
		this.loadEV3();
		
	}
	@Override
	public void loadEV3() {
		C1 = new EV3ColorSensor(SensorPort.S1);
		C2 = new EV3ColorSensor(SensorPort.S2);
		M1 = new EV3LargeRegulatedMotor(MotorPort.A);
		M2 = new EV3LargeRegulatedMotor(MotorPort.D);
	}
	
	// fermeture du bloc
	@Override
	public void stopEV3() {
	
		C1.setFloodlight(false);
		C2.setFloodlight(false);
		C1.close();
		C2.close();
		M1.close();
		M2.close();
		
	}
	@Override
	public void run() {
	
		initMotors();
		initColors();
		
		while (Button.getButtons() != Button.ID_ESCAPE && getBool(QUITTER)!=true){
			
			indicationConnexion();
			setColors();
			actionMotors();
			drawScreen();

			Delay.msDelay(40);
		}
		
		stopEV3();
		
	}
	
	private void drawScreen() {
		LCD.clearDisplay();
		LCD.drawString("CircuitCentre", 0, 0);
		LCD.drawString("ETAT_MOT_1 : " + etat_mot[0], 0, 2);
		LCD.drawString("ETAT_MOT_2 : " + etat_mot[1], 0, 3);
		LCD.drawString("COULEUR  1 : " + sample[0], 0, 5);
		LCD.drawString("COULEUR  2 : " + sample[1], 0, 6);
	}
	
	private void indicationConnexion() {
		if (getBool(TEST_CONNECT) == true) {
			Button.LEDPattern(1); //Green
		}		
		else {
			Button.LEDPattern(2); // Red 
		}
	}
	
	private void initMotors(){
		while(!Button.DOWN.isDown()){
			////// MOTOR 1  INITIALISATION //////////
			while(!Button.ENTER.isDown()&& !Button.DOWN.isDown()){ // suivant ou quit
		
				LCD.clearDisplay();
				LCD.drawString("CircuitCentre", 0, 0);
				LCD.drawString("MOTOR_1 ferme - gauche",0,1);
				LCD.drawString("MOTOR_1 ouvre - droite ",0,2);
				LCD.drawString("MOTOR_1 suivant - OK ",0,3);
				LCD.drawString("quitter Init - bas ",0,4);
				if(Button.RIGHT.isDown()){ //ouverture
					M1.rotate(-20);
					setBool(ETAT_MOT_1,true);
					etat_mot[0]=1;
				}
				if(Button.LEFT.isDown()){ // fermeture
					M1.rotate(20);
					setBool(ETAT_MOT_1,false);
					etat_mot[0]=0;
				}

				Delay.msDelay(25);
			}
			/////// MOTOR 2 INITIALISATION //////////
			while(!Button.ENTER.isDown()&& !Button.DOWN.isDown()){
		
				LCD.clearDisplay();
				LCD.drawString("CircuitCentre", 0, 0);
				LCD.drawString("MOTOR_2 ferme - gauche",0,1);
				LCD.drawString("MOTOR_2 ouvre - droite ",0,2);
				LCD.drawString("MOTOR_2 suivant - OK ",0,3);
				LCD.drawString("quitter Init - bas ",0,4);
				if(Button.RIGHT.isDown()){
					M2.rotate(-20);
					setBool(ETAT_MOT_2,true);
					etat_mot[1]=1;
				}
				if(Button.LEFT.isDown()){
					M2.rotate(20);
					setBool(ETAT_MOT_2,false);
					etat_mot[1]=0;
				}

				Delay.msDelay(25);
			}
		}
	}
	
	private void initColors(){
		C1.setCurrentMode("ColorID");
		C1.setFloodlight(true);
		C2.setCurrentMode("ColorID");
		C2.setFloodlight(true);
	}
	
	private void actionMotors(){
		
		if(getBool(CONS_MOT_1)==true){
			if (getBool(ETAT_MOT_1) == false ) {
				M1.rotate(-20);
				setBool(ETAT_MOT_1,true);
				etat_mot[0]=1;
			} else {}
		}
		if(getBool(CONS_MOT_1)==false){
			if (getBool(ETAT_MOT_1) == true ) {
				M1.rotate(20);
				setBool(ETAT_MOT_1,false);
				etat_mot[0]=0;
			} else {}
		}
		
		
		if(getBool(CONS_MOT_2)==true){
			if (getBool(ETAT_MOT_2) == false ) {
				M2.rotate(-20);
				setBool(ETAT_MOT_2,true);
				etat_mot[1]=1;
			} else {}
		}
		if(getBool(CONS_MOT_2)==false){
			if (getBool(ETAT_MOT_2) == true ) {
				M2.rotate(20);
				setBool(ETAT_MOT_2,false);
				etat_mot[1]=0;
			} else {}
		}
		
		/*
		
		boolean a=false;
		boolean b=false;
		a=getBool(ETAT_MOT_1);
		b=getBool(ETAT_MOT_2);
		///// MOTOR 1 //////// 
		
		if(a!=getBool(CONS_MOT_1)){
			if (a==false){//équivalent à consigne vrai
				M1.rotate(-20);
				setBool(ETAT_MOT_1,true);
				etat_mot[0]=1;
			} else {
				M1.rotate(20);
				setBool(ETAT_MOT_1,false);
				etat_mot[0]=0;
			}
			
		}		
		
		///// MOTOR 2 /////////
		if(b!=getBool(CONS_MOT_2)){
			if (b==false){ //équivalent à consigne vrai
				M2.rotate(-20);
				setBool(ETAT_MOT_2,true);
				etat_mot[1]=1;
			} else {
				M2.rotate(20);
				setBool(ETAT_MOT_2,false);
				etat_mot[1]=0;
			}
		}
		
		*/
	}
	
	private void setColors(){
		C1.fetchSample(sample, 0);
		C2.fetchSample(sample, 1);
		setInt(COULEUR_1,(int) sample[0]); // cast float to int
		setInt(COULEUR_2,(int) sample[1]);
	}
	
	@Override
	public void beep() {
		Audio audio = this.ev3.getAudio();
		audio.systemSound(0);
	}
	public void beep2() {
		Audio audio = this.ev3.getAudio();
		audio.systemSound(4);
	}
}