package com.jhd.services;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Path("/paymentservice")
public class PayU {

  public static String baseUrl = "https://test.payu.in/_payment";

  // Your application key
  public static String key = "gtKFFx";

  @SuppressWarnings("unchecked")
public static String doTransaction(String countryCode, String mobileNumber){
	  String output=null;
	  try {
      Client client = Client.create();
      String Url  = baseUrl;
      WebResource webResource = client.resource(Url);
      
      MultivaluedMap formData = new MultivaluedMapImpl();

      formData.add("key",key);
      formData.add("txnid","343726");
      formData.add("amount","10.00");
      formData.add("productinfo","test");
      formData.add("firstname","ANkur");
      formData.add("lastname","agarwal");
      formData.add("email","ankur.agarwal5@yahoo.com");
      formData.add("phone",mobileNumber);
      formData.add("surl","www.facebook.com");
      formData.add("furl","www.gmail.com");
      formData.add("hash",getCheckSum());

      ClientResponse response = webResource
    		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
    		    .post(ClientResponse.class, formData);

      output = response.getEntity(String.class);
      
      //System.out.println(output);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output;
  }
  
  public static String getCheckSum() throws NoSuchAlgorithmException, IOException{
	  	MessageDigest md = MessageDigest.getInstance("SHA-512"); 
		String s="gtKFFx|343726|10.00|test|ANkur|ankur.agarwal5@yahoo.com|||||||||||eCwWELxi";
		byte[] b = s.getBytes();
		byte[] mdbytes = md.digest(b); 
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++)
		{ 
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1)); 
		} 
		//System.out.println("Hex format : " + sb.toString());
		return sb.toString();
  }
  
  
  public static void main(String args[]) throws AddressException, MessagingException, NoSuchAlgorithmException, IOException{
	  String res=doTransaction("91", "9987800826");
	  //JSONObject obj = new JSONObject(res);
	  File file = new File("C:\\JHD\\test.html");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(res);
		bw.close();
		System.out.println(res);
	  
  }

  

  
}
