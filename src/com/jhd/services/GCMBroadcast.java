package com.jhd.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

@Path("/GCMBroadcast")
public class GCMBroadcast {
	private static final long serialVersionUID = 1L;
	
	// The SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.
	private static final String SENDER_ID = "AIzaSyAEjqRRhoHq0eLA639DtbV5vGKrJrdNIHU";
	
	// This is a *cheat*  It is a hard-coded registration ID from an Android device
	// that registered itself with GCM using the same project id shown above.
	private static String ANDROID_DEVICE = "YOUR_CAPTURED_ANDROID_DEVICE_KEY";
		
	// This array will hold all the registration ids used to broadcast a message.
	// for this demo, it will only have the ANDROID_DEVICE id that was captured 
	// when we ran the Android client app through Eclipse.
	private List<String> androidTargets = new ArrayList<String>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	  @GET
	  @Path("/pushsinglemsg/")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String pushSingleMsg(String msg) throws IOException, MessagingException {
		  JSONObject obj = new JSONObject(msg);
		  String collapseKey = "";
		  String userMessage = "";
			
			try {
				userMessage = obj.getString("Message");
				collapseKey = obj.getString("CollapseKey");
				ANDROID_DEVICE=obj.getString("phnRegId");
				androidTargets.add(ANDROID_DEVICE);
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}

			// Instance of com.android.gcm.server.Sender, that does the
			// transmission of a Message to the Google Cloud Messaging service.
			Sender sender = new Sender(SENDER_ID);
			
			// This Message object will hold the data that is being transmitted
			// to the Android client devices.  For this demo, it is a simple text
			// string, but could certainly be a JSON object.
			Message message = new Message.Builder()
			
			// If multiple messages are sent using the same .collapseKey()
			// the android target device, if it was offline during earlier message
			// transmissions, will only receive the latest message for that key when
			// it goes back on-line.
			.collapseKey(collapseKey)
			.timeToLive(30)
			.delayWhileIdle(true)
			.addData("message", userMessage)
			.build();
			
			try {
				// use this for multicast messages.  The second parameter
				// of sender.send() will need to be an array of register ids.
				MulticastResult result = sender.send(message, androidTargets, 1);
				
				if (result.getResults() != null) {
					int canonicalRegId = result.getCanonicalIds();
					if (canonicalRegId != 0) {
						
					}
				} else {
					int error = result.getFailure();
					System.out.println("Broadcast failure: " + error);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			// We'll pass the CollapseKey and Message values back to index.jsp, only so
			// we can display it in our form again.
			return collapseKey;
	  }

	  public static String pushSingleMsg1(String userMessage,String collapseKey, String phnRegId) throws IOException, MessagingException {
		 
		  List<String> androidTargets = new ArrayList<String>();
			try {
				ANDROID_DEVICE=phnRegId;
				androidTargets.add(ANDROID_DEVICE);
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}

			// Instance of com.android.gcm.server.Sender, that does the
			// transmission of a Message to the Google Cloud Messaging service.
			Sender sender = new Sender(SENDER_ID);
			
			// This Message object will hold the data that is being transmitted
			// to the Android client devices.  For this demo, it is a simple text
			// string, but could certainly be a JSON object.
			Message message = new Message.Builder()
			
			// If multiple messages are sent using the same .collapseKey()
			// the android target device, if it was offline during earlier message
			// transmissions, will only receive the latest message for that key when
			// it goes back on-line.
			.collapseKey(collapseKey)
			.timeToLive(30)
			.delayWhileIdle(true)
			.addData("message", userMessage)
			.build();
			
			try {
				// use this for multicast messages.  The second parameter
				// of sender.send() will need to be an array of register ids.
				MulticastResult result = sender.send(message, androidTargets, 1);
				
				if (result.getResults() != null) {
					int canonicalRegId = result.getCanonicalIds();
					if (canonicalRegId != 0) {
						
					}
				} else {
					int error = result.getFailure();
					System.out.println("Broadcast failure: " + error);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			// We'll pass the CollapseKey and Message values back to index.jsp, only so
			// we can display it in our form again.
			return collapseKey;
	  }


	  public static String pushFCMSingleMsg(String userMessage,String collapseKey, String phnRegId) throws IOException, MessagingException {
			 
		  List<String> androidTargets = new ArrayList<String>();
			try {
				ANDROID_DEVICE=phnRegId;
				androidTargets.add(ANDROID_DEVICE);
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}

			// Instance of com.android.gcm.server.Sender, that does the
			// transmission of a Message to the Google Cloud Messaging service.
			Sender sender = new FCMSender(SENDER_ID);
			
			// This Message object will hold the data that is being transmitted
			// to the Android client devices.  For this demo, it is a simple text
			// string, but could certainly be a JSON object.
			Message message = new Message.Builder()
			
			// If multiple messages are sent using the same .collapseKey()
			// the android target device, if it was offline during earlier message
			// transmissions, will only receive the latest message for that key when
			// it goes back on-line.
			.collapseKey(collapseKey)
			.timeToLive(30)
			.delayWhileIdle(true)
			.addData("message", userMessage)
			.build();
			
			try {
				// use this for multicast messages.  The second parameter
				// of sender.send() will need to be an array of register ids.
				MulticastResult result = sender.send(message, androidTargets, 1);
				
				if (result.getResults() != null) {
					int canonicalRegId = result.getCanonicalIds();
					if (canonicalRegId != 0) {
						
					}
				} else {
					int error = result.getFailure();
					System.out.println("Broadcast failure: " + error);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			// We'll pass the CollapseKey and Message values back to index.jsp, only so
			// we can display it in our form again.
			return collapseKey;
	  }

}

class FCMSender extends Sender {

    public FCMSender(String key) {
        super(key);
    }

    @Override
    protected HttpURLConnection getConnection(String url) throws IOException {
        String fcmUrl = "https://fcm.googleapis.com/fcm/send";
        return (HttpURLConnection) new URL(fcmUrl).openConnection();
    }
}
