package klepet;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private Timer timer;
	
	public Robot(ChatFrame chat) {
		this.chat = chat;
	}
	
	public Robot(ZasebniKlepet zasebni_chat) {
	}
	

	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 5000);
		System.out.println("aktivacija");
	}

	public void deactivate() {
		timer.cancel();
		System.out.println("deaktivacija");
	}
	
	
	@Override
	public void run() {
		try {
			chat.izpisUporabnikov();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (chat.prijavljen) {
			//obnovimo vsa prejeta sporocila
			ChatFrame.sporocila = Naloge.receive(ChatFrame.jaz_klepetalec);
			try {
				chat.izpisSporocil();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}	
			if (chat.zasebni_klepeti.isEmpty() == false) {
				//gremo po vseh uporabnikih, s katerimi imamo odprte zasebne pogovore
				for (String uporabnik : chat.zasebni_klepeti.keySet()) {
					chat.zasebni_klepeti.get(uporabnik).izpisSporocil();
					}
				};
	}

}
					




