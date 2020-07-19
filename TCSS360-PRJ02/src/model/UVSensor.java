package model;
import java.io.Serializable;
import java.util.Random;

import application.Main;
/**
 * This is a UV sensor
 * @author Karnveer Gill
 * @version July 2020 - borrowing code from Group 6
 */
public class UVSensor extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is used to determine whether the sensor is generating new data or not.
	 */
	private volatile boolean cancel;
	
	/**
	 * This is the update interval for the sensor
	 */
	private final int updateInterval;

	/**
	 * This is the UV radiation dose.
	 */
	private double uvDose;
	
	/**
	 * This is the UV radiation index.
	 */
	private double uvIndex;

	/**
	 * This is the minimum for the UV radiation dose range.
	 */
	private final double MINDOSE = 0;
	
	/**
	 * This is the maximum for the UV radiation dose range.
	 */
	private final double MAXDOSE = 199;
	
	/**
	 * This is the minimum for the UV radiation index range.
	 */
	private final double MININDEX = 0;
	
	/**
	 * This is the maximum for the UV radiation index range.
	 */
	private final double MAXINDEX = 16;
	
	/**
	 * Used to speed up testing, 1 for testing, 1000 for normal usage.
	 */
	private final int TESTINTERVAL = 1000;


	/**
	 * This is the constructor for the UV sensor.
	 */
	public UVSensor() {
		this.cancel = false;
		this.uvDose = 0;
		this.uvIndex = 0;
		this.updateInterval = 60;  //Sensor update interval is 60 seconds.
	}
	
	/**
	 * This randomly generates the UV radiation dose.
	 */
	private void setUVDose() {
		final Random r = new Random();
		this.uvDose = MINDOSE + (MAXDOSE - MINDOSE) * r.nextDouble();
	}
	
	/**
	 * This returns the UV radiation dose. 
	 * @return double - UV radiation dose
	 */
	public Double getUVDose() {
		return this.uvDose;
	}

	/**
	 * This randomly generates the UV radiation index.
	 */
	private void setUVIndex() {
		final Random r = new Random();
		this.uvIndex = MININDEX + (MAXINDEX - MININDEX) * r.nextDouble();
	}
	
	/**
	 * This returns the UV radiation index.
	 * @return double - UV radiation index.
	 */
	public Double getUVIndex() {
		return this.uvIndex;
	}

	/**
	 * This is the run method that handles generates new data with the intervals.
	 */
	public void run() {
	while (true) {
		try {
			if(!cancel) {
				setUVDose();
				setUVIndex();
				Main.serialization("UV_S.txt", this);
				Thread.sleep(updateInterval * TESTINTERVAL);
				//System.out.println("Sensor: " + getUVDose() + getUVIndex());
			} else {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("UV Sensor stopped!");
		  } 
		}
	}
	
	/**
	 * This is used to stop the data generation.
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