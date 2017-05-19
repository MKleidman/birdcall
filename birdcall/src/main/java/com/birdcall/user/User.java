package com.birdcall.user;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.*;
import org.springframework.web.client.RestTemplate;

public class User implements Serializable {

	public User() {
		super();
	}

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String userEmail;
	private String userPassword;
	private String userCurrentZip;
	
    public Long getUserId() {
        return userId;
    }

    public void setUserEmail(String email) {
       this.userEmail = email;
    }

    public String getUserEmail() {
       return this.userEmail;
    }

    public void setUserPassword(String password) {
       this.userPassword = password;
    }

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserPassword() {
		return "";
	}
	
    public void setUserCurrentZip(String currentZip) {
        this.userCurrentZip = currentZip;
     }

     public String getUserCurrentZip() {
        return this.userCurrentZip;
     }
     
     public void notifyBirds(List<JSONObject> foundBirds) throws Exception{
    	 RestTemplate rest = new RestTemplate();
    	 JSONObject data = new JSONObject();
    	 JSONObject content = new JSONObject();
    	 JSONArray recipients = new JSONArray();
    	 JSONObject recipient = new JSONObject();
    	 recipient.put("address", this.getUserEmail());
    	 recipients.put(recipient);
    	 content.put("from", "postmaster@birdcall.tech");
    	 content.put("subject", "Bird Sighting!");
    	 StringBuffer message = new StringBuffer("Found the following birds\n");
    	 for (JSONObject foundBird : foundBirds) {
    		 message.append(foundBird.getInt("howMany") + " " +
    		 foundBird.getString("comName") + 
    		 " at " + 
    		 foundBird.getString("locName") +
    		 " at " +
    		 foundBird.getString("obsDt") +
    		 "\n");
    	 }
    	 content.put("text", message);
    	 data.put("recipients", recipients);
    	 data.put("content",  content);
    	 HttpHeaders headers = new HttpHeaders();
    	 headers.setContentType(MediaType.APPLICATION_JSON);
    	 headers.set("Authorization", "e2108ac9af6c244f192003ff5ea51a791cb755b3");
    	 System.out.println(data.toString());
    	 HttpEntity<String> entity = new HttpEntity<String>(data.toString(), headers);
    	 rest.exchange("https://api.sparkpost.com/api/v1/transmissions", HttpMethod.POST, entity, String.class);
     }

}