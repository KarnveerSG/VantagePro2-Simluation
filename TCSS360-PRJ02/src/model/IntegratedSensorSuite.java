package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;


/**
 * ISS will start sensor threads and collect data from them periodically to send 
 * to the GUI. It will also gather data from the GUI in order to check which sensors are
 * supposed to be read. Transfer between sensors, ISS, and GUI will be serialized.
 * 
 * @author Andrew Josten
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
	public transient static Timer timer;
	
	/**
	 * Altitude at startup
	 */
	public int alt;
	
	//Sensor objects (transient, since we just want the data sent to GUI, not the objects)
    public transient HumiditySensor myHumiditySensor;
    public transient AnemometerSensor myAnemometerSensor;
    public transient LeafWetnessSensor myLeafWetnessSensor;
    public transient RainCollectorSensor myRainCollectorSensor;
    public transient SoilMoistureSensor mySoilMoistureSensor;
    public transient TemperatureSensor myTemperatureSensor;
    public transient UVSensor myUVSensor;
    
    /**
     * This data field will hold a map of sensor names and their relevant data readings
     * for the GUI to collect and read from.
     * NOTE: Wind direction will be rendered in degrees (0-359)
     */
    public HashMap<String, Double> sensorData;
    
    /**
     * Constructs the ISS which in turn constructs the sensor classes.
     * @param id  Transmitter ID as each console can receive up to 8 suites
     * @param altitude The altitude used in barometric calculations
     */
    public IntegratedSensorSuite(int id, int altitude) {
    	transmitterID = id;
    	alt = altitude;
    	
    	myHumiditySensor = new HumiditySensor();
    	myAnemometerSensor = new AnemometerSensor();
        myLeafWetnessSensor = new LeafWetnessSensor();
        myRainCollectorSensor = new RainCollectorSensor();
        mySoilMoistureSensor = new SoilMoistureSensor();
        myTemperatureSensor = new TemperatureSensor();
        myUVSensor = new UVSensor();
    	
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
    	sensorData.put("Barometer", 0.0);
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
    	//Some new objects constructed to hold deserial data if they are used in other calculations
    	
    	TemperatureSensor dataTemp = ((TemperatureSensor) Main.deserialization("Temperature_S.txt"));
    	HumiditySensor dataHum = ((HumiditySensor) Main.deserialization("Humidity_S.txt"));
    	
    	//This is like so because the Anemometer sometimes has an issue and can't be cast
    	AnemometerSensor dataAnemometer;
    	try {
    		dataAnemometer = ((AnemometerSensor) Main.deserialization("Anemometer_S.txt"));
    	}
    	catch(Exception e) {
    		dataAnemometer = new AnemometerSensor();
    	}
    	
    	//AnemometerSensor dataAnemometer = ((AnemometerSensor) Main.deserialization("Anemometer_S.txt"));
    	
    	sensorData.put("RainFall", ((RainCollectorSensor) Main.deserialization("RainCollector_S.txt")).getRainFall());
    	sensorData.put("RainRate", ((RainCollectorSensor) Main.deserialization("RainCollector_S.txt")).getRainRate());
    	sensorData.put("WindSpeed", dataAnemometer.getWindSpeed());
    	sensorData.put("WindDirection", (double) dataAnemometer.getWindDirection());
    	sensorData.put("InnerTemp", dataTemp.getInnerTemperature());
    	sensorData.put("OuterTemp", dataTemp.getOuterTemperature());
    	sensorData.put("UVIndex", ((UVSensor) Main.deserialization("UV_S.txt")).getUVIndex());
    	sensorData.put("UVDose", ((UVSensor) Main.deserialization("UV_S.txt")).getUVDose());
    	sensorData.put("InnerHumidity", dataHum.getInnerHumidity());
    	sensorData.put("OuterHumidity", dataHum.getOuterHumidity());
    	sensorData.put("LeafWetness", (double) ((LeafWetnessSensor) Main.deserialization("LeafWetness_S.txt")).getLeafWetness());
    	sensorData.put("SoilMoisture", ((SoilMoistureSensor) Main.deserialization("SoilMoisture_S.txt")).getSoilMoisture());
    	
    	//Updates all the calculated variables as per the document "Derived Variables in Davis Weather Products"
    	
    	/*Wind Chill
    	 * param: outside air temp, wind speed (Imperial units)
    	 * W = 35.74 + 0.6215*T - 35.75 * (V^0.16) + 0.4275 * (V^0.16)
    	 */
    	double V = dataAnemometer.getWindSpeed();
    	double T = dataTemp.getOuterTemperature();
    	sensorData.put("WindChill", 35.74 + 0.6215*T - 35.75 * (Math.pow(V, 0.16)) + 0.4275 * (Math.pow(V, 0.16)));
    	
    	/*Heat index
    	 * param: outside air temp, outside humidity.
    	 * Using a formula that mimics the index table
    	 * HI = -42.379 + 2.04901523*T + 10.14333127*RH - .22475541*T*RH - .00683783*T*T - .05481717*RH*RH + .00122874*T*T*RH + .00085282*T*RH*RH - .00000199*T*T*RH*RH
    	 * If RH is < 13%, subtract this adjustment A = [(13-RH)/4]*SQRT{[17-ABS(T-95.)]/17}
    	 * if RH > 85%, add this adjustment A = [(RH-85)/10] * [(87-T)/5]
    	 * 
    	 * Note: Due to the randomized sensor data, there can be some wacky heat indexes given.
    	 */
    	T = dataTemp.getOuterTemperature();
    	double RH = dataHum.getOuterHumidity();
    	double A = 0.0;
    	if(RH < 13) {
    		A = ((13-RH)/4) * Math.sqrt((17-Math.abs(T-95))/17);
    		A *= -1;
    	}
    	else if(RH > 85) {
    		A = ((RH-85)/10) * ((87-T)/5);
    	}    	
    	sensorData.put("HeatIndex", (-42.379 + 2.04901523*T + 10.14333127*RH - 0.22475541*T*RH - 0.00683783*T*T - 0.05481717*RH*RH + 0.00122874*T*T*RH + 0.00085282*T*RH*RH - 0.00000199*T*T*RH*RH) + A);
    	
    	/*Dewpoint
    	 * param: outside temp, outside humidity
    	 * vapor pressure = RH*0.01*6.112*Math.pow(17.62*T, (T+243.12));
    	 * D = (243.12*(ln v) - 440.1)/(19.43 -ln v)
    	 */
    	T = dataTemp.getOuterTemperature();
    	RH = dataHum.getOuterHumidity();
    	double VP = RH*0.01*6.112*Math.exp((17.62*T) / (T+243.12));
    	double Numerator = (243.12*(Math.log(VP)) - 440.1);
    	double Denominator = (19.43 - (Math.log(VP))); 	
    	sensorData.put("Dewpoint", Numerator/Denominator);
    	
    	/*Barometric pressure
    	 * param:outside air, outside humidity, elevation, atmospheric pressure
    	 * 
    	 * Note that atmospheric pressure is a sensor located on the main circuit board according
    	 * to documentation. We'll implement  pressure sensing RNG soon. In the mean time,
    	 * we'll use 14.35 PSI (approx. PSI at 200 ft).
    	 * 
    	 * Psl = Ps * (R)
    	 * Tv = T + 460 + L
    	 * L = 11 * alt/8000
    	 * R = 10 ^ (Math.exp(alt/(122.8943111*Tv)); 
    	 */
    	double Ps = 14.35; //supposed to get pressure from console here
    	int a = alt;
    	sensorData.put("Barometer", Ps * (Math.pow(10, ( Math.exp(a / (122.8943111 * ( T + 460 + (11* a/8000))))))));
    	
    	/*Evotranspiration / UV index / UVDose
    	 * Note: requires optional solar radiation sensor. We've already included several optional
    	 * sensors at this point. If we have the time to implement radiation sensor and ET, we will, but
    	 * this currently has _lower priority_.
    	 * 
    	 * Note that atmospheric pressure is a sensor located on the console main circuit board according
    	 * to documentation. We'll implement  pressure sensing when GUI is up. In the mean time,
    	 * we'll use 14.35 PSI (approx. PSI at 200 ft).
    	 * 
    	 * Param: air temp, wind speed, *solar radiation*, humidity, air pressure (from console)
    	 * 
    	 */	
    	sensorData.put("Evotranspiration", 0.0);
    	
    	//Check GUI_S.txt and convert data if need be.
    	convertData(false);
    	//covertData(Main.deserialization("GUI_S.txt").getSystem());
    }   
    
    /**
     * This method changes the data from imperial to metric using a boolean.
     * @param m True if we want metric
     */
    public void convertData(boolean m) {
		//Convert to metric
		if(m) {
			sensorData.put("RainFall", (sensorData.get("RainFall")) * 25.4);
	    	sensorData.put("RainRate", (sensorData.get("RainRate")) * 25.4);
	    	sensorData.put("WindSpeed", (sensorData.get("WindSpeed") * 1.60934));
	    	//sensorData.put("WindDirection", 0.0);
	    	sensorData.put("InnerTemp", (sensorData.get("InnerTemp") -32) / 1.8);
	    	sensorData.put("OuterTemp", (sensorData.get("OuterTemp") -32) / 1.8);
	    	//sensorData.put("UVIndex", 0.0);
	    	//sensorData.put("UVDose", 0.0);
	    	//sensorData.put("InnerHumidity", 0.0);
	    	//sensorData.put("OuterHumidity", 0.0);    	
	    	sensorData.put("Barometer", sensorData.get("Barometer") * 6894.76);
	    	//sensorData.put("LeafWetness", 0.0);
	    	//sensorData.put("SoilMoisture", 0.0);
	    	sensorData.put("Dewpoint", (sensorData.get("Dewpoint") -32) / 1.8);
	    	sensorData.put("WindChill", (sensorData.get("WindChill") -32) / 1.8);
	    	sensorData.put("HeatIndex", (sensorData.get("HeatIndex") -32) / 1.8);  
	    	
	    	//T(°C) = (T(°F) - 32) / 1.8
		}		
	}

	/**
     * enables/disables sensors according to a map of sensornames and booleans from the GUI
     *  
     */
    public void enableSensors(HashMap<String, Boolean> m) {
    	//System.out.println(m == null);
    	Set<String> sensorList = new HashSet<>(m.keySet());
    	
    	//turn off
    	for(String s: sensorList) {
    		if (!m.get(s)) {
    			if(s.equals("Humidity")) {
    				myHumiditySensor.cancel();
    				System.out.println("Humidity Disabled");
    			}
    			else if(s.equals("Temperature")){
    				myTemperatureSensor.cancel();
    				System.out.println("Temp Disabled");
    			}
				else if(s.equals("Anemometer")){
					myAnemometerSensor.cancel();
					System.out.println("Anemometer Disabled");
				}
				else if(s.equals("LeafWetness")){
					myLeafWetnessSensor.cancel();
					System.out.println("LeafWetness Disabled");
				}
				else if(s.equals("SoilMoisture")){
					mySoilMoistureSensor.cancel();
					System.out.println("SoilMoisture Disabled");
				}
				else if(s.equals("RainCollector")){
					myRainCollectorSensor.cancel();
					System.out.println("RainCollector Disabled");
    			}
				else if(s.equals("UV")){
					myUVSensor.cancel();
    			}
				else {
					System.out.println("Not a sensor");
				}
    		}
    		//turn on
    		else {
    			if(s.equals("Humidity")) {
    				myHumiditySensor.restart();
    				//System.out.println("Humid Enabled");
    			}
    			else if(s.equals("Temperature")){
    				myTemperatureSensor.restart();
    				//System.out.println("Temp Enabled");
    			}
				else if(s.equals("Anemometer")){
					myAnemometerSensor.restart();
					System.out.println("Anemometer Enabled");
				}
				else if(s.equals("LeafWetness")){
					myLeafWetnessSensor.restart();
					//System.out.println("LeafWetness Enabled");
				}
				else if(s.equals("SoilMoisture")){
					mySoilMoistureSensor.restart();
					//System.out.println("Soil Enabled");
				}
				else if(s.equals("RainCollector")){
					myRainCollectorSensor.restart();
					//System.out.println("RainCollector Enabled");
    			}
				else if(s.equals("UV")){
					//System.out.println("UV Enabled");
					myUVSensor.restart();
    			}
				else {
					System.out.println("Not a sensor");
				}
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
    	myHumiditySensor.start();
    	myAnemometerSensor.start();
        myLeafWetnessSensor.start();
        myRainCollectorSensor.start();
        mySoilMoistureSensor.start();
        myTemperatureSensor.start();
        myUVSensor.start();
    }
    
    /**
     * Runs the ISS. ISS will update at the shortest sensor interval (3 seconds)
     */
    @Override
    public void run() {
    	startSensors();
    	System.out.println("Sensors started\n");
    	
    	timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				//update data from sensors
				updateData();			
				
				@SuppressWarnings("unchecked")
				HashMap<String, Boolean> enabledSensors = ((HashMap<String, Boolean>) Main.deserialization("GUI_S.txt"));
				if(enabledSensors != null) {
					enableSensors(enabledSensors);
				}				
				
				//Deserialize GUI stuff for sensor enabling and metric exchange
				boolean metric = ((boolean) Main.deserialization("GUI_M.txt"));
				convertData(metric);
				
				//Serialize for the use of GUI. Only serializes the data the GUI should need
				Main.serialization("ISS_S.txt", sensorData);
			}
		}, 0, 3000);	
    }	
}
