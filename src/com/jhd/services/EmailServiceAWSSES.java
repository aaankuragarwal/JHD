package com.jhd.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import java.io.IOException;

import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;
import com.jhd.utils.EmailTemplateReader;

public class EmailServiceAWSSES {
	static final String FROM = "SENDER@EXAMPLE.COM";  // Replace with your "From" address. This address must be verified.
	 public static void sendMail(Map map) throws IOException, MessagingException {
	    	Map<String, String> template=null;
	    	if("welcome".equalsIgnoreCase((String)map.get("type"))){
	        	template= EmailTemplateReader.readTemplate("welcome");
	    	}else{
	    		String status = (String)(((HashMap) map.get("data")).get("status"));
	        	template = EmailTemplateReader.readTemplate(status.toLowerCase());
	    	}

	    	
	    	HashMap<String,String> dataMap = (HashMap<String,String>)map.get("data");
	    	for (Map.Entry<String, String> entry : template.entrySet()) {
	    		
	    		String value=entry.getValue();
	    		for (Map.Entry<String,String> dataentry : dataMap.entrySet()) {
	    			value = value.replace("#" + dataentry.getKey() + "#", dataentry.getValue());
	    		}
	    		entry.setValue(value);	
	    	}
	    	
	    	
	    	Destination destination = new Destination().withToAddresses(new String[]{(String)map.get("to")});
	        
	        // Create the subject and body of the message.
	        Content subject = new Content().withData((String) template.get("subject"));
	        Content textBody = new Content().withData((String) template.get("content")); 
	        Body body = new Body().withText(textBody);
	        
	        // Create a message with the specified subject and body.
	        Message message = new Message().withSubject(subject).withBody(body);
	        
	        // Assemble the email.
	        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
	        
	        try
	        {        
	            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
	        
	            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials. 
	            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials 
	            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables 
	            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY. 
	            // For more information, see http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html
	            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();
	               
	            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox 
	            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS 
	            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using 
	            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1 
	            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html 
	            Region REGION = Region.getRegion(Regions.US_WEST_2);
	            client.setRegion(REGION);
	       
	            // Send the email.
	            client.sendEmail(request);  
	            System.out.println("Email sent!");
	        }
	        catch (Exception ex) 
	        {
	            System.out.println("The email was not sent.");
	            System.out.println("Error message: " + ex.getMessage());
	        }
	    }
	      
	    	
  }
