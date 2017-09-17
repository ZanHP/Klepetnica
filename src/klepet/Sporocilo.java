package klepet;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sporocilo {
	private Boolean global;
	private String sender;
	private String recipient;
	private String text;
	private Date sent_at;
		
	private Sporocilo() {}
	
	public Sporocilo(Boolean javno, String recipient, String posiljatelj, String besedilo, Date poslano_ob) {
		this.global = javno;
		this.recipient = recipient;
		this.sender = posiljatelj;
		this.text = besedilo;
		this.sent_at = poslano_ob;
		
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


