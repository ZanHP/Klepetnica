package klepet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.BadLocationException;

public class Robot extends TimerTask {
	private ChatFrame chat;
	private Timer timer;
	private int osvezitev = 500; // hitrost osveževanja
	private int minute_od_zacetka = 0;
	private int milisekunde_od_zacetka = 0;
	private String cas = "";
	
	public Robot(ChatFrame chat) {
		this.chat = chat;
	}
	
	public Robot(ZasebniKlepet zasebni_chat) {
	}
	

	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, osvezitev);
		System.out.println("aktivacija");
	}

	public void deactivate() {
		timer.cancel();
		System.out.println("deaktivacija");
	}
	
	
	@Override
	public void run() {
		milisekunde_od_zacetka = milisekunde_od_zacetka + osvezitev;
		minute_od_zacetka = milisekunde_od_zacetka / 6000;
		cas = "Klepetaš že " + minute_od_zacetka + " minut";
		chat.spremeniCas(minute_od_zacetka);
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
			} catch (ParseException | BadLocationException | IOException e) {
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
					




