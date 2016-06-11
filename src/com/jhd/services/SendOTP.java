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
  
  /*
  * This function is used to send OTP message on mobile number
  * */
  public static void verifyOTP(String oneTimePassword){
    try {
      //fetch your oneTimePassword from session or db
      //and compare it with the OTP sent from clien
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
/*  public static void main(String args[]) throws AddressException, MessagingException{
	generateOTP("91", "9560060874");  
  }*/

}
