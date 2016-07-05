package com.jhd.queue;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.SerializationUtils;

public class Producer extends EndPoint{
	
	public Producer(String endPointName) throws IOException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException{
		super(endPointName);
	}

	public void sendMessage(Serializable object) throws IOException {
	    channel.basicPublish(endPointName,"", null, SerializationUtils.serialize(object));
	}	
}

