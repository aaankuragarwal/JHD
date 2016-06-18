package com.jhd.services;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
  public static String baseUrl = "http://localhost:8080/JHD/rest/otpservice";

  
  
  public static void main(String args[]){
	  Client client = Client.create();
      String Url  = baseUrl+"/sendotp";
      WebResource webResource = client.resource(Url);

      HashMap<String, String> requestBodyMap = new HashMap();
      requestBodyMap.put("mobileNumber","8010030912");
      requestBodyMap.put("OTP","1234");
      JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
      String input = requestBodyJsonObject.toString();

      ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
        .post(ClientResponse.class, input);

      String output= response.getEntity(String.class);
      
      System.out.println(output);  
  }

}
