package model;

import java.io.Serializable;
import java.util.Random;

import application.Main;

/**
 * The Leaf Wetness sensor measures leaf wetness.
 * The Leaf Wetness sensor runs on its own thread.
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
public class LeafWetnessSensor extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** minimum leaf wetness value in degrees */
	public static final int MIN_LEAF_WETNESS = 0;
	
	/** maximum leaf wetness value in degrees */
	public static final int MAX_LEAF_WETNESS = 15;
	
	/** the time interval between the data updates. */
	public static final int INTERVAL = 54;
	
	/** an integer that represents the leaf wetness value the sensor generates. */
	private int leafWetness; // updates 46 to 54 seconds
	
	/** A boolean to determine if the thread should sleep. */
	private volatile boolean cancel;
	
	/** A Random object to generate data. */
	private final Random rand;
	
	/**
	 * constructor that initializes the variables. Initial leaf wetness is 0
	 */
	public LeafWetnessSensor() {
		rand = new Random();
		cancel = false;
		leafWetness = 0;
	}
	
	/**
	 * Generates a random value for leaf wetness
	 */
	public void setLeafWetness() {
		leafWetness = rand.nextInt(MAX_LEAF_WETNESS - MIN_LEAF_WETNESS + 1);
	}
	
	/**
	 * Returns the leaf wetness value
	 * @return the leaf wetness value
	 */
	public int getLeafWetness() {
		return leafWetness;
	}
	
	/**
	 * This is the run method that handles generates new data with the intervals.
	 */
	public void run() {
	while (true) {
		try {
			if(!cancel) {
				setLeafWetness();
				Main.serialization("LeafWetness_S.txt", this);
				
				Thread.sleep(INTERVAL * 1000);
			} else {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Leaf Wetness Sensor stopped!");
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
