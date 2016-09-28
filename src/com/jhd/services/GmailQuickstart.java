package com.jhd.services;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.jhd.utils.EmailTemplateReader;
@Path("/emailservice")
public class GmailQuickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Gmail API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/gmail-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList(GmailScopes.GMAIL_LABELS,GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_INSERT, GmailScopes.GMAIL_MODIFY, GmailScopes.GMAIL_READONLY, GmailScopes.MAIL_GOOGLE_COM);

	private static final String HashMap = null;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            GmailQuickstart.class.getResourceAsStream("/resources/client_id.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
	private static MimeMessage createEmail(String to, String cc, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress cAddress = cc.isEmpty() ? null : new InternetAddress(cc);
        InternetAddress fAddress = new InternetAddress("justhomedelivery@gmail.com");

        email.setFrom(fAddress);
        if (cAddress != null) {
            email.addRecipient(javax.mail.Message.RecipientType.CC, cAddress);
        }
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
        email.setSubject(subject);
       
        Multipart mp = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setText(bodyText);
        mp.addBodyPart(htmlPart);
        email.setContent(mp);
        
        return email;
    }

    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    
    
   /* @GET
    @Path("/sendmail/{recipientEmail}/{ccEmail}/{fromEmail}/{title}/{message}")
    public static void sendMail(@PathParam("recipientEmail") String recipientEmail,@PathParam("ccEmail")  String ccEmail,@PathParam("fromEmail")  String fromEmail,@PathParam("type")  String type,@PathParam("data")  String data) throws IOException, MessagingException {
       
    	
    	Message m = createMessageWithEmail(createEmail(recipientEmail, ccEmail, fromEmail, type, data));
        getGmailService().users().messages().send("me", m).execute();
    }*/
    
   
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
    	
    	Message m = createMessageWithEmail(createEmail((String)map.get("to"), (String)map.get("cc"),(String) template.get("subject"), (String) template.get("content")));
        getGmailService().users().messages().send("me", m).execute();
    	
    }
    	
	
    
    @POST
    @Path("/send/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(String msg) throws IOException, MessagingException {
    	String output = "POST:Jersey say : " + msg;
    	System.out.println(msg);
        return Response.status(200).entity(output).build();
    }
    
	public static void main(String args[]) throws IOException, MessagingException{
		
    }

}