package klepet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output; //sporocila
	private JTextField input; //vnos sporocila
	private JTextField vzdevek_vnos; //vnos vzdevka
	private JPanel uporabniki_panel;
	
	private JButton gumb_prijavi;
	private JButton gumb_odjavi;
	
	public static String jaz_klepetalec = System.getProperty("user.name");
	public Boolean prijavljen = false;
	
	public static Map<String, ZasebniKlepet> zasebni_klepeti;
	static List<Sporocilo> sporocila = Collections.emptyList();

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle("blabla");
		this.setMinimumSize(new Dimension(700,500));
		
		this.addWindowListener(new WindowAdapter() 
		{
		@Override
		public void windowClosing(WindowEvent e) {
			if (prijavljen.equals(true)) {
				ChitChat.robot.deactivate();

				Naloge.log_out(jaz_klepetalec);
				for (ZasebniKlepet zasebni : zasebni_klepeti.values()) {
					zasebni.dispose();
				}
			}
		}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		GridBagConstraints panelConstraint = new GridBagConstraints();
		panelConstraint.weightx = 1;
		panelConstraint.weighty = 0;
		panelConstraint.gridx = 0;
		panelConstraint.gridy = 0;
		panelConstraint.fill = GridBagConstraints.HORIZONTAL;
		
		pane.add(panel, panelConstraint);
		
		
		this.input = new JTextField(40);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		this.input.setEditable(false);
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		//Definiciji pisav.
		Font oblika_label = new Font("Arial", Font.BOLD, 14);
		Font oblika_poslano = new Font("Courier", Font.ITALIC, 12);
		
		this.output = new JTextArea(20, 40);
		this.output.setFont(oblika_poslano);
		this.output.setEditable(false);
		//this.output.set
		
		JScrollPane scroll = new JScrollPane(output);
		        
		GridBagConstraints scrollConstraint = new GridBagConstraints();
		scrollConstraint.weightx = 1;
		scrollConstraint.weighty = 1;
		scrollConstraint.fill = GridBagConstraints.BOTH;
		scrollConstraint.gridx = 0;
		scrollConstraint.gridy = 1;
		pane.add(scroll, scrollConstraint);		
		
		//Prostor za vzdevek.
		JLabel vzdevek_napis = new JLabel("Ime:");
		vzdevek_napis.setFont(oblika_label);
		panel.add(vzdevek_napis);
		this.vzdevek_vnos = new JTextField(20);
		panel.add(vzdevek_vnos);
		vzdevek_vnos.addKeyListener(this);
		
		gumb_prijavi = new JButton("Prijavi se!");
		panel.add(gumb_prijavi);
		gumb_prijavi.addActionListener(this);
		
		gumb_odjavi = new JButton("Odjava");
		panel.add(gumb_odjavi);
		gumb_odjavi.addActionListener(this);
		gumb_odjavi.setEnabled(false);		
		
		//Prostor za izpis prijavljenih klepetalcev.
		this.uporabniki_panel = new JPanel();
		JLabel aktivni = new JLabel("Trenutno prijavljeni:");
		aktivni.setFont(oblika_label);
		pane.add(aktivni);
		JScrollPane uporabniki_scroll = new JScrollPane(uporabniki_panel);
		uporabniki_scroll.setPreferredSize(new Dimension(160, 300)); 
		GridBagConstraints uporscrollConstraint = new GridBagConstraints();
		uporscrollConstraint.fill = GridBagConstraints.BOTH;
		uporscrollConstraint.gridheight = 2;
		uporscrollConstraint.weightx = 0;
		uporscrollConstraint.weighty = 1;
		uporscrollConstraint.gridx = 1;
		uporscrollConstraint.gridy = 1;
		uporabniki_panel.setLayout((LayoutManager) new BoxLayout(uporabniki_panel, BoxLayout.Y_AXIS));

		pane.add(uporabniki_scroll, uporscrollConstraint);
		
		//seznam robotov zasebnih pogovorov
		zasebni_klepeti = new HashMap<String, ZasebniKlepet>(); 
		
	}
	
	    
	public void addMessage(String posiljatelj, String sporocilo, String cas) {
		String novo = " " + posiljatelj + ": " + sporocilo + "  , ob " + cas + "\n";
		this.output.append(novo);
	}
	
	public void izpisUporabnikov () throws ParseException {
		uporabniki_panel.removeAll();
		List<Uporabnik> uporabniki = Naloge.get_users(); 
		
		for (Uporabnik uporabnik : uporabniki) {
			String ime = uporabnik.getUsername();
			
			if ((ime.equals(this.vzdevek_vnos.getText())) == false) { //svojega imena ne vidimo med prijavljenimi
				String aktiven = String.valueOf(uporabnik.getLastActive());
				
				JButton gumb_aktivnez = new JButton(ime + " " + aktiven + " min");
				gumb_aktivnez.setFont(gumb_aktivnez.getFont().deriveFont(Font.BOLD, 14f));
				gumb_aktivnez.setMinimumSize(new Dimension(160, 40) );
				
				//glede na aktivnost dolocimo barvo
				if (Integer.valueOf(aktiven) < 2) {
					gumb_aktivnez.setBackground(Color.green);
				} else {
					if (Integer.valueOf(aktiven) < 4) {
						gumb_aktivnez.setBackground(Color.yellow);
					} else {
						if (Integer.valueOf(aktiven) < 8) {
							gumb_aktivnez.setBackground(Color.orange);
						} else {
							gumb_aktivnez.setBackground(Color.red);
								
				}}}
				uporabniki_panel.add(gumb_aktivnez);
						
				gumb_aktivnez.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						zasebni_pogovor(uporabnik.getUsername());
					}
				});
				
				
			}
		}
		uporabniki_panel.revalidate();
		uporabniki_panel.repaint();
	}
	
	
	private void zasebni_pogovor(String uporabnik) {
		if (prijavljen) {
			if (zasebni_klepeti.keySet().contains(uporabnik)) {
			} else {
				ZasebniKlepet zasebni = new ZasebniKlepet(uporabnik);
				zasebni.pack();
				zasebni.setVisible(true);
				zasebni_klepeti.put(uporabnik, zasebni);
				System.out.println(zasebni_klepeti);
			}
		}
	}
	
	
	
	public void izpisSporocil() throws ParseException {
		
		System.out.println("JAVNI izpis vseh za " + ChatFrame.jaz_klepetalec + ": " + sporocila);
		if (sporocila.isEmpty()) { 
			} else {		
			for (Sporocilo sporocilo : sporocila) {
				String besedilo = sporocilo.getText();
				String posiljatelj = sporocilo.getSender();
				Date cas = sporocilo.getSent_at();
				
				//Odloèimo se za preprosto obliko zapisovanja èasa,
				//ta klepetalnica namreè ne shranjuje sporoèil in torej ni
				//namenjena klepetanju dan za dnem, kjer bi morda potrebovali
				//podatek od dnevu, ko je bilo neko sporoèilo poslano.
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				String cas_string = df.format(cas);
				
				if (sporocilo.getGlobal()) {
					//javno sporoèilo dodamo v output
					this.addMessage(posiljatelj, besedilo, cas_string);
				} else {
					if (zasebni_klepeti.keySet().contains(posiljatelj)) {
						//ta primer uredi zasebni klepet
					} else {
						//sicer odpremo novega
						this.zasebni_pogovor(posiljatelj);
					}
					
				}
				}
			}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == gumb_prijavi) {
			jaz_klepetalec = vzdevek_vnos.getText();
			vpis(jaz_klepetalec);
		}
		
		if (e.getSource() == gumb_odjavi) {
			izpis(jaz_klepetalec);
			}
		}
	
	private void vpis(String klepetalec) {
		if (this.vzdevek_vnos.equals("") == false) {
			gumb_prijavi.setEnabled(false);
			gumb_odjavi.setEnabled(true);
			input.setEditable(true);
			vzdevek_vnos.setEditable(false);
			input.requestFocusInWindow();
			prijavljen = true;
			Naloge.log_in(klepetalec);
			}
	}
	
	@SuppressWarnings("static-access")
	private void izpis(String klepetalec) {
		gumb_prijavi.setEnabled(true);
		gumb_odjavi.setEnabled(false);
		input.setEditable(false);
		vzdevek_vnos.setEditable(true);
		vzdevek_vnos.requestFocusInWindow();
		prijavljen = false;
		this.zasebni_klepeti = Collections.emptyMap();
		Naloge.log_out(klepetalec);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.vzdevek_vnos) {
			if (e.getKeyChar() == '\n') {
				jaz_klepetalec = this.vzdevek_vnos.getText(); 
				vpis(jaz_klepetalec);
			}
		}
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				if (this.input.getText().equals("") == false) {
					Naloge.send(true, "", jaz_klepetalec, this.input.getText());
					this.addMessage(this.vzdevek_vnos.getText(), this.input.getText(), Naloge.trenutniCas());
					this.input.setText("");
					}
				}
		}		
	}

	

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	
}


