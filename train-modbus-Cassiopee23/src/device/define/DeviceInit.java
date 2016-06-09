package device.define;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DeviceInit {
	//Network address (127.0.0.1, etc.)
	private static String deviceAddr = "";
	
	//Modbus params
	private static int modbusPort = 502;
	private static int modbusUnitId = 1;
	
	//Type of device (CircuitExtern ou CircuitCentre))
	private static String deviceType = "";
	
	
	/**
	 * Toll run
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		
		Device device = null;//new CircuitExtern("192.168.2.4", 502, 1);
		
		/*Lecture de device.ini pour choisir quelle classe se lance sur le EV3*/
		
		read_init("Device.ini");

		switch(deviceType){
		
		case "CircuitCentre":
			device = new CircuitCentre(deviceAddr, modbusPort,modbusUnitId);
		case "CircuitExtern":
			device = new CircuitExtern(deviceAddr, modbusPort,modbusUnitId);
		case "Emetteur_IR":
			device = new Emetteur_IR(deviceAddr, modbusPort,modbusUnitId);
				
		default: 
			System.err.println("Device type ("+deviceType+") unknown");
				
		}
		
		if (device != null) {
			device.initEV3();
			device.initModbus();
			
			device.beep();
			try {
				device.run();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			device.beep();
			
			device.stopModbus();
			device.stopEV3();
		}
		
		System.exit(0);
	}
	
	public static void read_init(String fileName) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open ini file");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Cannot read ini file");
			e.printStackTrace();
		}
		deviceAddr = p.getProperty("DEVICE_IP");
		modbusUnitId = Integer.parseInt(p.getProperty("DEVICE_MOD_ID"));
		modbusPort = Integer.parseInt(p.getProperty("DEVICE_MOD_PORT"));
		deviceType = p.getProperty("DEVICE_TYPE");
	}
}
