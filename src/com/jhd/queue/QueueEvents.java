package com.jhd.queue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Path("/queueevents")
public class QueueEvents {

	@POST
	@Path("/addotpevent/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addOTPEvent(String msg) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		  Producer producer = new Producer("otpQueue");
		  try{
		  	JSONObject obj = new JSONObject(msg);
		  	
		  	HashMap message = new HashMap();
			message.put("otp", obj.getString("otp"));
			message.put("mobile", obj.getString("mobile"));
			message.put("eventType", "sendOtp");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}
		return "success";
	}
	
	@POST
	@Path("/addpushmsgevent/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addPushMsgEvent(String msg) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		  Producer producer = new Producer("pushmsgQueue");
		  try{
		  	JSONObject obj = new JSONObject(msg);
		  	
		  	HashMap message = new HashMap();
			message.put("Message", obj.getString("Message"));
			message.put("CollapseKey", obj.getString("CollapseKey"));
			message.put("phnRegId", obj.getString("phnRegId"));
			message.put("eventType", "pushmsgEvent");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}
		return "success";
	}
	
}
