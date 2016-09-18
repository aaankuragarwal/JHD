package com.jhd.queue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.SerializationUtils;

import com.jhd.services.GCMBroadcast;
import com.jhd.services.GmailQuickstart;
import com.jhd.services.SendOTP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

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
				GCMBroadcast.pushSingleMsg1((String)map.get("Message"), (String)map.get("CollapseKey"), (String)map.get("phnRegId"));
			}
			else if(map.get("eventType").equals("sendSMS")){
				SendOTP.sendSMS((String)map.get("msg"), (String)map.get("mobile"));
			}
			else if(map.get("eventType").equals("sendEmail")){
				GmailQuickstart.sendMail(map);
				//GmailQuickstart.sendMail((String)map.get("to"), (String)map.get("cc"), (String)map.get("from"), (String)map.get("type"), (String)map.get("data"));
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