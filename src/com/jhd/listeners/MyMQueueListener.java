package com.jhd.listeners;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jhd.queue.QueueConsumer;

public class MyMQueueListener  implements ServletContextListener {
	private QueueConsumer otpConsumer= null; 
	private QueueConsumer pushmsgConsumer= null;
	private QueueConsumer smsConsumer= null;
	private QueueConsumer emailConsumer= null;
	private Thread emailConsumerThread= null;
	private Thread smsConsumerThread= null;
	private Thread otpConsumerThread= null;
	private Thread pushmsgConsumerThread= null;
	
    public void contextInitialized(ServletContextEvent sce) {
        if((otpConsumerThread == null) || (!otpConsumerThread.isAlive()))	{
        	try {
        		otpConsumer = new QueueConsumer("otpQueue");
			} catch (KeyManagementException | NoSuchAlgorithmException
					| IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	otpConsumerThread = new Thread(otpConsumer);
        	otpConsumerThread.start();
        }
        
        if((smsConsumerThread == null) || (!smsConsumerThread.isAlive()))	{
        	try {
        		smsConsumer = new QueueConsumer("smsQueue");
			} catch (KeyManagementException | NoSuchAlgorithmException
					| IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	smsConsumerThread = new Thread(smsConsumer);
        	smsConsumerThread.start();
        }
        
        if((emailConsumerThread == null) || (!emailConsumerThread.isAlive()))	{
        	try {
        		emailConsumer = new QueueConsumer("emailQueue");
			} catch (KeyManagementException | NoSuchAlgorithmException
					| IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	emailConsumerThread = new Thread(emailConsumer);
        	emailConsumerThread.start();
        }
        
        if((pushmsgConsumerThread == null) || (!pushmsgConsumerThread.isAlive()))	{
        	try {
        		pushmsgConsumer = new QueueConsumer("pushmsgQueue");
			} catch (KeyManagementException | NoSuchAlgorithmException
					| IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	pushmsgConsumerThread = new Thread(pushmsgConsumer);
        	pushmsgConsumerThread.start();
        }
    }

    public void contextDestroyed(ServletContextEvent sce){
        try {
        	otpConsumerThread.stop();
        	otpConsumerThread.interrupt();
        	pushmsgConsumerThread.stop();
        	pushmsgConsumerThread.interrupt();
        	emailConsumerThread.stop();
        	emailConsumerThread.interrupt();
        	smsConsumerThread.stop();
        	smsConsumerThread.interrupt();
        } catch (Exception ex) {
        }
    }
}
