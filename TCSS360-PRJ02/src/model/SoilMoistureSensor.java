package model;

import java.io.Serializable;
import java.util.Random;

import application.Main;
/**
 * This represents a soil moisture sensor. 
 * @author Karnveer Gill
 * @version 1 - July 2020 - borrowing code from Group 6
 */
public class SoilMoistureSensor extends Thread implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is used to determine whether the sensor is generating new data or not.
	 */
	private volatile boolean cancel;
	
	/**
	 * This is the update interval for the sensor.
	 */
	private final int updateInterval;
	
	/**
	 * This is the soil moisture in cb. 
	 */
	private double soilMoisture;
	
	/**
	 * This is the maximum for the soil moisture range.
	 */
	private final double MINMOISTURE = 0;
	
	/**
	 * This is the minimum for the soil moisture range.
	 */
	private final double MAXMOISTURE = 200;
	
	/**
	 * Used to speed up testing, 1 for testing, 1000 for normal usage.
	 */
	private final int TESTINTERVAL = 1000;
	
	/**
	 * This is the constructor for the soil moisture sensor.
	 */
	public SoilMoistureSensor() {
		this.cancel = false;
		this.soilMoisture = 0;
		this.updateInterval = 90;  //Sensor update interval is 90 seconds.
	}
	
	/**
	 * This generates a random value for soil moisture.
	 */
	private void setSoilMoisture() {
		final Random r = new Random();
		this.soilMoisture = MINMOISTURE + (MAXMOISTURE - MINMOISTURE) * r.nextDouble();
	}
	

	/**
	 * This returns the soil moisture.
	 * @return double - soil moisture(cb)
	 */
	public Double getSoilMoisture() {
		return this.soilMoisture;
	}

	/**hu
	 * This is the run method that handles generates new data with the intervals.
	 */
	public void run() {
	while (true) {
		try {
			if(!cancel) {
				setSoilMoisture();
				Main.serialization("SoilMoisture_S.txt", this);
				Thread.sleep(updateInterval * TESTINTERVAL);
			} else {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Soil Moisture Sensor stopped!");
			} 
		}
	}
	
	/**
	 * This is used to stop generating new data.
	 */
	public void cancel() {
		this.cancel = true;
	}
	
	/**
	 * This is used to restart the data generation.
	 */
	public void restart() {
		this.cancel = false;
	}
}