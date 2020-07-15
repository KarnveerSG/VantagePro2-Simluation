package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AnemometerSensor;

/**
 * A test class for the Anemometer Sensor. The Anemometer measures wind speed and wind direction. 
 * 
 * @author Albert Lin
 * @version July 2020
 *
 */
class AnemometerSensorTest {
	
	/**
	 * An Anemometer Sensor object.
	 */
	private AnemometerSensor AS;

	/**
	 * Initializes the Anemometer Sensor object and its thread
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		AS = new AnemometerSensor();
		AS.start();
	}

	/**
	 * Tests if the thread is alive
	 * @throws InterruptedException
	 */
	@Test
	void testRun() throws InterruptedException {
		assertTrue(AS.isAlive());
		
		//uncomment when testing if data is updated when forced
//		for (int i = 0; i < 10; i++) {
//			AS.setWindDirection();
//			AS.setWindSpeed(3);
//			System.out.println("Wind direction: " + AS.getWindDirection());
//			System.out.println("Wind speed: " + AS.getWindSpeed());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(3);
//		}
		
		//uncomment when testing if data is automatically updated
//		for (int i = 0; i < 10; i++) {
//			System.out.println("Wind direction: " + AS.getWindDirection());
//			System.out.println("Wind speed: " + AS.getWindSpeed());
//			System.out.println();
//			TimeUnit.SECONDS.sleep(3);
//		}
	}
	
	/**
	 * Tests if the anemometer sensor object is not null
	 */
	@Test
	void testAnemometerSensor() {
		assertNotNull(new AnemometerSensor());
	}
	
	/**
	 * Tests if the wind direction measurements gotten are within the range of valid values. 
	 * Tests different units and time periods. Implicitly tests the setter methods.
	 */
	@Test
	void testGetWindDirection() {
		for (int i = 0; i < 1000; i++) {
			AS.setWindDirection();
			int windDirection = AS.getWindDirection();		
			assertTrue(windDirection >= 1 && windDirection <= 360, "wind direction must be within "
					+ "1° and 360°");
		}	
	}
	
	/**
	 * Tests if the wind speed measurements gotten are within the range of valid values. 
	 * Tests different units and time periods. Implicitly tests the setter methods.
	 */
	@Test
	void testGetWindSpeed() {
		for (int i = 0; i < 1000; i++) {
			// testing for wind speed in miles per hour
			AS.setWindSpeed(1);
			int windSpeed = (int) AS.getWindSpeed();		
			assertTrue(windSpeed >= 0 && windSpeed <= 200, "wind speed must be within "
						+ "0 mph and 200 mph");
			
			// testing for wind speed in knots
			AS.setWindSpeed(2);
			int windSpeed2 = (int) AS.getWindSpeed();		
			assertTrue(windSpeed2 >= 0 && windSpeed2 <= 173, "wind speed must be within "
						+ "0 knots and 173 knots");
			
			// testing for wind speed in meters per second
			AS.setWindSpeed(3);
			double windSpeed3 = AS.getWindSpeed();		
			assertTrue(windSpeed3 >= 0 && windSpeed3 <= 89, "wind speed must be within "
						+ "0 m/s and 89 m/s");
			
			// testing for wind speed in kilometers per hour
			AS.setWindSpeed(4);
			int windSpeed4 = (int) AS.getWindSpeed();		
			assertTrue(windSpeed4 >= 0 && windSpeed4 <= 322, "wind speed must be within "
						+ "0 km/h and 322 km/h");
		}
	}
	
	/**
	 * Tests for invalid inputs when calling setWindSpeed(int)
	 */
	@Test
	void testInvalidWindSpeedOption() {	
		//uncomment to test the invalid inputs.
//		AS.setWindSpeed(5);
//		AS.setWindSpeed(0);
//		AS.setWindSpeed(-1);
//		AS.setWindSpeed(1000);
	}
	
	/**
	 * Tests if pausing the thread keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testCancel() throws InterruptedException {
		assertTrue(AS.isAlive());
		AS.cancel();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(AS.isAlive());
	}
	
	/**
	 * Tests if restarting the thread keeps the thread alive.
	 * @throws InterruptedException
	 */
	@Test
	void testRestart() throws InterruptedException {
		assertTrue(AS.isAlive());
		AS.restart();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(AS.isAlive());
	}

}
