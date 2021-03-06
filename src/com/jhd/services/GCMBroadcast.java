package com.jhd.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Path;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONObject;

@Path("/GCMBroadcast")
public class GCMBroadcast {
	private static final long serialVersionUID = 1L;
	
	// The SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.
	private static final String SENDER_ID = "AIzaSyBcsaTBfYhMufsFgM7uPh1YBG2L3IaLb9c";
	
	// This is a *cheat*  It is a hard-coded registration ID from an Android device
	// that registered itself with GCM using the same project id shown above.
	private static String ANDROID_DEVICE = "YOUR_CAPTURED_ANDROID_DEVICE_KEY";
		
	// This array will hold all the registration ids used to broadcast a message.
	// for this demo, it will only have the ANDROID_DEVICE id that was captured 
	// when we ran the Android client app through Eclipse.
	private List<String> androidTargets = new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	  
	  public static void main(String args[]) throws IOException{
		  //sendFCMMsg("fNOJGgjKxaE:APA91bFdgqWJs5CHIjTy8A8YX_yc5TGwqSB-_1aptzaH456jArP4JIqDQPD3nARm9B3HJyfvmlYT3M6nm_gGjTGkHZUTv1rqH4R7VFLotzgPV_252o9NnBlQpmJbWHUgYUWXujcENBza","Offers2","JHD offers","test","https://s3.ap-south-1.amazonaws.com/jhd-category-images/4_cover_banner-Baby.png","promo");
	  }
	  
	  
	  public static void sendFCMMsg(String to,String title, String msg,String orderId,String url,String type,String cat_id) throws IOException{
		  OkHttpClient client = new OkHttpClient();
		  
		  JSONObject json = new JSONObject(); 
			JSONObject json1 = new JSONObject();    
			 
		    json.put("to",to);   
			 
			// populate message
			json1.put("title", title);
			json1.put("body", msg);
			if(type.equalsIgnoreCase("status")){
				json1.put("order_id", orderId);
			}else{
				json1.put("cat_id", cat_id);
				json1.put("url", url);
			}
			json1.put("type",type);
			json.put("data", json1);  
			System.out.println(json);
			
			
		  okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		  RequestBody body = RequestBody.create(mediaType, json.toString());
		  Request request = new Request.Builder()
		    .url("https://fcm.googleapis.com/fcm/send")
		    .post(body)
		    .addHeader("content-type", "application/json")
		    .addHeader("authorization", "key=AIzaSyBYKbkdhW7qCXJOD6g8qj3crSy5MbpK0VY")
		    .build();
		  
		  Response response = client.newCall(request).execute();
		  System.out.println(response.isSuccessful());
		  System.out.println(response.message());
		  response.close();
	  }
}
