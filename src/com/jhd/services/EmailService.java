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
	    	SendGrid sg = new SendGrid("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");
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
	    		int cashback = Integer.parseInt((String)(((HashMap) map.get("data")).get("CASHBACK")));
	    		if(cashback >0 && status.equalsIgnoreCase("delivered")){
	    			template = EmailTemplateReader.readTemplate("cashbackdelivered");
	    		}else{
	    			template = EmailTemplateReader.readTemplate(status.toLowerCase());
	        	}
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
	        
	        SendGrid sg = new SendGrid("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");
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

	 
	 
	 public static void sendOTPMail(String otp, String emailid) throws IOException, MessagingException {
	    	Map<String, String> template=null;
        	template= EmailTemplateReader.readTemplate("otp");

	    	for (Map.Entry<String, String> entry : template.entrySet()) {
	    		
	    		String value=entry.getValue();
	    		value = value.replace("#OTP#",otp);
	    		entry.setValue(value);	
	    	}
	    	
	    	Mail mail = new Mail();

	        Email fromEmail = new Email();
	        fromEmail.setName("Just Home Delivery");
	        fromEmail.setEmail("cs@justhomedelivery.com");
	        mail.setFrom(fromEmail);
	        
	        Personalization personalization = new Personalization();
	        
	        Email to = new Email();
	        to.setEmail((String)emailid);
	        personalization.addTo(to);

	        personalization.setSubject((String) template.get("subject"));
	        
	        Content content = new Content();
	        content.setType("text/plain");
	        content.setValue((String) template.get("content"));
	        
	        mail.addContent(content);
	        mail.addPersonalization(personalization);
	        
	        SendGrid sg = new SendGrid("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");
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
	        to.setEmail((String)map.get("ankur.agarwal5@yahoo.com"));
	        personalization.addTo(to);

	        mail.addSection("#NAME#", "Ankur Agarwal");
	        mail.addSection("#order id#", "JHD-1-1234565");
	        mail.addSection("#bill#", "Rs.130");
	        mail.addSection("#delivery#", "Rs.30");
	        mail.addSection("#total#", "Rs.160");
	        mail.addSection("#discount#", "Rs.0");
	        
	        mail.addSection("#discount#", "Rs.0");
	        mail.addSection("#discount#", "Rs.0");
	        mail.addSection("#discount#", "Rs.0");
	        mail.addSection("#discount#", "Rs.0");
	        mail.addSection("#discount#", "Rs.0");
	        
	        
	        
	        
	        
	    	mail.setTemplateId("7a1bed23-cf98-499b-9e5f-624f6c7ba843");
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
