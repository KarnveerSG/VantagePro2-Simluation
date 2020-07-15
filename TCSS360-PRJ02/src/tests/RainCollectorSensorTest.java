package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.RainCollectorSensor;

/**
 * A test class for the Rain Collector Sensor. The Rain Collector Sensor measures rainfall 
 * and rain rate. 
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
class RainCollectorSensorTest {
	
	/**
	 * A Rain Collector Sensor object.
	 */
	private RainCollectorSensor RCS;

	/**
	 * Initializes the Rain Collector Sensor object and its thread
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		RCS = new RainCollectorSensor();
		RCS.start();
	}

	/**
	 * Tests if the thread is alive
	 * @throws InterruptedException
	 */
	@Test
	void testRun() throws InterruptedException {
		assertTrue(RCS.isAlive());
		
		//uncomment when testing if data is updated when forced
//		for (int i = 0; i < 10; i++) {
//			RCS.setRainFall(true, false);
//			RCS.setRainRate(false);
//			System.out.println("Rain fall: " + RCS.getRainFall());
//			System.out.println("Rain rate: " + RCS.getRainRate());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(3);
//		}
		
		//uncomment when testing if data is automatically updated
//		for (int i = 0; i < 5; i++) {
//			System.out.println("Rain fall: " + RCS.getRainFall());
//			System.out.println("Rain rate: " + RCS.getRainRate());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(24);
//		}
	}
	
	/**
	 * Tests if the Rain Collector Sensor object is not null
	 */
	@Test
	void testRainCollectorSensor() {
		assertNotNull(new RainCollectorSensor());
	}
	
	/**
	 * Tests if the rainfall measurements gotten are within the range of valid values. Tests different 
	 * units and time periods. Implicitly tests the setter methods.
	 */
	@Test
	void testGetRainFall() {
		for (int i = 0; i < 1000; i++) {
			// testing for rainfall in daily inches
			RCS.setRainFall(true, true);
			double rainfall = RCS.getRainFall();
			assertTrue(rainfall >= 0 && rainfall <= 99.99, "rainfall in daily inches must be within "
					+ "0\" and 99.99\"");
			
			// testing for rainfall in daily mm
			RCS.setRainFall(true, false);
			double rainfall2 = RCS.getRainFall();
			assertTrue(rainfall2 >= 0 && rainfall2 <= 999.8, "rainfall in daily mm must be within "
					+ "0 mm and 999.8 mm");
			
			// testing for rainfall in monthly inches
			RCS.setRainFall(false, true);
			double rainfall3 = RCS.getRainFall();
			assertTrue(rainfall3 >= 0 && rainfall3 <= 199.99, "rainfall in monthly inches must be within "
					+ "0\" and 199.99\"");
			
			// testing for rainfall in monthly mm
			RCS.setRainFall(false, false);
			double rainfall4 = RCS.getRainFall();
			assertTrue(rainfall4 >= 0 && rainfall4 <= 6553, "rainfall in monthly mm must be within "
					+ "0 mm and 6553 mm");
		}	
	}
	
	/**
	 * Tests if the rain rate measurements gotten are within the range of valid values. Tests different 
	 * units and time periods. Implicitly tests the setter methods.
	 */
	@Test
	void testGetRainRate() {
		for (int i = 0; i < 1000; i++) {
			// testing for rain rate in inches/hour
			RCS.setRainRate(true);
			double rainRate = RCS.getRainRate();
			assertTrue(rainRate >= 0 && rainRate <= 96, "rain rate in monthly inches must be within "
					+ "0\"/hr and 96\"/hr");
			
			// testing for rainfall in mm/hour
			RCS.setRainRate(false);
			double rainRate2 = RCS.getRainRate();
			assertTrue(rainRate2 >= 0 && rainRate2 <= 2438, "rain rate in monthly mm must be within "
					+ "0 mm/hr and 2438 mm/hr");
		}
	}
	
	/**
	 * Tests if pausing the thread keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testCancel() throws InterruptedException {
		assertTrue(RCS.isAlive());
		RCS.cancel();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(RCS.isAlive());
	}
	
	/**
	 * Tests if restarting the thread keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testRestart() throws InterruptedException {
		assertTrue(RCS.isAlive());
		RCS.restart();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(RCS.isAlive());
	}
	
}
