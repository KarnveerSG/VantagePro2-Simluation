package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Timer;

import application.Main;

import javax.swing.*;

import model.IntegratedSensorSuite;

/**
 * This is the main class for the GUI
 * @author Sierra
 *
 */
public class GUI extends Thread {
	
	/**
	 * Map that store booleans for whether to enable or disable that sensor.
	 */
	public Map<String, Boolean> map;
		
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	/**
	 * The default JFrame for the GUI.
	 */
	private JFrame frame;
	
	/**
	 * The series of labels that will display the sensor data.
	 */
	private JLabel label1, label2, label3, label4, label5, label6, label7, label8, 
	label9, label10, label11, label12, label13, label14, label15, label16, label17, label18,
	label19, label20, label21, label22, label23, label24,label25;

	/**
	 * A check box to switch between metric and imperial units.
	 */
	private JCheckBox box;
	
	/**
	 * The compass display GUI class.
	 */
	private Compass c;
	
	/**
	 * The graph display GUI class.
	 */
	private Graph g;

	/**
	 * A map that stores the most recent update of sensor data from the ISS.
	 */
	private Map <String, Double> sensorData;
	
	/**
	 * A timer used to regularly call for updates from the ISS.
	 */
	private transient static Timer timer;
	
	/**
	 * A List to store the 10 most recent updates on the amount of rain fallen.
	 */
	private List<Double> history;
	
	/**
	 * A boolean to check if the units of measure are metric.
	 */
	private boolean isMetric;
	
	/**
	 * Stores the previous wind direction reading.
	 */
	private int oldCompass;
	
