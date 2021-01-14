package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import view.Graph;

class GraphTest {

	List<Double> s= new ArrayList<>();
	Graph g = new Graph(s);
	
	
	
		
	@Test
	void test() {
		List<Double> l = new ArrayList<Double>();
	    l.add(2.2);

	    g = new Graph(l);
	    g.paintComponents(g.getGraphics());

	    g.update(l);
		assertTrue(g != null);
	}

}
