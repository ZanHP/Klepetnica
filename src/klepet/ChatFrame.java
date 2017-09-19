package klepet;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output; //sporocila
	private JTextField input; //vnos sporocila
	private JTextField vzdevek_vnos; //vnos vzdevka
	private JPanel uporabniki_panel;
	
	private JButton gumb_prijavi;
	private JButton gumb_odjavi;
	
	public static String klepetalec = System.getProperty("user.name");
	public Boolean prijavljen = false;
	
	public List<String> zasebni_klepeti;

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle("blabla");
		this.setMinimumSize(new Dimension(700,500));
		


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
		
		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		
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
		panel.add(vzdevek_napis);
		this.vzdevek_vnos = new JTextField(20);
		panel.add(vzdevek_vnos);
		
		gumb_prijavi = new JButton("Prijavi se!");
		panel.add(gumb_prijavi);
		gumb_prijavi.addActionListener(this);
		
		gumb_odjavi = new JButton("Odjava");
		panel.add(gumb_odjavi);
		gumb_odjavi.addActionListener(this);
		gumb_odjavi.setEnabled(false);		
		
		
		//Prostor za izpis prijavljenih klepetalcev.
		this.uporabniki_panel = new JPanel();
		JScrollPane uporabniki_scroll = new JScrollPane(uporabniki_panel);
		uporabniki_scroll.setPreferredSize(new Dimension(160, 300)); 
		//this.klepetalci.setEditable(false);
		GridBagConstraints uporscrollConstraint = new GridBagConstraints();
		uporscrollConstraint.fill = GridBagConstraints.BOTH;
		uporscrollConstraint.gridheight = 2;
		uporscrollConstraint.weightx = 0;
		uporscrollConstraint.weighty = 1;
		uporscrollConstraint.gridx = 1;
		uporscrollConstraint.gridy = 1;
		uporabniki_panel.setLayout((LayoutManager) new BoxLayout(uporabniki_panel, BoxLayout.Y_AXIS));

		pane.add(uporabniki_scroll, uporscrollConstraint);
		
		zasebni_klepeti = new ArrayList<String>(); //seznam odprtih zasebnih pogovorov
		
	}
	
	    
	public void addMessage(String posiljatelj, String sporocilo) {
		String chat = this.output.getText();
		this.output.setText(chat + posiljatelj + ": " + sporocilo + "  , ob " + Naloge.trenutniCas() + "\n");
	}
	
	public void izpisUporabnikov () throws ParseException {
		uporabniki_panel.removeAll();
		List<Uporabnik> uporabniki = Naloge.get_users(); 
		
		for (Uporabnik uporabnik : uporabniki) {
			String ime = uporabnik.getUsername();
			
			if ((ime.equals(this.vzdevek_vnos.getText())) == false) { //svojega imena ne vidimo med prijavljenimi
				String aktiven = String.valueOf(uporabnik.getLastActive());
				
				JButton gumb_aktivnez = new JButton(ime + " " + aktiven + " min");
				gumb_aktivnez.setBorder(new LineBorder(Color.RED));
				gumb_aktivnez.setFont(gumb_aktivnez.getFont().deriveFont(Font.BOLD, 14f));
				gumb_aktivnez.setSize(new Dimension(140, 40) );
				gumb_aktivnez.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				gumb_aktivnez.setBorder(BorderFactory.createEtchedBorder(1));
				gumb_aktivnez.setAlignmentX(Component.CENTER_ALIGNMENT);
				
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
		Zasebni_klepet zasebni = new Zasebni_klepet(uporabnik);
		Robot zasebni_robot = new Robot(zasebni);
		zasebni.pack();
		zasebni.setVisible(true);
		zasebni_robot.activate();
		zasebni_klepeti.add(uporabnik);
		System.out.println(zasebni_klepeti);
	}
	
	public void izpisSporocil() {
		List<Sporocilo> sporocila = Naloge.receive(klepetalec); 
		if (sporocila.isEmpty()) { 
			} else {		
			for (Sporocilo sporocilo : sporocila) {
				String besedilo = sporocilo.getText();
				String posiljatelj = sporocilo.getSender();
				this.addMessage(posiljatelj, besedilo);
			}
			}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == gumb_prijavi) {
			klepetalec = vzdevek_vnos.getText();
			vpis(klepetalec);
		}
		
		if (e.getSource() == this.gumb_odjavi) {
			izpis(klepetalec);
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
	
	private void izpis(String klepetalec) {
		gumb_prijavi.setEnabled(true);
		gumb_odjavi.setEnabled(false);
		input.setEditable(false);
		vzdevek_vnos.setEditable(true);
		vzdevek_vnos.requestFocusInWindow();
		prijavljen = false;
		Naloge.log_out(klepetalec);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		//TODO
		if (e.getSource() == this.vzdevek_vnos) {
			System.out.println("pritisk_vpis");
			if (e.getKeyChar() == '\n') {
				System.out.println("enter_vpis");
				vpis(klepetalec);
			}
		}
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				if (this.input.getText().equals("") == false) {
					Naloge.send(true, "", klepetalec, this.input.getText());
					this.addMessage(this.vzdevek_vnos.getText(), this.input.getText());
					this.input.setText("");
					}
				}
		}		
	}

	public void windowClosed(WindowEvent e) {
		if (prijavljen) {
			Naloge.log_out(klepetalec);
		}
		ChitChat.robot.deactivate();

	}


	public void windowClosing(WindowEvent e) {
		System.out.println("zapiranje okna");
		if (prijavljen) {
			Naloge.log_out(klepetalec);
		}
		ChitChat.robot.deactivate();

	}

	public void windowOpened(WindowEvent e) {
		System.out.println("odpiranje okna");
		vzdevek_vnos.requestFocusInWindow();
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void zapiranje() {
		System.out.println("zapiranje okna");
		if (vzdevek_vnos.isFocusOwner() == false) {
			Naloge.log_out(klepetalec);
		}
		ChitChat.robot.deactivate();		
		}


//class FrameListener extends WindowAdapter {
//   public void windowClosing(WindowEvent e){
//		this.zapiranje();
//  }
//
//private void zapiranje() {
//	zapiranje();
	
}


