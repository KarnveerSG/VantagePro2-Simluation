package model;
import java.io.Serializable;
import java.util.Random;

import application.Main;
/**
 * This represents a humidity sensor. 
 * @author Karnveer Gill & Albert Lin
 * @version 1 - July 2020
 */
public class HumiditySensor extends Thread implements Serializable  {
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
	 * This is the inner humidity located in the console.
	 */
	private double innerHumidity;
	
	/**
	 * This is the outer humidity located in ISS. 
	 */
	private double outerHumidity;
	
	/**
	 * This is the maximum for the humidity range.
	 */
	private final double MINHUMIDITY = 0;
	
	/**
	 * This is the minimum for the humidity range.
	 */
	private final double MAXHUMIDITY = 100;
	
	/**
	 * This is the constructor for the humidity sensor.
	 */
	public HumiditySensor() {
		this.cancel = false;
		this.innerHumidity = 0;
		this.outerHumidity = 0;
		this.updateInterval = 60;  //Sensor update interval is 60 seconds.
	}
	
	/**
	 * This generates a random value for inner humidity.
	 */
	private void setInnerHumidity() {
		final Random r = new Random();
		this.innerHumidity = MINHUMIDITY + (MAXHUMIDITY - MINHUMIDITY) * r.nextDouble();
	}
	
	/**
	 * This returns the inner humidity.
	 * @return double - inner humidity(%)
	 */
	public Double getInnerHumidity() {
		return this.innerHumidity;
	}
	
	/**
	 * This generates a random value for outer humidity.
	 */
	private void setOuterHumidity() {
		final Random r = new Random();
		this.outerHumidity = MINHUMIDITY + (MAXHUMIDITY - MINHUMIDITY) * r.nextDouble();
	}

	/**
	 * This returns the outer humidity.
	 * @return double - outer humidity(%)
	 */
	public Double getOuterHumidity() {
		return this.outerHumidity;
	}

	/**
	 * This is the run method that handles generates new data with the intervals.
	 */
	public void run() {
	while (true) {
		try {
			if(!cancel) {
				setInnerHumidity();
				setOuterHumidity();
				//System.out.println("Humidity Update ding");
				Main.serialization("Humidity_S.txt", this);
				
				Thread.sleep(updateInterval * 1000);
			} else {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Humidity Sensor stopped!");
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