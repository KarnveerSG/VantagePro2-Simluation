package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;
import view.GUI;

class GUITest {
	
	Main main;
	
	GUI test = new GUI();
	
	@Test
	void initializeTest() {
		test.start();
	}
	
	
	@Test
	void testUpdate() {

		test.update();
		test.interrupt();
	}
}
