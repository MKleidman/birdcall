package com.birdcall.app;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.birdcall.user.UserBo;
import com.birdcall.user.User;
import com.birdcall.userbird.UserBird;
import com.birdcall.userbird.UserBirdBo;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.out.println("-------- MySQL JDBC Connection Testing ------------");

    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return;
    	}

    	System.out.println("MySQL JDBC Driver Registered!");
    	Connection connection = null;

    	try {
    		connection = DriverManager
    		.getConnection("jdbc:mysql://localhost:3306/birdcall","socialcode", "edoclaicos");

    	} catch (SQLException e) {
    		System.out.println("Connection Failed! Check output console");
    		e.printStackTrace();
    		return;
    	}

    	if (connection != null) {
    		System.out.println("You made it, take control your database now!");
    	} else {
    		System.out.println("Failed to make connection!");
    	}
    
        ApplicationContext appContext =
          	  new ClassPathXmlApplicationContext("spring/config/BeanLocations.xml");
        UserBo userBo = (UserBo)appContext.getBean("userBo");
        UserBirdBo userBirdBo = (UserBirdBo)appContext.getBean("userBirdBo");
        
        User user = userBo.findByUserEmail(args[0]);
        List<UserBird> userBirds = userBirdBo.getBirdsForUser(user.getUserId());
        List<String> birdNames = new ArrayList<String>();
        List<JSONObject> foundBirds = new ArrayList<JSONObject>();
        for (UserBird bird : userBirds) {
        	System.out.println("Finding " + bird.getUserBirdBirdName() + " for user " + args[0]);
        	birdNames.add(bird.getUserBirdBirdName());
        }
        try {
        	JSONArray birdSightings =  UserBird.findBirdsNearUser(user.getUserId(), user.getUserCurrentZip());
            for (Object birdSightingObject : birdSightings) {
            	JSONObject birdSighting = (JSONObject) birdSightingObject;
            	String comName = birdSighting.getString("comName");
            	if (birdNames.contains(comName))  {
            		System.out.println("found bird " + comName + "\n" + birdSighting.toString(2));
            		foundBirds.add(birdSighting);
            	}
            }
            if (foundBirds.size() > 0) {
            	// foundSome birds.  Let's notify
            	user.notifyBirds(foundBirds);
            }
        } catch (Exception e) {
    		System.out.println("Couldn't get birds near user " + user.getUserEmail());
    		e.printStackTrace();
    	}
        System.exit(0);
    }
}
