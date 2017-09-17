package klepet;


import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@SuppressWarnings("deprecation")
public class Naloge {
	
	
	public static void main(String[] args) {	
    }
	
	public static void get_index(){
        try {
            String hello = Request.Get("http://chitchat.andrej.com")
                                  .execute()
                                  .returnContent().asString();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	//Pridobimo seznam uporabnikov.
	@JsonProperty("username")
	public static List<Uporabnik> get_users() {
		//Za pretvorbo iz JSON v seznam uporabnikov.
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new ISO8601DateFormat());
		try {
			  String responseBody = Request.Get("http://chitchat.andrej.com/users")
		          .execute()
		          .returnContent()
		          .asString();
			  String uporabniki = responseBody;
			  TypeReference<List<Uporabnik>> t = new TypeReference<List<Uporabnik>>() { };
			  List<Uporabnik> klepetalci = mapper.readValue(uporabniki, t);
			  return(klepetalci);
		} catch (IOException e) {
            e.printStackTrace();
        }
		return null; 
	}
	
	
	
	public static void log_in(String username) {  
	try {
		URI uri = new URIBuilder("http://chitchat.andrej.com/users")
		          .addParameter("username", username)
		          .build();
		String responseBody;
		responseBody = Request.Post(uri)
		      .execute()
		      .returnContent()
		      .asString();
		System.out.println(responseBody);
		
	} catch (URISyntaxException e) {
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
	public static void log_out(String username){
		URI uri;
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/users")
					.addParameter("username", username)
					.build();
			String responseBody = Request.Delete(uri)
					.execute()
					.returnContent()
					.asString();
			System.out.println(responseBody);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//	public static void send (String me, String myMessage ){
//		try {
//			URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
//			          .addParameter("username", me)
//			          .build();
//
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setDateFormat(new ISO8601DateFormat());
//			
//			String message = "{ \"global\" : true, \"text\" : \""+myMessage+"\"}";
//			  
//			String responseBody = Request.Post(uri)
//			          .bodyString(message, ContentType.APPLICATION_JSON)
//			          .execute()
//			          .returnContent()
//			          .asString();
//			System.out.println(responseBody);
//			
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void send (Boolean global, String recipient, String sender, String text, String sent_at){
		System.out.println("mewmew");
			try {
				URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				          .addParameter("username", sender)
				          .build();
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new ISO8601DateFormat());
				
				Sporocilo message = new Sporocilo(global, recipient, sender, text, sent_at);
				String jsonMessage = mapper.writeValueAsString(message);
				System.out.println(jsonMessage);
				
				String responseBody = Request.Post(uri)
				          .bodyString(jsonMessage, ContentType.APPLICATION_JSON)
				          .execute()
				          .returnContent()
				          .asString();
				System.out.println(responseBody);
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void receive (String me) {
		try {
			URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
			          .addParameter("username", me)
			          .build();
			String responseBody;
			responseBody = Request.Get(uri)
                    .execute()
                    .returnContent()
                    .asString();
			System.out.println(responseBody);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		  
	}
	
	//Trenutni cas oblike HH:mm.
	public static String trenutniCas() {
		Calendar cal = Calendar.getInstance();
		Date cas = cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String trenutni = df.format(cas);
		return trenutni;
	}
	
}
	

