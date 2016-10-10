package com.jhd.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.jhd.utils.EmailTemplateReader;
import com.sendgrid.BccSettings;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.MailSettings;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class EmailService {
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
	    	
	    	Email from = new Email("cs@justhomedelivery.com");
	        String subject = (String) template.get("subject");
	        Email to = new Email((String)map.get("to"));
	        Content content = new Content("text/plain", (String) template.get("content"));
	        Mail mail = new Mail(from, subject, to, content);
	    	
	    	//Message m = createMessageWithEmail(createEmail((String)map.get("to"), (String)map.get("cc"),(String) template.get("subject"), (String) template.get("content")));
	    	SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    	Request request = new Request();
	        try {
	          request.method = Method.POST;
	          request.endpoint = "mail/send";
	          request.body = mail.build();
	          Response response = sg.api(request);
	          System.out.println(response.statusCode);
	          System.out.println(response.body);
	          System.out.println(response.headers);
	        } catch (IOException ex) {
	          throw ex;
	        }
	    	
	    }
	 
	 public static void sendMailBCC(Map map) throws IOException, MessagingException {
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
	    	
	    	Mail mail = new Mail();

	        Email fromEmail = new Email();
	        fromEmail.setName("Just Home Delivery");
	        fromEmail.setEmail("cs@justhomedelivery.com");
	        mail.setFrom(fromEmail);
	        
	        Personalization personalization = new Personalization();
	        
	        Email to = new Email();
	        to.setEmail((String)map.get("to"));
	        personalization.addTo(to);

	        Email bcc = new Email();
	        bcc.setEmail("rishav@justhomedelivery.com");
	        personalization.addBcc(bcc);
	        bcc.setEmail("nishant@justhomedelivery.com");
	        personalization.addBcc(bcc);
	        bcc.setEmail("ankur@justhomedelivery.com");
	        personalization.addBcc(bcc);
	        personalization.setSubject((String) template.get("subject"));
	        
	        Content content = new Content();
	        content.setType("text/plain");
	        content.setValue((String) template.get("content"));
	        
	        mail.addContent(content);
	        mail.addPersonalization(personalization);
	        
	        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    	Request request = new Request();
	        try {
	          request.method = Method.POST;
	          request.endpoint = "mail/send";
	          request.body = mail.build();
	          Response response = sg.api(request);
	          System.out.println(response.statusCode);
	          System.out.println(response.body);
	          System.out.println(response.headers);
	        } catch (IOException ex) {
	          throw ex;
	        }
	 }
	 
	 
	 
	 public static void sendMailTemplate(Map map) throws IOException, MessagingException {
	    	Mail mail = new Mail();

	        Email fromEmail = new Email();
	        fromEmail.setName("Just Home Delivery");
	        fromEmail.setEmail("cs@justhomedelivery.com");
	        mail.setFrom(fromEmail);
	        
	        Personalization personalization = new Personalization();
	        
	        Email to = new Email();
	        to.setEmail((String)map.get("to"));
	        personalization.addTo(to);

	        HashMap<String,String> dataMap = (HashMap<String,String>)map.get("data");
	    	for (Map.Entry<String,String> dataentry : dataMap.entrySet()) {
	    		personalization.addSubstitution("#" + dataentry.getKey() + "#", dataentry.getValue());
	    	}
	        
	    	mail.setTemplateId("f1251073-8baf-43d5-bd0f-d69cb0e014bd");
	        mail.addPersonalization(personalization);
	        
	        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    	Request request = new Request();
	        try {
	          request.method = Method.POST;
	          request.endpoint = "mail/send";
	          request.body = mail.build();
	          Response response = sg.api(request);
	          System.out.println(response.statusCode);
	          System.out.println(response.body);
	          System.out.println(response.headers);
	        } catch (IOException ex) {
	          throw ex;
	        }
	 }
	        
}
