package model;

import java.util.Random;

/**
 * The Anemometer sensor measures wind direction and wind speed. Wind direction is measured in degrees
 * and wind speed can be measured in miles per hour, knots, meters per second, or kilometers per hour.
 * The anemometer sensor runs on its own thread.
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
public class AnemometerSensor extends Thread {

	/** minimum wind direction value in degrees */
	public static final int MIN_WIND_DIRECTION = 1;
	
	/** maximum wind direction value in degrees */
	public static final int MAX_WIND_DIRECTION = 360;
	
	/** minimum wind speed value (the same regardless of units) */
	public static final int MIN_WIND_SPEED = 0;
	
	/** maximum wind speed value in miles per hour */
	public static final int MAX_WIND_SPEED_MPH = 200;
	
	/** maximum wind speed value in knots */
	public static final int MAX_WIND_SPEED_KNOTS = 173;
	
	/** maximum wind speed value in meters per seconds */
	public static final double MAX_WIND_SPEED_MPS = 89;
	
	/** maximum wind speed value in kilometers per hour */
	public static final int MAX_WIND_SPEED_KMH = 322;
	
	/** the time interval between the data updates. */
	public static final int INTERVAL = 3;
	
	/** an integer that represents the wind direction value the sensor generates. */
	private int windDirection; // updates 2.5 to 3 seconds
	
	/** a double that represents the wind speed value the sensor generates. */
	private double windSpeed; // updates 2.5 to 3 seconds
	
	/** an integer that represents the type of units the sensor generates for wind speed. */
	private int units;
	
	/** A boolean to determine if the thread should sleep. */
	private volatile boolean cancel;
	
	/** A Random object to generate data for the Rain Collector. */
	private final Random rand;
	
	/**
	 * constructor that initializes the variables. Miles per hour is the default wind speed.
	 */
	public AnemometerSensor() {
		rand = new Random();
		cancel = false;
		units = 1;
		windDirection = 0;
		windSpeed = 0;
	}
	
	/**
	 * Generates a random wind direction value in degrees
	 */
	public void setWindDirection() {
		windDirection = rand.nextInt(MAX_WIND_DIRECTION - MIN_WIND_DIRECTION + 1) + 1;
	}
	
	/**
	 * Returns the wind direction value
	 * @return wind direction value
	 */
	public int getWindDirection() {
		return windDirection;
	}
	
	/**
	 * Generates a random wind speed value with the given units
	 * @param option the units of wind speed
	 */
	public void setWindSpeed(int option) {
		units = option;
		
		if (option == 1) {
			setWindSpeedMilesPerHour();
		} else if (option == 2) {
			setWindSpeedKnots();
		} else if (option == 3) {
			setWindSpeedMetersPerSecond();
		} else if (option == 4) {
			setWindSpeedKilometersPerHour();
		} else {
			System.out.println("Error: you must input a valid number to set wind speed units: \n"
					+ "Input 1 to set wind speed as miles per hour\n"
					+ "Input 2 to set wind speed as knots\n"
					+ "Input 3 to set wind speed as meters per second\n"
					+ "Input 4 to set wind speed as kilometers per hour\n");
		}
		
	}
	
	/**
	 * Generates a random wind speed value in miles per hour
	 */
	private void setWindSpeedMilesPerHour() {
		windSpeed = rand.nextInt(MAX_WIND_SPEED_MPH - MIN_WIND_SPEED + 1);
	}
	
	/**
	 * Generates a random wind speed value in knots 
	 */
	private void setWindSpeedKnots() {
		windSpeed = rand.nextInt(MAX_WIND_SPEED_KNOTS - MIN_WIND_SPEED + 1);
	}
	
	/**
	 * Generates a random wind speed value in meters per second
	 */
	private void setWindSpeedMetersPerSecond() {
		windSpeed = MIN_WIND_SPEED + (MAX_WIND_SPEED_MPS - MIN_WIND_SPEED) * rand.nextDouble();
	}
	
	/**
	 * Generates a random wind speed value in kilometers per hour
	 */
	private void setWindSpeedKilometersPerHour() {
		windSpeed = rand.nextInt(MAX_WIND_SPEED_KMH - MIN_WIND_SPEED + 1);
	}
	
	/**
	 * Returns the wind speed
	 * @return the wind speed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}
	
	/**
	 * Starts running the thread. 
	 */
	public void run() {
		//thread always runs
		while (true) {
			try {
				//updates data in intervals if not paused
				if(!cancel) {
					setWindDirection();
					setWindSpeed(units);
					Thread.sleep(INTERVAL * 1000);
					
					System.out.println("updating");
				} else {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.out.println("Anemometer Sensor stopped!");
			} 
		}
	}
	
	/**
	 * Pauses the thread;
	 */
	public void cancel() {
		this.cancel = true;
	}
	
	/**
	 * Resumes the thread;
	 */
	public void restart() {
		this.cancel = false;
	}
}
