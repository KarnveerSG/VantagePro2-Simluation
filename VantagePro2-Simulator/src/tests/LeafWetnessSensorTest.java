package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.LeafWetnessSensor;

/**
 * A test class for the Leaf Wetness Sensor. The Leaf Wetness sensor measures leaf wetness 
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
class LeafWetnessSensorTest {
	
	/**
	 * A Leaf Wetness Sensor object.
	 */
	private LeafWetnessSensor LWS;

	/**
	 * Initializes the Leaf Wetness Sensor object and its thread
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		LWS = new LeafWetnessSensor();
		LWS.start();
	}
	
	/**
	 * Tests if the leaf wetness sensor object is not null
	 */
	@Test
	void testLeafWetnessSensor() {
		assertNotNull(new LeafWetnessSensor());
	}

	/**
	 * Tests if the thread is alive
	 * @throws InterruptedException
	 */
	@Test
	void testRun() throws InterruptedException {
		assertTrue(LWS.isAlive());
		
		//uncomment when testing if data is updated when forced
//		for (int i = 0; i < 10; i++) {
//			LWS.setLeafWetness();
//			System.out.println("Leaf Wetness: " + LWS.getLeafWetness());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(3);
//		}
		
		//uncomment when testing if data is automatically updated
//		for (int i = 0; i < 10; i++) {
//			System.out.println("Leaf Wetness: " + LWS.getLeafWetness());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(54);
//		}
	}
	
	/**
	 * Tests if the leaf wetness measurements gotten are within the range of valid values. 
	 * Implicitly tests the setter methods.
	 */
	@Test
	void testGetLeafWetness() {
		for (int i = 0; i < 1000; i++) {
			LWS.setLeafWetness();
			int leafWetness = LWS.getLeafWetness();		
			assertTrue(leafWetness >= 0 && leafWetness <= 15, "leaf wetness must be within "
					+ "0 and 15");
		}	
	}
	
	/**
	 * This tests to see whether the cancel method keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testCancel() throws InterruptedException {
		assertTrue(LWS.isAlive());
		LWS.cancel();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(LWS.isAlive());
	}
	
	/**
	 * This tests to see whether the restart method keeps the thread alive. 
	 * @throws InterruptedException
	 */
	@Test
	void testRestart() throws InterruptedException {
		assertTrue(LWS.isAlive());
		LWS.restart();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(LWS.isAlive());
	}

}
