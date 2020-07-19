package tests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.TemperatureSensor;
/**
 * This is a class to test the temperature sensor.
 * @author Karnveer Gill
 */
class TemperatureSensorTest {
	/**
	 * This is a test temperature sensor.
	 */
	static TemperatureSensor test;
	
	/**
	 * This sets up the test environment -> creates a test temperature sensor and starts it.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		test = new TemperatureSensor();
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
	void testTemperatureSensor() {
		assertNotNull(new TemperatureSensor());
	}

	/**
	 * This tests if the inner temperature is within range.
	 * @throws InterruptedException 
	 */
	@Test
	void testGetInnerTemperature() throws InterruptedException {
		double inner1 = test.getInnerTemperature();
		double high = 140.0;
		double low = 32.0;
		
		for(int i = 0; i < 500; i++) {
			inner1 = test.getInnerTemperature();	//Sensor timer is 60 seconds to specification
			TimeUnit.MILLISECONDS.sleep(60);		//Change the TESTINTERVAL in sensor to 1 test quickly**
		assertTrue(inner1 >= low);					
		assertTrue(inner1 <= high);
		}
	}

	/**
	 * This tests if the outer temperature is within range.
	 * @throws InterruptedException 
	 */
	@Test
	void testGetOuterTemperature() throws InterruptedException {
		double outer1 = test.getOuterTemperature();
		double high = 150.0;
		double low = -40.0;
		
		for(int i = 0; i < 1000; i++) {
			outer1 = test.getOuterTemperature();	//Sensor timer is 10 seconds to specification
			TimeUnit.MILLISECONDS.sleep(10);		//Change TESTINTERVAL in sensor to 1 to test quickly**
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
		test.restart();				//if no restart here, other tests get affected.
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