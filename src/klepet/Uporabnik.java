package klepet;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
		long cas = TimeUnit.MILLISECONDS.toMinutes(cal.getTimeInMillis() - this.lastActive.getTime());
		return cas;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}
	
	
}