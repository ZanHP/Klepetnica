package klepet;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private Zasebni_klepet zasebni_chat;
	private Timer timer;
	private int k;
	
	public Robot(ChatFrame chat) {
		this.chat = chat;
		this.k = 2;
	}
	
	public Robot(Zasebni_klepet zasebni_chat) {
		this.zasebni_chat = zasebni_chat;
		this.k = 2;
	}
	

	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 2400);
	}

	public void deactivate() {
		timer.cancel();
		System.out.println("deaktivacija");
	}
	
	@Override
	public void run() {
			//chat.addMessage("primer", "k");
			try {
				chat.izpisUporabnikov();
				if (chat.prijavljen) {
					chat.izpisSporocil();
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		this.k++;
	}
}

