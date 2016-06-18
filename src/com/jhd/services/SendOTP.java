package com.jhd.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


/*
* This code is based on jersey-client library.
* For gradle based project use compile 'com.sun.jersey:jersey-client:1.18.4'
* You can also download the jar and add it to you project.
* */

@Path("/otpservice")
public class SendOTP {

  //Base URL
  public static String baseUrl = "https://sendotp.msg91.com/api";

  // Your application key
  public static String applicationKey = "LFhmq33RzS5g3GR9SKIXJJXvxvdaiyZop4kzX4XroG56-IleyhL7ZVCIIGasO2_EPrx1FaSH-CXxFZIe7eFrYeZy5FEnjxkEKDYUzBdF_MFAUivFZgk5eGYuv_RORhKTnCR5W61F1QKDQXpVLUAEg2ZRU8qTI0Uxv8xGjw37g0M=";

  /* This function is used to send OTP message on mobile number
  * */
  
  @GET
  @Produces("application/json")
  @Path("generateotp/{countryCode}/{mobileNumber}")
  public String generateOTP(@PathParam("countryCode")  String countryCode,@PathParam("mobileNumber") String mobileNumber){
	  String output=null;
	  try {
      Client client = Client.create();
      String Url  = baseUrl+"/generateOTP";
      WebResource webResource = client.resource(Url);

      HashMap<String, String> requestBodyMap = new HashMap();
      requestBodyMap.put("countryCode",countryCode);
      requestBodyMap.put("mobileNumber",mobileNumber);
      requestBodyMap.put("getGeneratedOTP","true");
      JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
      String input = requestBodyJsonObject.toString();

      ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
        .header("application-Key", applicationKey)
        .post(ClientResponse.class, input);

       output= response.getEntity(String.class);
      
      System.out.println(output);
      //fetch your oneTimePassword and save it to session or db
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output;
  }
  
  
  @POST
  @Path("/sendotp/")
  @Consumes(MediaType.APPLICATION_JSON)
  public String sendOTP(String msg) throws IOException, MessagingException {
	  	
	  	JSONObject obj = new JSONObject(msg);
		//Your authentication key
		String authkey = "114971AFjay0rnEGI5754ff0a";
		//Multiple mobiles numbers separated by comma
//		String mobiles = "9560060874";
		//Sender ID,While using route4 sender id should be 6 characters long.
		String senderId = "JUSTHD";
		//Your message to send, Add URL encoding here.
		String message = obj.getString("otp")+" is your verification code.";
		//define route
		String route="4";

		
		//Prepare Url
		URLConnection myURLConnection=null;
		URL myURL=null;
		BufferedReader reader=null;

		//encoding message 
		String encoded_message=URLEncoder.encode(message);

		//Send SMS API
		String mainUrl="https://control.msg91.com/api/sendhttp.php?";
		//Prepare parameter string 
		StringBuilder sbPostData= new StringBuilder(mainUrl);
		sbPostData.append("authkey="+authkey); 
		sbPostData.append("&mobiles="+obj.getString("mobile"));
		sbPostData.append("&message="+encoded_message);
		sbPostData.append("&route="+route);
		sbPostData.append("&sender="+senderId);

		//final string
		mainUrl = sbPostData.toString();
		try
		{
		    //prepare connection
		    myURL = new URL(mainUrl);
		    myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
		    reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
		    //reading response 
		    String response;
		    while ((response = reader.readLine()) != null) 
		    //print response 
		    System.out.println(response);
		    
		    //finally close connection
		    reader.close();
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
}