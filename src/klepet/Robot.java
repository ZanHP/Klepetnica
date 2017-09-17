package klepet;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private Timer timer;
	private int k;
	
	public Robot(ChatFrame chat) {
		this.chat = chat;
		this.k = 2;
	}

	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 2400);
	}

	public void deactivate() {
		timer.cancel();
		System.out.println("deakticacija");
	}
	
	@Override
	public void run() {
			chat.addMessage("primer", "k");
			try {
				chat.izpisUporabnikov();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		this.k++;
	}
}

