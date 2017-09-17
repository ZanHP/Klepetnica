package klepet;

import java.awt.Adjustable;
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
	
	public String klepetalec = System.getProperty("user.name");

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle("blabla");
		this.setMinimumSize(new Dimension(700,500));
		
//		this.addWindowListener(new FrameListener());
//		this.addWindowListener(new WindowAdapter()); {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				System.out.println("zapiranje okna");
//				if (vzdevek_vnos.isFocusOwner() == false) {
//					Naloge.log_out(klepetalec);
//				}
//				ChitChat.robot.deactivate();		
//			}
//		}

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		GridBagConstraints panelConstraint = new GridBagConstraints();
		panelConstraint.weightx = 1;
		panelConstraint.weighty = 0;
		panelConstraint.gridx = 0;
		panelConstraint.gridy = 0;
		panelConstraint.fill = GridBagConstraints.HORIZONTAL;
		
		pane.add(panel, panelConstraint);
		
		
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
		
		
		//Prostor za vzdevek.
		JLabel vzdevek_napis = new JLabel("Ime:");
		panel.add(vzdevek_napis);
		setVzdevek_vnos(new JTextField(20));
		panel.add(getVzdevek_vnos());
		
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
		
		
		
	}
	
	    
	public void addMessage(String posiljatelj, String sporocilo) {
		String chat = this.output.getText();
		this.output.setText(chat + posiljatelj + ": " + sporocilo + "\n");
	}
	
	public void izpisUporabnikov () throws ParseException {
		uporabniki_panel.removeAll();
		List<Uporabnik> uporabniki = Naloge.get_users(); 
		
		for (Uporabnik uporabnik : uporabniki) {
			String ime = uporabnik.getUsername();
			
			if ((ime.equals(this.getVzdevek_vnos().getText())) == false) { //svojega imena ne vidimo med prijavljenimi
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
			}
		}
		uporabniki_panel.revalidate();
		uporabniki_panel.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == gumb_prijavi) {
			klepetalec = getVzdevek_vnos().getText();
			gumb_prijavi.setEnabled(false);
			gumb_odjavi.setEnabled(true);
			input.setEditable(true);
			getVzdevek_vnos().setEditable(false);
			input.requestFocusInWindow();
			Naloge.log_in(klepetalec);
		}
		
		if (e.getSource() == this.gumb_odjavi) {
			gumb_prijavi.setEnabled(true);
			gumb_odjavi.setEnabled(false);
			input.setEditable(false);
			getVzdevek_vnos().setEditable(true);
			getVzdevek_vnos().requestFocusInWindow();
			Naloge.log_out(klepetalec);
			}
		}
	

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				Naloge.send(klepetalec, this.input.getText());
				//this.addMessage(this.getVzdevek_vnos().getText(), this.input.getText());
				this.input.setText("");
			}
		}		
	}

	public void windowClosed(WindowEvent e) {
		if (getVzdevek_vnos().isFocusOwner() == false) {
			Naloge.log_out(klepetalec);
		}
		ChitChat.robot.deactivate();

	}


	public void windowClosing(WindowEvent e) {
		System.out.println("zapiranje okna");
		if (vzdevek_vnos.isFocusOwner() == false) {
			Naloge.log_out(klepetalec);
		}
		ChitChat.robot.deactivate();

	}

	public void windowOpened(WindowEvent e) {
		System.out.println("odpiranje okna");
		getVzdevek_vnos().requestFocusInWindow();
		
	}


	public JTextField getVzdevek_vnos() {
		return vzdevek_vnos;
	}


	public void setVzdevek_vnos(JTextField vzdevek_vnos) {
		this.vzdevek_vnos = vzdevek_vnos;
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
		if (getVzdevek_vnos().isFocusOwner() == false) {
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


