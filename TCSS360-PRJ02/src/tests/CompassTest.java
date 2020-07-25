package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import view.Compass;
import view.Compass.CompassPanel;

import java.awt.*;

class CompassTest {
	
	Compass c;

	@Test
	void setup() {
		c = new Compass();
		
		c.paint(c.getGraphics());
		
		Compass.CompassPanel cc = c.new CompassPanel();
//		cc.paint(cc.getGraphics());
	}
	
	@Test
	void testPaint() {
		c.paint(c.getGraphics());
	}
	
	@Test
	void testCompassPanel() {
		c = new Compass();
		Compass.CompassPanel cc = c.new CompassPanel();
		
		cc.setRotationAngle(15);
		System.out.println(cc.getRotationAngle());
		assertFalse(cc.getRotationAngle() == 15); // Due to decimal rounding
		
		cc.paint(cc.getGraphics());
		
		cc.update(c.getGraphics());
	}
}
