package model;

import java.util.Random;

/**
 * The Rain Collector sensor measures rainfall and rain rate. Rainfall can be measured in 
 * daily inches, monthly/yearly inches, daily mm, or monthly/yearly mm. Rain rate can be
 * measured in inches/hour or mm/hour. The Rain Collector sensor runs on its own thread
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
public class RainCollectorSensor extends Thread {
	
	/** minimum rainfall value (the same regardless of units) */
	public static final double MIN_RAINFALL = 0;
	
	/** maximum daily rainfall value in inches */
	public static final double MAX_RAINFALL_DAILY_IN = 99.99;
	
	/** maximum monthly rainfall value in inches */
	public static final double MAX_RAINFALL_MONTHLY_IN = 199.99;
	
	/** maximum daily rainfall value in mm */
	public static final double MAX_RAINFALL_DAILY_MM = 999.8;
	
	/** maximum monthly rainfall value in mm*/
	public static final double MAX_RAINFALL_MONTHLY_MM = 6553;
	
	/** minimum rainfall value (the same regardless of units) */
	public static final double MIN_RAINRATE = 0;
	
	/** minimum rain rate value in inches per hour */
	public static final double MAX_RAINRATE_IN = 96;
	
	/** minimum rain rate value in mm per hour */
	public static final double MAX_RAINRATE_MM = 2438;
	
	/** the time interval between the data updates. */
	public static final int INTERVAL = 24;
	
	/** a double that represents the rainfall value the sensor generates. */
	private double rainfall; // updates 20-24 seconds
	
	/** a double that represents the rain rate value the sensor generates. */
	private double rainrate; // updates 20-24 seconds
	
	/** True if daily, false if monthly */
	private boolean time;
	
	/** True if inches, false if mm */
	private boolean units;
	
	/** A boolean to determine if the thread should sleep. */
	private volatile boolean cancel;
	
	/** A Random object to generate data for the Rain Collector. */
	private final Random rand;

	/**
	 * Constructor that initializes the variables. Daily inches is the default time/units.
	 */
	public RainCollectorSensor() {
		rand = new Random();
		cancel = false;
		rainfall = 0;
		rainrate = 0;
		time = true;
		units = true;
	}
	
	/**
	 * Generates a rain fall value with the given time and units
	 * @param time 	true if daily, false if monthly
	 * @param units true if inches, false if mm
	 */
	public void setRainFall(boolean time, boolean units) {
		this.time = time;
		this.units = units;
		
		if (time) {
			// measure in daily
			if (units) { 
				//measure in inches
				setRainFallDailyInches();
			} else { 
				//measure in mm
				setRainFallDailyMillimeters();
			}
		} else { 
			//measure in monthly
			if (units) { 
				//measure in inches
				setRainFallMonthlyInches();
			} else { 
				//measure in mm
				setRainFallMonthlyMillimeters();
			}
		}
	}
	
	/**
	 *  Generates a random rainfall value for daily inches
	 */
	private void setRainFallDailyInches() {
		rainfall = MIN_RAINFALL + (MAX_RAINFALL_DAILY_IN - MIN_RAINFALL) * rand.nextDouble();
	}
	
	/**
	 *  Generates a random rainfall value for daily mm
	 */
	private void setRainFallDailyMillimeters() {
		rainfall = MIN_RAINFALL + (MAX_RAINFALL_DAILY_MM - MIN_RAINFALL) * rand.nextDouble();
	}
	
	/**
	 *  Generates a random rainfall value for monthly inches
	 */
	private void setRainFallMonthlyInches() {
		rainfall = MIN_RAINFALL + (MAX_RAINFALL_MONTHLY_IN - MIN_RAINFALL) * rand.nextDouble();
	}
	
	/**
	 *  Generates a random rainfall value for monthly mm
	 */
	private void setRainFallMonthlyMillimeters() {
		rainfall = MIN_RAINFALL + (MAX_RAINFALL_MONTHLY_MM - MIN_RAINFALL) * rand.nextDouble();
	}
	
	/**
	 * Returns rainfall value.
	 * @return rainfall value.
	 */
	public Double getRainFall() {
		return rainfall;
	}
	
	/**
	 * Generates a random rain rate value with the given units
	 * @param units true if inches, false if mm
	 */
	public void setRainRate(boolean units) {
		this.units = units;
		
		if (units) {
			// measure in inches
			setRainRateInches();
		} else { 
			//measure in mm
			setRainRateMillimeters();
		}
	}
	
	/**
	 * Generates a random rain rate value in inches/hour
	 */
	private void setRainRateInches() {
		rainrate = MIN_RAINFALL + (MAX_RAINRATE_IN - MIN_RAINRATE) * rand.nextDouble();
	}
	
	/**
	 * Generates a random rain rate value in mm/hour
	 */
	private void setRainRateMillimeters() {
		rainrate = MIN_RAINFALL + (MAX_RAINRATE_MM - MIN_RAINRATE) * rand.nextDouble();
	}
	
	/**
	 * Returns rain rate value.
	 * @return rain rate value.
	 */
	public Double getRainRate() {
		return rainrate;
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
					setRainFall(time, units);
					setRainRate(units);
					Thread.sleep(INTERVAL * 1000);
				} else {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.out.println("Rain Collector Sensor stopped!");
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
