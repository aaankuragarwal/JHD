package com.jhd.queue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
	public Main() throws Exception{
		
		QueueConsumer consumer = new QueueConsumer("queue1");
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
		
		QueueConsumer consumer1 = new QueueConsumer("queue2");
		Thread consumerThread1 = new Thread(consumer1);
		//consumerThread1.start();
		
		Producer producer = new Producer("queue1");
		//Producer producer1 = new Producer("queue2");
		
		/*for (int i = 0; i < 100; i++) {
			HashMap message = new HashMap();
			message.put("message number", i);
			producer.sendMessage(message);
			//producer1.sendMessage(message);
			System.out.println("Message Number "+ i +" sent.");
		}*/
		
		HashMap message = new HashMap();
		message.put("otp", "1234");
		message.put("mobile", "9987800826");
		message.put("eventType", "sendOtp");
		producer.sendMessage(message);
		
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception{
	  new Main();
	}
}