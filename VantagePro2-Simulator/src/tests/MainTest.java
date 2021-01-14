package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.Main;

class MainTest {

	@Test
	void test() {
		assertTrue(Main.deserialization("ISS_S.txt") != null);
	}

}
