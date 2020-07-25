package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The GUI class that handles the compass display.
 * Compass simulator modified from:
 * https://stackoverflow.com/questions/14142043/compass-needle-is-drawn-at-wrong-position
 *  
 * @author Sierra
 *
 */
public class Compass extends JFrame {
		
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6997832053937066874L;
	
	/**
	 * The panel for this class that will hold the compass display.
	 */
	CompassPanel c;

	/**
	 * The constructor that calls on the method that
	 * initializes the components of the display.
	 */
	public Compass() {
	    initComponents();
	}
	
	/**
	 * The method that initializes the display.
	 */
	public void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setSize(500, 500);
        setVisible(true);
        c = new CompassPanel();
        add(c, BorderLayout.CENTER);

    }


	/**
	 * An internal class extending JPanel that is added to the outer class.
	 * @author Sierra
	 */
	public class CompassPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = -460645283969363323L;
		Image bufImage;
        Graphics bufG;
        private int circleX, circleY, circleRadius;
        private int[] xPoints, yPoints;
        private double rotationAngle = Math.toRadians(0); 

        public CompassPanel() {
            setVisible(true);


        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            circleRadius = (int) (getWidth() * 0.7);
            circleX = 50;
            circleY = 50;

            g2d.setColor(Color.BLACK);
            for (int angle = 0; angle <= 360; angle += 5) {
                double sin = Math.sin(Math.toRadians(angle));
                double cos = Math.cos(Math.toRadians(angle));
                int x1 = (int) ((circleX + circleRadius / 2) - cos  * (circleRadius * 0.37) - sin * (circleRadius * 0.37));
                int y1 = (int) ((circleY + circleRadius / 2) + sin  * (circleRadius * 0.37) - cos * (circleRadius * 0.37));
                g2d.setColor(Color.BLACK);
                g2d.drawLine(x1, y1, (circleX + circleRadius / 2), (circleY + circleRadius / 2));
            }

            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            g2d.drawString("WEST", circleX - 45, circleY + circleRadius / 2 + 4);
            g2d.drawString("EAST", circleX + circleRadius + 13, circleY + circleRadius / 2 + 4);
            g2d.drawString("NORTH", circleX + circleRadius / 2 - 14, circleY - 15);
            g2d.drawString("SOUTH", circleX + circleRadius / 2 - 14, circleY + circleRadius + 25);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(circleX, circleY, circleRadius, circleRadius);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(circleX, circleY, circleRadius, circleRadius);

            xPoints = new int[] { (int) (circleX + circleRadius / 2),
                    (int) (circleX + circleRadius * 0.25),
                    (int) (circleX + circleRadius / 2),
                    (int) (circleX + circleRadius * 0.75) };

            yPoints = new int[] { (int) (circleY + 30),
                    (int) (circleY + circleRadius * 0.85),
                    (int) (circleY + circleRadius * 0.6),
                    (int) (circleY + circleRadius * 0.85) };

            Polygon fillPoly = new Polygon(xPoints, yPoints, 4);
            Polygon outerPoly = new Polygon(xPoints, yPoints, 4);

            int rotationX = circleX + (circleRadius / 2);
            int rotationY = circleX + (circleRadius / 2);

            g2d.setColor(Color.green);
            g2d.fillOval(rotationX, rotationY, 5, 5);
            
            

            
			AffineTransform a = new AffineTransform();
			a.setToRotation(rotationAngle, rotationX, rotationY);
			g2d.setTransform(a);
			g2d.setColor(Color.RED);
			g2d.drawPolygon(xPoints, yPoints , 4);
			g2d.getTransform();

				    
			

            g2d.setColor(Color.RED);
            g2d.fillPolygon(fillPoly);

            g2d.setColor(Color.black);
            g2d.draw(outerPoly);
        }

        @Override
        public void update(Graphics g) {
            int w = this.getSize().width;
            int h = this.getSize().height;

            if (bufImage == null) {
                bufImage = this.createImage(w, h);
                bufG = bufImage.getGraphics();
            }

            bufG.setColor(this.getBackground());
            bufG.fillRect(0, 0, w, h);
            bufG.setColor(this.getForeground());

            paint(bufG);
            g.drawImage(bufImage, 0, 0, this);
        }
        
        /**
         * Sets the angle the needle will be rotated by.
         * @param angle The angle to rotate the needle by.
         */
        public void setRotationAngle(int angle) {
            rotationAngle = angle;
        }
        
        /**
         * Returns the angle the needle will be rotated by in degrees as an int.
         * @return The angle the needle will be rotated by in degrees as an int
         */
        public int getRotationAngle() {
			return (int) Math.toDegrees(rotationAngle);	        	
        }
    }	
}
