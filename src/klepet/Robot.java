package klepet;

import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private ZasebniKlepet zasebni_chat;
	private Timer timer;
	
	public Robot(ChatFrame chat) {
		this.chat = chat;
	}
	
	public Robot(ZasebniKlepet zasebni_chat) {
		this.zasebni_chat = zasebni_chat;
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
		//obnovimo vsa prejeta sporocila
		ChatFrame.sporocila = Naloge.receive(ChatFrame.jaz_klepetalec); 
		try {
			chat.izpisUporabnikov();
			if (chat.prijavljen) {
				chat.izpisSporocil();
				//System.out.println("izpis javnih sporocil");
			}	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (chat.zasebni_klepeti.isEmpty() == false) {
			//gremo po vseh uporabnikih, s katerimi imamo odprte zasebne pogovore
			for (String uporabnik : chat.zasebni_klepeti.keySet()) {
				//System.out.println(uporabnik + " poslal zasebna meni, robot");
				chat.zasebni_klepeti.get(uporabnik).izpisSporocil();
				}
			};
		}
			
}



