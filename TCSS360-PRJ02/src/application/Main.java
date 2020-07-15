package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import model.*;

public class Main {

	public static void main(String[] args) {		
		//Start ISS on its own thread
		new Thread(new IntegratedSensorSuite(1)).start();
		
		//Start GUI on its own thread
		//new Thread(new GUI())).start();
	}
	
	
	//This is taken from group 1's project Main
	/**
	   * A method which takes a filepath name and object to serialize. The file can then be deserialized to return the object. 
	   * @author chanteltrainer
	   * @param theFileName
	   * @param theObject
	   */
	  public static void serialization(String theFilePath, Object theObject) {
	         try
	          {    
	              //This will clear the file of any previous data
	              PrintWriter writer = new PrintWriter(theFilePath);
	              writer.print("");
	              writer.close();
	             
	              //Saving of object in a file 
	              FileOutputStream file = new FileOutputStream(theFilePath); 
	              ObjectOutputStream out = new ObjectOutputStream(file); 
	                
	              // Method for serialization of object 
	              out.writeObject(theObject); 
	                
	              out.close(); 
	              file.close(); 
	                
	              System.out.println("Object has been serialized \n"); 
	    
	          } 
	            
	          catch(IOException ex) 
	          { 
	              System.out.println("Serialization Failed."); 
	              ex.printStackTrace();
	          } 
	  }
	  
	  /**
	   * For testing purposes, a method to deserialize the data.
	   * @param theFilePath
	   */
	  public static Object deserialization(String theFilePath) {
	      try
	      {    
	          // Reading the object from a file 
	          FileInputStream file = new FileInputStream(theFilePath); 
	          ObjectInputStream in = new ObjectInputStream(file);
	            
	          // Method for deserialization of object 
	          Object deserial = (Object) in.readObject(); 
	            
	          in.close(); 
	          file.close(); 
	            
	          return deserial;
	      } 
	        
	      catch(IOException ex) 
	      { 
	          System.out.println("IOException is caught in ds");
	          return ex;
	      } 
	        
	      catch(ClassNotFoundException ex) 
	      { 
	          System.out.println("ClassNotFoundException is caught in ds");
	          return ex;
	      } 
	  }

}
