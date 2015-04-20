import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class Catalog extends JFrame implements ActionListener{
	JPanel ef=new JPanel(new GridBagLayout());
	 private JPanel control;
	JTextField titlei,desci,addi;
	 String title=null;
	 String desc=null;
	 String add=null;
	 String lat=null;
	 String lon=null;
	 public Catalog()
	 {
	createCatalog();
	}
public void createCatalog() {
	
	   
	   ef.setVisible(true);
	   ef.setName("File Location Catalog");
	   ef.setSize(640,480);
	   //ef.setLocation(null);
	   //ef.setDefaultCloseOperation(EXIT_ON_CLOSE);
	   JOptionPane.showMessageDialog(ef,"Welcome.\nEnter the details of your document and it will be Geo-tagged and stored in our database.");
	  
	   GridBagConstraints gbc = new GridBagConstraints();
	   JButton button1 = new JButton("SUBMIT");
	   button1.addActionListener(this);
	   gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.anchor = GridBagConstraints.WEST;
       JLabel titlel = new JLabel("Title: ");
       JLabel descl = new JLabel("Description: ");
       JLabel addl = new JLabel("Address: ");
       titlei = new JTextField(20);
       desci = new JTextField(20);
       addi = new JTextField(20);
       
       ef.add(titlel, gbc);
       gbc.gridy++;
       ef.add(descl, gbc);
       gbc.gridy++;
       ef.add(addl, gbc);
       
       
       gbc.gridx++;
       gbc.gridy = 0;
       ef.add(titlei, gbc);
       gbc.gridy++;
       ef.add(desci, gbc);
       gbc.gridy++;
       ef.add(addi, gbc);
       gbc.gridx+=2;
       
       ef.add(button1);
       
      
	   ef.setVisible(true);
	   
	    }
public void actionPerformed(ActionEvent e) {
	 
     //ef.dispose();
      	title= titlei.getText();
      	desc=desci.getText();
      	add=addi.getText();
      	titlei.setText("");
      	desci.setText("");
      	addi.setText("");
      	try{
      	GoogleResponse res = new AddressConverter().convertToLatLong(add);
      	  if(res.getStatus().equals("OK"))
      	  {
      		  
      	   Result result[] = res.getResults();
      	   
      		   lat=result[0].getGeometry().getLocation().getLat();
      		   lon=result[0].getGeometry().getLocation().getLng();
      	   /* System.out.println("Lattitude of address is :"  +result.getGeometry().getLocation().getLat());
      	    System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
      	    System.out.println("Location is " + result.getGeometry().getLocation_type());
      	    */
      		   
      	   
      	  }
      	  else
      	  {
      	   System.out.println(res.getStatus());
      	  }
      	  
      	}catch(IOException ioe){
      	
      	}
      	
      	
      	try{   
      				 // To connect to mongodb server
      		         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
      		         // Now connect to your databases
      		         DB db = mongoClient.getDB( "Catalog" );
      				 System.out.println("Connect to database successfully");
      				 DBCollection coll = db.getCollection("fixed");
      		         System.out.println("Collection created successfully");
      		         BasicDBObject doc = new BasicDBObject("title", title).
      		                 append("description", desc).
      		                 append("latitude",lat).
      		                 append("longitude",lon).
      		                 append("address",add);
      		         coll.insert(doc);
      		      }catch(Exception er){
      			     System.err.println( er.getClass().getName() + ": " + er.getMessage() );
      			  } 
      }

public static void main(String[] args) {
	
	 Catalog  ex = new Catalog();  
    
	 }


 }	
	


