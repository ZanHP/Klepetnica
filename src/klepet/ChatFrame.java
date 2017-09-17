package klepet;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

public class ChatFrame extends JFrame implements ActionListener, KeyListener, WindowListener {
	
	private JTextArea output; //sporocila
	private JTextField input; //vnos sporocila
	private JTextField ime; //vnos vzdevka
	private JPanel klepetalci;
	
	private JButton gumb_prijavi;
	private JButton gumb_odjavi;
	
	public String klepetalec = System.getProperty("user.name");

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		GridBagConstraints panelConstraint = new GridBagConstraints();
		panelConstraint.weightx = 1;
		panelConstraint.weighty = 0;
		panelConstraint.gridx = 0;
		panelConstraint.gridy = 0;
		panelConstraint.fill = GridBagConstraints.HORIZONTAL;
		
		pane.add(panel, panelConstraint);
		
		this.setTitle("blabla");
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
		JLabel vzdevek = new JLabel("Ime:");
		panel.add(vzdevek);
		ime = new JTextField(20);
		panel.add(ime);
		
		gumb_prijavi = new JButton("Prijavi se!");
		panel.add(gumb_prijavi);
		gumb_prijavi.addActionListener(this);
		
		gumb_odjavi = new JButton("Odjava");
		panel.add(gumb_odjavi);
		gumb_odjavi.addActionListener(this);
		gumb_odjavi.setEnabled(false);		
		
		
		//Prostor za izpis prijavljenih klepetalcev.
		this.klepetalci = new JPanel();
		JScrollPane klepetalciPane = new JScrollPane(klepetalci);
		klepetalciPane.setPreferredSize(new Dimension(100, 300)); 
		//this.klepetalci.setEditable(false);
		GridBagConstraints klepConstraint = new GridBagConstraints();
		klepConstraint.fill = GridBagConstraints.BOTH;
		klepConstraint.gridheight = 2;
		klepConstraint.weightx = 0;
		klepConstraint.weighty = 1;
		klepConstraint.gridx = 1;
		klepConstraint.gridy = 1;
		pane.add(klepetalciPane, klepConstraint);
		
	}
	
	    
	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}
	
	public void izpisUporabnikov () throws ParseException {
		klepetalci.removeAll();
		List<Uporabnik> uporabniki = Naloge.get_users(); 
		for (Uporabnik uporabnik : uporabniki) {
			String ime = uporabnik.getUsername();
			String aktiven = String.valueOf(uporabnik.getLastActive());
			
			JButton gumb_aktivnez = new JButton(ime + " " + aktiven + " min");
			klepetalci.add(gumb_aktivnez);
			gumb_aktivnez.setAlignmentX(Component.CENTER_ALIGNMENT);
			gumb_aktivnez.setBackground(Color.white);
			
		}
		klepetalci.revalidate();
		klepetalci.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == gumb_prijavi) {
			klepetalec = ime.getText();
			gumb_prijavi.setEnabled(false);
			gumb_odjavi.setEnabled(true);
			input.setEditable(true);
			ime.setEditable(false);
			input.requestFocusInWindow();
			Naloge.log_in(klepetalec);
		}
		
		if (e.getSource() == this.gumb_odjavi) {
			klepetalec = ime.getText();
			gumb_prijavi.setEnabled(true);
			gumb_odjavi.setEnabled(false);
			input.setEditable(false);
			ime.setEditable(true);
			ime.requestFocusInWindow();
			Naloge.log_out(klepetalec);
			}
		}
	

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				
				this.addMessage(this.ime.getText(), this.input.getText());
				this.input.setText("");
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

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (ime.hasFocus() == false) {
			Naloge.log_out(klepetalec);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		ime.requestFocusInWindow();
		
	}
}
