package com.birdcall.userbird;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.*;

public class UserBird implements Serializable {

	public UserBird() {
		super();
	}

	private static final long serialVersionUID = 1L;

	private Long userBirdId;
	private Long userBirdUserId;
	private String userBirdBirdName;
	
    public Long getUserBirdId() {
        return userBirdId;
    }
    
    public void setUserBirdId(Long userBirdId) {
        this.userBirdId = userBirdId;
     }

    public Long getUserBirdUserId() {
        return userBirdUserId;
    }

    public void setUserBirdUserId(Long userBirdUserId) {
       this.userBirdUserId = userBirdUserId;
    }

    public String getUserBirdBirdName() {
       return userBirdBirdName;
    }

    public void setUserBirdBirdName(String userBirdBirdName) {
       this.userBirdBirdName = userBirdBirdName;
    }
    
    
	public static JSONArray findBirdsNearUser(Long userId, String zip) throws Exception {
		JSONObject location = UserBird.getLatandLongForZip(zip);
		System.out.println(location.toString(2));
		Double lat = location.getDouble("lat");
		Double lng = location.getDouble("lng");
		String ebirdURL = "http://ebird.org/ws1.1/data/obs/geo/recent?lat=" +
			lat + "&lng=" +
			lng + "&fmt=json&dist=15&back=1";
		return new JSONArray(UserBird.getURL(ebirdURL).toString());
	}
	
	
	public static JSONObject getLatandLongForZip(String zip) throws Exception{
		String token = "9srotnmom2PWzfEojPRgmDrAj8plJjZTLqieNEDaGs6fHLE1BvhY9JysRXOBhNv2";
        return new JSONObject(UserBird.getURL("https://www.zipcodeapi.com/rest/" +
        		token +
        		"/info.json/" +
        		zip +
        		"/degrees").toString());
	}
	
	public static StringBuffer getURL(String urlString) throws Exception{
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(
                new InputStreamReader(
                conn.getInputStream()));
		StringBuffer result = new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
		}
        in.close();
        return result;
		
	}
}