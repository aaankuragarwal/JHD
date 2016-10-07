package com.jhd.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.jhd.utils.EmailTemplateReader;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
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
}
