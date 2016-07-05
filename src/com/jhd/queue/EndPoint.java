package com.jhd.queue;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * Represents a connection with a queue
 * @author syntx
 *
 */
public abstract class EndPoint{
	
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;
    protected String queueName;
	
    public EndPoint(String endpointName) throws IOException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException{
         this.endPointName = endpointName;
		
         //Create a connection factory
         ConnectionFactory factory = new ConnectionFactory();
	    
         //hostname of your rabbitmq server
         factory.setUri("amqp://WsNp4rFL:X983mITnJp6XG8KXV1Wi3O3u5yWWJz9e@spotted-cowslip-58.bigwig.lshift.net:11170/y9wZtr8-oq_b");

         //getting a connection
         connection = factory.newConnection();
	    
         //creating a channel
         channel = connection.createChannel();
	    
         channel.exchangeDeclare(endpointName, "fanout");
         queueName = channel.queueDeclare().getQueue();
         channel.queueBind(queueName, endpointName, "");
         //declaring a queue for this channel. If queue does not exist,
         //it will be created on the server.
        // channel.queueDeclare(endpointName, false, false, false, null);
    }
	
	
    /**
     * Close channel and connection. Not necessary as it happens implicitly any way. 
     * @throws IOException
     */
     public void close() throws IOException{
         this.channel.close();
         this.connection.close();
     }
}







