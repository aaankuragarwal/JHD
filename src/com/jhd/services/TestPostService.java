package com.jhd.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


/*
* This code is based on jersey-client library.
* For gradle based project use compile 'com.sun.jersey:jersey-client:1.18.4'
* You can also download the jar and add it to you project.
* */

public class TestPostService {

  //Base URL
  public static String baseUrl = "http://justhomedeliveries.herokuapp.com/rest";

  
  
  public static void main(String args[]) throws IOException{
	  Client client = Client.create();
      String Url  = baseUrl+"/queueevents/addotpevent/";
      WebResource webResource = client.resource(Url);

      HashMap<String, String> requestBodyMap = new HashMap();
      requestBodyMap.put("mobile","9987800826");
      requestBodyMap.put("otp","1234");
      requestBodyMap.put("eventType", "sendOtp");
      JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
      
      String input = requestBodyJsonObject.toString();
      
      ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
        .post(ClientResponse.class, input);

      String output= response.getEntity(String.class);
      
      System.out.println(output);  
  }

}

@XmlRootElement
 class user {

	@XmlElement
   private String name;

	@XmlElement
   private String email;

	@XmlElement
   private String mobile;
	
	@XmlElement
   private String password;
   
	@XmlElement
	private String password_confirmation;
   
   
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPassword_confirmation() {
	return password_confirmation;
}
public void setPassword_confirmation(String password_confirmation) {
	this.password_confirmation = password_confirmation;
}
   
   
    // Other setters and methods
}
