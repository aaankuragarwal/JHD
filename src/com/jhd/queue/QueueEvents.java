package com.jhd.queue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.jhd.utils.EmailTemplateReader;

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
		}finally{
			producer.close();
		}
		return "success";
	}
	
	@POST
	@Path("/addotpemailevent/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addOTPEmailEvent(String msg) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		  Producer producer = new Producer("otpQueue");
		  try{
		  	JSONObject obj = new JSONObject(msg);
		  	
		  	HashMap message = new HashMap();
			message.put("otp", obj.getString("otp"));
			message.put("mobile", obj.getString("mobile"));
			message.put("email", obj.getString("email"));
			message.put("eventType", "sendOtpEmail");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}finally{
			producer.close();
		}
		return "success";
	}
	
	@POST
	@Path("/addsmsevent/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addSMSEvent(String msg) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		  Producer producer = new Producer("smsQueue");
		  try{
		  	JSONObject obj = new JSONObject(msg);
		  	
		  	HashMap message = new HashMap();
			message.put("msg", obj.getString("msg"));
			message.put("mobile", obj.getString("mobile"));
			message.put("eventType", "sendSMS");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}finally{
			producer.close();
		}
		return "success";
	}
	
	@POST
	@Path("/addemailevent/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addEmailEvent(String msg) throws KeyManagementException, NoSuchAlgorithmException, IOException, URISyntaxException {
		  Producer producer = new Producer("emailQueue");
		  try{
		  	JSONObject obj = new JSONObject(msg);
		  	HashMap message = EmailTemplateReader.jsonToMap(obj);
			//HashMap message = new HashMap();
			//message.put("msg", msg);
			message.put("eventType", "sendEmail");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}finally{
			producer.close();
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
			message.put("to", obj.getString("to"));
			message.put("title", obj.getString("title"));
			message.put("msg", obj.getString("msg"));
			message.put("url", obj.getString("url"));
			message.put("order_id", obj.getString("order_id"));
			//message.put("cat_id", obj.getString("cat_id"));
			message.put("type", obj.getString("type"));
			message.put("eventType", "pushmsgEvent");
			producer.sendMessage(message);
		  } 
		  catch (Exception e) 
		{ 
				e.printStackTrace();
				return "failed";
		}finally{
			producer.close();
		}
		return "success";
	}
	
}
