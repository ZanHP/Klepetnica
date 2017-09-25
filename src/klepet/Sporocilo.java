package klepet;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sporocilo {
	private Boolean global;
	private String sender;
	private String recipient;
	private String text;
	private Date sent_at;
		
	@SuppressWarnings("unused")
	private Sporocilo() {}
	
	//prejeto sporocilo
	public Sporocilo(Boolean global, String recipient, String sender, String text, Date sent_at) {
		this.global = global;
		this.recipient = recipient;
		this.sender = sender;
		this.text = text;
		this.sent_at = sent_at;
	}
	
	//zasebno sporocilo
	public Sporocilo(Boolean global, String recipient, String text) {
		this.global = global;
		this.recipient = recipient;
		this.text = text;
	}
	
	//javno sporocilo
	public Sporocilo(Boolean global, String text) {
		this.global = global;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return sender + ": " + text + "    ob " + sent_at;
	}
	
	//"{ \"global\" : false, \"recipient\" : \""+friend+"\", \"text\" : \""+myMessage+"\"}";
	
	
	@JsonProperty("global")
	public Boolean getGlobal() {
		return global;
	}
	
	@JsonProperty("sender")
	public String getSender() {
		return sender;
	}

	
	@JsonProperty("recipient")
	public String getRecipient() {
		return recipient;
	}
	
	@JsonProperty("text")
	public String getText() {
		return text;
	}
	
	@JsonProperty("sent_at")
	public Date getSent_at() {
		return sent_at;
	}
}


