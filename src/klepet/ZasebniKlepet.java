package klepet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ZasebniKlepet extends JFrame implements ActionListener, KeyListener, WindowListener {
	
	private JTextArea output; //sporocila
	private JTextField input; //vnos sporocila
	private String uporabnik = "";

	
	public ZasebniKlepet(String uporabnik) {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle(uporabnik);
		this.setMinimumSize(new Dimension(300,200));

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		GridBagConstraints panelConstraint = new GridBagConstraints();
		panelConstraint.weightx = 1;
		panelConstraint.weighty = 0;
		panelConstraint.gridx = 0;
		panelConstraint.gridy = 0;
		panelConstraint.fill = GridBagConstraints.HORIZONTAL;
		
		pane.add(panel, panelConstraint);
		
		
		this.input = new JTextField(30);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		
		this.output = new JTextArea(20, 30);
		this.output.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(output);
		        
		GridBagConstraints scrollConstraint = new GridBagConstraints();
		scrollConstraint.weightx = 1;
		scrollConstraint.weighty = 1;
		scrollConstraint.fill = GridBagConstraints.BOTH;
		scrollConstraint.gridx = 0;
		scrollConstraint.gridy = 1;
		pane.add(scroll, scrollConstraint);
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				input.requestFocusInWindow();
			}
		});

		this.uporabnik = uporabnik;
	}
	

	private void addMessage(String posiljatelj, String sporocilo) {
		String chat = this.output.getText();
		this.output.setText(chat + posiljatelj + ": " + sporocilo + "  , ob " + Naloge.trenutniCas() + "\n");
	}
	
	public void izpisSporocil() {
		List<Sporocilo> sporocila = ChatFrame.sporocila; 
				//Naloge.receive(ChatFrame.jaz_klepetalec); 
		System.out.println("izpis vseh za " + ChatFrame.jaz_klepetalec + ": " + sporocila);
		if (sporocila.isEmpty()) { 
			System.out.println("izpis zasebnih, prazen seznam");
		} else {
			System.out.println("izpis zasebnih");
			for (Sporocilo sporocilo : sporocila) {
				
				String posiljatelj = sporocilo.getSender();
				System.out.println("posiljatelj " + posiljatelj);
				if (posiljatelj.equals(uporabnik)) {
					System.out.println(posiljatelj);
					if (sporocilo.getGlobal().equals(false)) {
					String besedilo = sporocilo.getText();
					this.addMessage(posiljatelj, besedilo);
					}
				}
			}
			}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				if (this.input.getText().equals("") == false) {
					Naloge.send(false, uporabnik, ChatFrame.jaz_klepetalec, this.input.getText());
					this.addMessage(ChatFrame.jaz_klepetalec, this.input.getText());
					this.input.setText("");
					}
				}
		}
		
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
}
