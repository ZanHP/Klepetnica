package klepet;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private int k;
	
	private static boolean isPrime(int n) {
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) { return false; }
		}
		return true;
	}

	public Robot(ChatFrame chat) {
		this.chat = chat;
		this.k = 2;
	}

	/**
	 * Activate the robot!
	 */
	public void activate() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 5000, 1000);
	}
	
	@Override
	public void run() {
		if (isPrime(this.k)) {
			chat.addMessage("primer", "k");
			try {
				chat.izpisUporabnikov();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.k++;
	}
}

