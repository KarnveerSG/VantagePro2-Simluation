package tests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.SoilMoistureSensor;
/**
 * This is a class to test the soil moisture sensor.
 * @author Karnveer Gill
 */ 
class SoilMoistureSensorTest {
	/**
	 * This is a test soil moisture sensor.
	 */
	static SoilMoistureSensor test;
	
	/**
	 * This sets up the test environment -> creates a test soil moisture sensor and starts it.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		test = new SoilMoistureSensor();
		test.start();
	}

	/**
	 * This tests to see if the thread is alive after starting it.
	 */
	@Test
	void testRun() {
		assertTrue(test.isAlive());
	}

	/**
	 * This tests whether the soil moisture constructor works. 
	 */
	@Test
	void testSoilMoistureSensor() {
		assertNotNull(new SoilMoistureSensor());
	}

	/**
	 * This tests if the inner soil moisture is within range.
	 */
	@Test
	void testGetSoilMoisture() {
		double inner1 = test.getSoilMoisture();
		double high = 200.0;
		double low = 0.0;
		
		for(int i = 0; i < 100000; i++) {
			inner1 = test.getSoilMoisture();
		assertTrue(inner1 >= low);
		assertTrue(inner1 <= high);
		}
	}
	
	/**
	 * This tests to see whether the cancel method keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testCancel() throws InterruptedException {
		assertTrue(test.isAlive());
		test.cancel();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(test.isAlive());
	}
	
	/**
	 * This tests to see whether the restart method keeps the thread alive. 
	 * @throws InterruptedException
	 */
	@Test
	void testRestart() throws InterruptedException {
		assertTrue(test.isAlive());
		test.restart();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(test.isAlive());
	}
}
