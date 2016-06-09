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

import java.lang.Math;
import lejos.hardware.Audio;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.device.IRLink;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
//Modbus imports
import net.wimpi.modbus.ModbusDeviceIdentification;
import net.wimpi.modbus.procimg.SimpleDigitalIn;
import net.wimpi.modbus.procimg.SimpleProcessImage;
import net.wimpi.modbus.procimg.SimpleRegister;

public class Emetteur_IR extends Device{


	  public Emetteur_IR(String deviceAddr, int modbusPort, int modbusUnitId) {
		super(deviceAddr, modbusPort, modbusUnitId);
	}

	  public EV3 ev3 = null;
	  private IRLink E1 = null;
	  
	  	// BOOL registre
	  private static final int TEST_CONNECT = 0;
	  private static final int QUITTER = 1;

	  	// INT registre
	  private static final int TRAIN_1_VITESSE=0;
	  private static final int TRAIN_2_VITESSE=1;
	  private static final int TRAIN_1_AVAR=2;
	  private static final int TRAIN_2_AVAR=3;	

	  private int tV1 = 0 ;
	  private int tV2 = 0 ;

	  private int avAr1 = 0 ;
	  private int avAr2 = 0 ;
	  
	@Override
	public void initModbusSpi() {
		
		this.spi = new SimpleProcessImage();		
		// Bool RO
		this.spi.addDigitalIn(new SimpleDigitalIn(false)); // TEST_CONNECT = 0
		this.spi.addDigitalIn(new SimpleDigitalIn(false)); // QUITTER = 1
		
		this.spi.addInputRegister(new SimpleRegister(0));  // TRAIN_1_VITESSE = 0 initialisé à 0
		this.spi.addInputRegister(new SimpleRegister(0));  // TRAIN_2_VITESSE = 1 initialisé à 0
		this.spi.addInputRegister(new SimpleRegister(0));  // TRAIN_1_AVAR = 2
		this.spi.addInputRegister(new SimpleRegister(0));  // TRAIN_2_AVAR = 3
		
	}
	@Override
	public void initModbusIdentification() {		
		
		this.mbIdent = new ModbusDeviceIdentification();
		this.mbIdent.setIdentification(0, "TELECOM SUD PARIS");
		this.mbIdent.setIdentification(1, "LEGO TRAIN");
		this.mbIdent.setIdentification(2, "1.0");
		this.mbIdent.setIdentification(3, "http://www.telecom-sudparis.eu");
		this.mbIdent.setIdentification(4, "EMETTEUR_IR");
		
	}
	@Override
	public void initEV3() {	
		this.ev3 = (EV3) BrickFinder.getDefault();
		this.loadEV3();
		
	}
	@Override
	public void loadEV3() {
		E1 = new IRLink(SensorPort.S3);

	}
	
	// fermeture du bloc
	@Override
	public void stopEV3() {
		E1.close();
		
	}
	@Override
	public void run() {
		while (Button.getButtons() != Button.ID_ESCAPE && getBool(QUITTER)!=true){
			
			indicationConnexion();
			sendVitesse();
			drawScreen();
		}
		
	}
	private void sendVitesse(){

		tV1=getIntRO(TRAIN_1_VITESSE);
		tV2=getIntRO(TRAIN_2_VITESSE);

		avAr1=getIntRO(TRAIN_1_AVAR);
		avAr1=getIntRO(TRAIN_2_AVAR);


		E1.sendPFComboDirect(0,avAr1,1);
		E1.sendPFComboDirect(3,avAr2,1);
		Delay.msDelay(Math.min(tV1,tV2));
		E1.sendPFComboDirect(0,avAr1,0);
		E1.sendPFComboDirect(3,avAr2,1);
		Delay.msDelay(Math.max(tV1,tV2)-Math.min(tV1,tV2));		
		E1.sendPFComboDirect(0,avAr1,0);
		E1.sendPFComboDirect(3,avAr2,0);
		Delay.msDelay(8-Math.max(tV1,tV2));

		
	}
	
	
	private void drawScreen() { 
		LCD.clearDisplay();

		LCD.drawString("Emetteur_IR", 0, 0);
		LCD.drawString("Vitesse 1 / AvAr: " + getIntRO(TRAIN_1_VITESSE) + getIntRO(TRAIN_1_AVAR),0,2); 
		LCD.drawString("Vitesse 2 / AvAr : " + getIntRO(TRAIN_2_VITESSE) + getIntRO(TRAIN_2_AVAR),0,3);
	}
	
	private void indicationConnexion() {
		if (getBool(TEST_CONNECT) == true) {
			Button.LEDPattern(1); //Green
		}		
		else {
			Button.LEDPattern(2); // Red 
		}
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
