package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Main;
import model.IntegratedSensorSuite;

class IntegratedSensorSuiteTest {
	public IntegratedSensorSuite ISSTest;
	public HashMap<String, Double> testSensorData;
	

	@BeforeEach
    void setUp() throws Exception {
        ISSTest = new IntegratedSensorSuite(1, 200);
        
        testSensorData = new HashMap<String, Double>(); 
    }
	
	@Test
	void test() {
		for(String s : ISSTest.sensorData.keySet()) {
			assertEquals(ISSTest.sensorData.get(s), 0.0);
		}
		assertEquals(ISSTest.getTransmitterId(), 1);
		ISSTest.setTransmitterId(3);
		assertEquals(ISSTest.getTransmitterId(), 3);
		ISSTest.updateData();
		
		ISSTest.convertData(true);
		//ISSTest.enableSensors();
		
		HashMap<String, Boolean> e = new HashMap<String, Boolean>();
		e.put("UV", true);
		e.put("Humidity", false);
		ISSTest.enableSensors(e);
		
		ISSTest.start();
		
		ISSTest.interrupt();
	}

}
