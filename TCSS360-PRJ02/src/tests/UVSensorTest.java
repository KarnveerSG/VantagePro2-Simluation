package tests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.UVSensor;
/**
 * This class is used to test the UV sensor.
 * @author Karnveer Gill
 * @version 1 - July 2020
 */
class UVSensorTest {
	/**
	 * This is a test UV sensor.
	 */
	static UVSensor test;

	/**
	 * This sets up the test environment -> creates a test UV sensor and starts it.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		test = new UVSensor();
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
	 * This tests whether the temperature constructor works. 
	 */
	@Test
	void testUVSensor() {
		assertNotNull(new UVSensor());
	}

	/**
	 * This tests if the UV radiation index is within range.
	 */
	@Test
	void testGetUVIndex() {
		double inner1 = test.getUVIndex();
		double high = 16.0;
		double low = 0.0;
		
		for(int i = 0; i < 100000; i++) {
			inner1 = test.getUVIndex();
		assertTrue(inner1 >= low);
		assertTrue(inner1 <= high);
		}
	}

	/**
	 * This tests if the UV radiation dose is within range.
	 */
	@Test
	void testGetUVDose() {
		double outer1 = test.getUVDose();
		double high = 199.0;
		double low = 0.0;
		
		for(int i = 0; i < 100000; i++) {
			outer1 = test.getUVDose();
		assertTrue(outer1 >= low);
		assertTrue(outer1 <= high);
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
