package tests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.HumiditySensor;
/**
 * This is a class to test the humidity sensor.
 * @author Karnveer Gill & Albert Lin
 *
 */
class HumiditySensorTest {
	/**
	 * This is a test humidity sensor.
	 */
	static HumiditySensor test;

	/**
	 * This sets up the test environment -> creates a test humidity sensor and starts it.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {		
		test = new HumiditySensor();
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
	 * This tests whether the humidity constructor works. 
	 */
	@Test
	void testHumiditySensor() {
		assertNotNull(new HumiditySensor());
	}

	/**
	 * This tests if the inner humidity is within range.
	 */
	@Test
	void testGetInnerHumidity() {
		double inner1 = test.getInnerHumidity();
		double high = 100.0;
		double low = 0.0;
		
		for(int i = 0; i < 100000; i++) {
			inner1 = test.getInnerHumidity();
		assertTrue(inner1 >= low);
		assertTrue(inner1 <= high);
		}
	}

	/**
	 * This tests if the outer humidity is within range.
	 */
	@Test
	void testGetOuterHumidity() {
		double outer1 = test.getOuterHumidity();
		double high = 100.0;
		double low = 0.0;
		
		for(int i = 0; i < 100000; i++) {
			outer1 = test.getOuterHumidity();
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
