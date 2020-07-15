package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ISS will start sensor threads and collect data from them periodically to send 
 * to the GUI. It will also gather data from the GUI in order to check which sensors are
 * supposed to be read. Transfer between sensors, ISS, and GUI will be serialized.
 * 
 * @author Andrew Josten
 * Borrowing some code from group 1's ISS
 *
 */
public class IntegratedSensorSuite extends Thread implements Serializable {

	/**
	 * Used for serialization process
	 */
	private static final long serialVersionUID = 4L;
	
	/**
	 * Console can get data from multiple (8) different transmitters. ID is usually just 1
	 */
	private int transmitterID = 1;
	
	/**
	 *  Timer which can be cancel() by Main or Test classes 
	 */
	public static Timer timer;
	
	//Sensor objects (transient, since we just want the data sent to GUI, not the objects)
    //public transient HumiditySensor myHumiditySensor;
    
    /*
     * Put the other sensors here
     */
    
    /**
     * This data field will hold a map of sensor names and their relevant data readings
     * for the GUI to collect and read from.
     * NOTE: Wind direction will be rendered in degrees (0-359)
     */
    public HashMap<String, Double> sensorData;
    
    public IntegratedSensorSuite(int id) {
    	transmitterID = id;   	
    	//myHumiditySensor = new HumiditySensor();
    	
    	/*
    	 * construct all sensors here
    	 */
    	
    	//Setup map of <variable, value>
    	sensorData = new HashMap<String, Double>();
   	
    	sensorData.put("RainFall", 0.0);
    	sensorData.put("RainRate", 0.0);
    	sensorData.put("WindSpeed", 0.0);
    	sensorData.put("WindDirection", 0.0);
    	sensorData.put("InnerTemp", 0.0);
    	sensorData.put("OuterTemp", 0.0);
    	sensorData.put("UVIndex", 0.0);
    	sensorData.put("UVDose", 0.0);
    	sensorData.put("InnerHumidity", 0.0);
    	sensorData.put("OuterHumidity", 0.0);
    	
    	sensorData.put("Evotranspiration", 0.0);
    	sensorData.put("LeafWetness", 0.0);
    	sensorData.put("SoilMoisture", 0.0);
    	sensorData.put("Dewpoint", 0.0);
    	sensorData.put("WindChill", 0.0);
    	sensorData.put("HeatIndex", 0.0);	
    	
    }
    
    /**
     * Calls periodically to poll the sensor data and update the map
     */
    public void updateData() {    	
    	sensorData.put("RainFall", 0.0);
    	sensorData.put("RainRate", 0.0);
    	sensorData.put("WindSpeed", 0.0);
    	sensorData.put("WindDirection", 0.0);
    	sensorData.put("InnerTemp", 0.0);
    	sensorData.put("OuterTemp", 0.0);
    	sensorData.put("UVIndex", 0.0);
    	sensorData.put("UVDose", 0.0);
    	sensorData.put("InnerHumidity", 0.0);
    	sensorData.put("OuterHumidity", 0.0); //((HumiditySensor) Main.deserialization("Humidity_S.txt")).getOuterHumidity());
    	
    	sensorData.put("Evotranspiration", 0.0);
    	sensorData.put("LeafWetness", 0.0);
    	sensorData.put("SoilMoisture", 0.0);
    	sensorData.put("Dewpoint", 0.0);
    	sensorData.put("WindChill", 0.0);
    	sensorData.put("HeatIndex", 0.0);
    }    
    
    
    /**
     * enables/disables sensors according to a map of sensornames and booleans from the GUI
     *  
     */
    private void enableSensors() {
    	//get map from GUI
    	HashMap<String, Boolean> enables = new HashMap<String, Boolean>();
    	Set<String> sensorList = new HashSet<>(enables.keySet());
    	for(String s: sensorList) {
    		if (!enables.get(s)) {
    			//put a bunch of if/switch statements here that will 
    		}
    		else {
    			//if enable is true, make sure the thread is still running. if not, restart it
    			//sensorname.isInterrupted 
    				//restart that thread: 
    			
    			//note: threads appear to not have an easy way of interrupting and restarting. maybe have ISS block it instead and just let sensors run?
    		}
    	}
    }
    
    
    
    /** 
     * Returns the transmitter ID. 
     */
    public int getTransmitterId() {
        return transmitterID;
    };
    
    /**  
     * Changes the transmitter ID. 
     */
    public void setTransmitterId(int id) {
        transmitterID = id;
    }
    
    /**
     * Starts all threads. Called during run().
     */
    private void startSensors() {
    	//myHumiditySensor.start();
    	/*
    	 * other sensors here
    	 */
    }
    
    /**
     * Runs the ISS. ISS will update at the shortest sensor interval (3 seconds)
     */
    @Override
    public void run() {
    	startSensors();
    	System.out.println("sensors started");
    	
    	timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateData();
				enableSensors();
				//System.out.println(sensorData.get("OuterHumidity"));
			}
		}, 0, 3000);	
	
    }
	
}
