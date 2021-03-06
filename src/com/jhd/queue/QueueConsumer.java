package com.jhd.queue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.json.JSONObject;

import com.jhd.services.EmailService;
import com.jhd.services.GCMBroadcast;
import com.jhd.services.SendOTP;
import com.jhd.utils.EmailTemplateReader;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class QueueConsumer extends EndPoint implements Runnable, Consumer{
	
	public QueueConsumer(String endPointName) throws IOException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException{
		super(endPointName);		
	}
	
	public void run() {
		try {
			//start consuming messages. Auto acknowledge messages.
			channel.basicConsume(queueName, true,this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer "+consumerTag +" registered");		
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		 
		Map map = (HashMap)SerializationUtils.deserialize(body);
		
		try {
			if(map.get("eventType").equals("sendOtp")){
				SendOTP.sendOTP1((String)map.get("otp"), (String)map.get("mobile"));
			}
			else if(map.get("eventType").equals("pushmsgEvent")){
				GCMBroadcast.sendFCMMsg((String)map.get("to"), (String)map.get("title"), (String)map.get("msg"), (String)map.get("order_id"), (String)map.get("url"), (String)map.get("type"),(String)map.get("cat_id"));
			}
			else if(map.get("eventType").equals("sendSMS")){
				SendOTP.sendSMS((String)map.get("msg"), (String)map.get("mobile"));
			}
			else if(map.get("eventType").equals("sendEmail")){
				//JSONObject obj = new JSONObject((String)map.get("msg"));
			  	//HashMap message = EmailTemplateReader.jsonToMap(obj);
			  	//message.put("eventType", "sendEmail");
				EmailService.sendMailBCC(map);
			}else if(map.get("eventType").equals("sendOtpEmail")){
				SendOTP.sendOTP1((String)map.get("otp"), (String)map.get("mobile"));
				EmailService.sendOTPMail((String)map.get("otp"),(String)map.get("email"));
			}
		}
		catch (Exception e) {
					// TODO: handle exception
			e.printStackTrace();
		}
	}
		

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}