	/**
	 * The constructor class that initializes the history storage for rainfall
	 * and starts the initialization of the GUI
	 */
	public GUI() {		
		isMetric = false;
		oldCompass = 0;
		
		history = new ArrayList<Double>();
		map = new HashMap<String, Boolean>();
		
		// Modified from FrameDemo.java on the Oracle website.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
	}
	
	
	/**
	 *  Modified from the FrameDemo.java file on the oracle website.
	 *  Initializes the GUI display and assigns each label the data type it displays.
	 */
	private void init() {
        //Create and set up the window.
        frame = new JFrame("Integrated Sensor Suite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;
        frame.setPreferredSize(new Dimension(width, height));
        
        frame.setLayout(new GridLayout(5,5));
        
    	// Assign data types to each Label
        label1 = new JLabel("");
        box = new JCheckBox("Metric");
        box.setHorizontalAlignment(SwingConstants.CENTER);
        box.setBounds(50,50,125,50);
        box.addActionListener(theEvent -> {
        	isMetric = box.isSelected();
        });
        label1.add(box);        
        frame.add(label1);
        
        label2 = new JLabel("Rainfall: ");
        frame.add(label2);        
        label3 = new JLabel("Rain rate: ");
        frame.add(label3);        
        label4 = new JLabel("Wind speed: ");
        frame.add(label4);        
        label5 = new JLabel("Wind direction: ");
        frame.add(label5);        
        label6 = new JLabel("Wind chill: ");
        frame.add(label6);        
        label7 = new JLabel("Inner Temp: ");
        frame.add(label7);        
        label8 = new JLabel("Outer Temp: ");
        frame.add(label8);        
        label9 = new JLabel("UV Index: ");
        frame.add(label9);        
        label10 = new JLabel("UV Dose: ");
        frame.add(label10);         
        label11 = new JLabel("Inner Humidity: ");
        frame.add(label11);        
        label12 = new JLabel("Outer Humidity: ");
        frame.add(label12);        
        label13 = new JLabel("Barometer: ");
        frame.add(label13);        
        label14 = new JLabel("Evotranspiration: ");
        frame.add(label14);        
        label15 = new JLabel("Leaf Wetness: ");
        frame.add(label15);        
        label16 = new JLabel("Soil Moisture: ");
        frame.add(label16);        
        label17 = new JLabel("Dewpoint: ");
        frame.add(label17);        
        label18 = new JLabel("Heat Index: ");
        frame.add(label18); 
        
        label19 = new JLabel("");
        JCheckBox box2 = new JCheckBox("Disable Rain Sensor");
        //box2.setHorizontalAlignment(SwingConstants.CENTER);
        box2.setBounds(50,50,125,50);
        box2.addActionListener(theEvent -> {
        	map.put("RainCollector", !box2.isSelected());
        });
        label19.add(box2);
        frame.add(label19);
        
        label20 = new JLabel("");
        JCheckBox box3 = new JCheckBox("Disable Humidity Sensor");
       // box3.setHorizontalAlignment(SwingConstants.CENTER);
        box3.setBounds(50,50,125,50);
        box3.addActionListener(theEvent -> {
        	map.put("Humidity", !box3.isSelected());
        });
        label20.add(box3);
        frame.add(label20);
        label21 = new JLabel("");
        JCheckBox box4 = new JCheckBox("Disable Temp Sensor");
        //box4.setHorizontalAlignment(SwingConstants.CENTER);
        box4.setBounds(50,50,125,50);
        box4.addActionListener(theEvent -> {
        	map.put("Temperature", !box4.isSelected());
        });
        label21.add(box4);
        frame.add(label21);
        label22 = new JLabel("");
        JCheckBox box5 = new JCheckBox("Disable Anemometer");
        //box5.setHorizontalAlignment(SwingConstants.CENTER);
        box5.setBounds(50,50,125,50);
        box5.addActionListener(theEvent -> {
        	map.put("Anemometer", !box5.isSelected());
        });
        label22.add(box5);
        frame.add(label22);
        label23 = new JLabel("");
        JCheckBox box6 = new JCheckBox("Disable Leaf Sensor");
        //box6.setHorizontalAlignment(SwingConstants.CENTER);
        box6.setBounds(50,50,125,50);
        box6.addActionListener(theEvent -> {
        	map.put("LeafWetness", !box6.isSelected());
        });
        label23.add(box6);
        frame.add(label23);
        label24 = new JLabel("");
        JCheckBox box7 = new JCheckBox("Disable Soil Sensor");
        //box7.setHorizontalAlignment(SwingConstants.CENTER);
        box7.setBounds(50,50,125,50);
        box7.addActionListener(theEvent -> {
        	map.put("SoilMoisture", !box7.isSelected());
        });
        label24.add(box7);
        frame.add(label24);
        label25 = new JLabel("");
        JCheckBox box8 = new JCheckBox("Disable UV Sensor");
        //box8.setHorizontalAlignment(SwingConstants.CENTER);
        box8.setBounds(50,50,125,50);
        box8.addActionListener(theEvent -> {
        	map.put("UV", !box8.isSelected());
        });
        label25.add(box8);        
        frame.add(label25);
        
 
        //Display the window.
        frame.pack();
        frame.setVisible(true); 
        
        c = new Compass();
		
		g = new Graph(history);
		JFrame gFrame = new JFrame("Rainfall in Inches");
		gFrame.add(g);
		gFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gFrame.pack();
	    gFrame.setLocationByPlatform(true);
	    gFrame.setVisible(true);
        
        
        timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run() {
				update();
			}
		}, 0, 3000);
		
		
    }
	
	/**
	 * Updates the various display outputs of the GUI
	 */
	@SuppressWarnings("unchecked")
	public void update() {
		sensorData = new HashMap<>(((HashMap<String, Double>) Main.deserialization("ISS_S.txt")));		
		DecimalFormat df = new DecimalFormat("#.##"); 
		
		// Update text
		if (!isMetric) { // If not metric
			//System.out.println(label2 == null);
			label2.setText("Rainfall: " + df.format(sensorData.get("RainFall")) + " in.");
			label3.setText("Rain rate: " + df.format(sensorData.get("RainRate")) + " in/day.");
			label4.setText("Wind speed: " + df.format(sensorData.get("WindSpeed")) + " mph.");
			label5.setText("Wind direction: " + df.format(sensorData.get("WindDirection")) + "\u00B0");		
			label6.setText("Wind chill: " + df.format(sensorData.get("WindChill")) + "\u00B0" + "F");
			label7.setText("Inner temp: " + df.format(sensorData.get("InnerTemp")) + "\u00B0" + "F");
			label8.setText("Outer temp: " + df.format(sensorData.get("OuterTemp")) + "\u00B0" + "F");		
			label9.setText("UV index: " + df.format(sensorData.get("UVIndex")) + " Index.");
			label10.setText("UV dose: " + df.format(sensorData.get("UVDose")) + " MEDs.");
			label11.setText("Inner humidity: " + df.format(sensorData.get("InnerHumidity")) + " %");		
			label12.setText("Outer humidity: " + df.format(sensorData.get("OuterHumidity")) + " %");
			label13.setText("Barometer: " + df.format(sensorData.get("Barometer")) + "\" Hg");
			label14.setText("Evotranspiration: " + df.format(sensorData.get("Evotranspiration")) + " in.");		
			label15.setText("Leaf wetness: " + df.format(sensorData.get("LeafWetness")));
			label16.setText("Soil moisture: " + df.format(sensorData.get("SoilMoisture")) + " cb.");
			label17.setText("Dewpoint: " + df.format(sensorData.get("Dewpoint")) + "\u00B0" + "F");
			label18.setText("Heat inex: " + df.format(sensorData.get("HeatIndex")) + "\u00B0" + "F");
		} else { // If metric
			label2.setText("Rainfall: " + df.format(sensorData.get("RainFall")) + " mm.");
			label3.setText("Rain rate: " + df.format(sensorData.get("RainRate")) + " mm/day.");
			label4.setText("Wind speed: " + df.format(sensorData.get("WindSpeed")) + " km/hr.");
			label5.setText("Wind direction: " + df.format(sensorData.get("WindDirection")) + "\u00B0");		
			label6.setText("Wind chill: " + df.format(sensorData.get("WindChill")) + "\u00B0" + "C");
			label7.setText("Inner temp: " + df.format(sensorData.get("InnerTemp")) + "\u00B0" + "C");
			label8.setText("Outer temp: " + df.format(sensorData.get("OuterTemp")) + "\u00B0" + "C");		
			label9.setText("UV index: " + df.format(sensorData.get("UVIndex")) + " Index.");
			label10.setText("UV dose: " + df.format(sensorData.get("UVDose")) + " MEDs.");
			label11.setText("Inner humidity: " + df.format(sensorData.get("InnerHumidity")) + "%");		
			label12.setText("Outer humidity: " + df.format(sensorData.get("OuterHumidity")) + "%");
			label13.setText("Barometer: " + df.format(sensorData.get("Barometer")) + "mm Hg.");
			label14.setText("Evotranspiration: " + df.format(sensorData.get("Evotranspiration")) + " mm.");		
			label15.setText("Leaf wetness: " + df.format(sensorData.get("LeafWetness")));
			label16.setText("Soil moisture: " + df.format(sensorData.get("SoilMoisture")) + " cb.");
			label17.setText("Dewpoint: " + df.format(sensorData.get("Dewpoint")) + "\u00B0" + "C");
			label18.setText("Heat index: " + df.format(sensorData.get("HeatIndex")) + "\u00B0" + "C");
		}
		
		
		// Update Graph
		if (history.size() < 10) { 
			history.add(sensorData.get("RainFall"));
		} else {
			for(int i = 0; i < history.size() - 1; i++) {
				history.set(i, history.get(i + 1));
			}
			history.set(9, sensorData.get("RainFall"));
		}		
		List <Double> temp = new ArrayList<Double>(history);
		if (!temp.isEmpty()) g.update(temp);
		else this.update();
		
		// Update Compass
		c.c.setRotationAngle(sensorData.get("WindDirection").intValue() /*- oldCompass*/);  // Calculation for angle adjust may need work.
		oldCompass = sensorData.get("WindDirection").intValue();
		c.repaint();
		
		//Serialize
		Main.serialization("GUI_S.txt", map);
		Main.serialization("GUI_M.txt", isMetric);

	}
}