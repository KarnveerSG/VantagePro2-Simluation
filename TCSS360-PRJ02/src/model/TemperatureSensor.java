package model;
import java.io.Serializable;
import java.util.Random;

import application.Main;
/**
 * This is a temperature sensor.
 * @author Karnveer Gill
 * @version July 2020 - borrowing code from Group 6
 */
public class TemperatureSensor extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is used to determine whether the sensor is generating new data or not.
	 */
	private volatile boolean cancel;

	/**
	 * This is the update interval for the outer temperature.
	 */
	private final int updateOuterInterval;
	
	/**
	 * This is the update interval for the inner temperature.
	 */
	private final int updateInnerInterval;

	/**
	 * This is the inner temperature in the console.
	 */
	private double innerTemperature;
	
	/**
	 * This is the outer temperature located in ISS. 
	 */
	private double outerTemperature;

	/**
	 * Minimum for the inner temperature range in Fahrenheit (ï¿½F).
	 */
	private final double MININNERTEMPERATURE = 32; //
	
	/**
	 * Maximum for the inner temperature range in Fahrenheit (ï¿½F).
	 */
	private final double MAXINNERTEMPERATURE = 140;
	
	/**
	 * Minimum for the outer temperature range in Fahrenheit (ï¿½F).
	 */
	private final double MINOUTERTEMPERATURE = -40;
	
	/**
	 * Maximum for the outer temperature range in Fahrenheit (ï¿½F).
	 */
	private final double MAXOUTERTEMPERATURE = 150;
	
	/**
	 * Used to speed up testing, 1 for testing, 1000 for normal usage.
	 */
	private final int TESTINTERVAL = 1000;

	/**
	 * This is the constructor for the temperature sensor.
	 */
	public TemperatureSensor() {
		this.cancel = false;
		this.innerTemperature = 0;
		this.outerTemperature = 0;
		this.updateInnerInterval = 60;  //Sensor update interval is 60 seconds.
		this.updateOuterInterval = 10;  //Sensor update interval is 10 seconds.
	}
	
	/**
	 * This generates a random value for inner temperature(ï¿½F).
	 */
	private void setInnerTemperature() {
		final Random r = new Random();
		this.innerTemperature = MININNERTEMPERATURE + (MAXINNERTEMPERATURE - MININNERTEMPERATURE) * r.nextDouble();
	}

	/**
	 * This returns the inner temperature(ï¿½F).
	 * @return double - inner temperature
	 */
	public Double getInnerTemperature() {
		return this.innerTemperature;
	}

	/**
	 * This generates a random value for outer temperature(ï¿½F).
	 */
	private void setOuterTemperature() {
		final Random r = new Random();
		this.outerTemperature = MINOUTERTEMPERATURE + (MAXOUTERTEMPERATURE - MINOUTERTEMPERATURE) * r.nextDouble();
	}

	/**
	 * This returns the outer temperature(ï¿½F).
	 * @return double - outer temperature
	 */
	public Double getOuterTemperature() {
		return this.outerTemperature;
	}
	
	/**
	 * This is the run method that handles generates new data with the intervals.
	 */
	public void run() {
	while (true) {
		try {
			if(!cancel) {
				setOuterTemperature();
				setInnerTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				setOuterTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				setOuterTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				setOuterTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				setOuterTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				setOuterTemperature();
				Thread.sleep(updateOuterInterval * TESTINTERVAL);
				Main.serialization("Temperature_S.txt", this);
//				for(int i = 0; i < updateInnerInterval/updateOuterInterval - 1; i++) {
//					Thread.sleep(updateOuterInterval * 1000);					   //Sets inner temperature every 60 seconds
//					setOuterTemperature();										   //Sets outer temperature every 10 seconds
//				}
//				Thread.sleep(updateOuterInterval * 1000);							
			} else {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Temperature Sensor stopped!");
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