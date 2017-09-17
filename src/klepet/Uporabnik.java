package klepet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.json.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Uporabnik {
	private String username;
	private Date lastActive;
	
	private Uporabnik() { }
	
	public Uporabnik(String username, Date lastActive) {
		this.username = username;
		this.lastActive = lastActive;
	}

	@Override
	public String toString() {
		return "Vzdevek: " + username + ", Nazadnje aktiven: " + lastActive + "]";
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("last_active")
	public long getLastActive(){
		Calendar cal = Calendar.getInstance();

		return (cal.getTimeInMillis() - this.lastActive.getTime());
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}
	
	
}