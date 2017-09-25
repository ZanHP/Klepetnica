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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ZasebniKlepet extends JFrame implements ActionListener, KeyListener {
	
	private JTextPane output; //sporocila
	private JTextField input; //vnos sporocila
	private String uporabnik = "";

	
	public ZasebniKlepet(String uporabnik) {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle(uporabnik);
		this.setMinimumSize(new Dimension(300,200));
		
		this.addWindowListener(new WindowAdapter() 
		{
		@Override
		public void windowClosing(WindowEvent e) {
			ChatFrame.zasebni_klepeti.remove(uporabnik);
		}
		
		public void windowActivated(WindowEvent e) {
			input.requestFocus();
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
		
		
		this.input = new JTextField(30);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.weightx = 1;
		inputConstraint.weighty = 0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		
		this.output = new JTextPane();
		this.output.setText("<html>");
		this.output.setContentType("text/html");
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
	

	private void addMessage(String posiljatelj, String sporocilo, String cas) {
		HTMLDocument doc = (HTMLDocument) output.getDocument(); 
		HTMLEditorKit editorKit = (HTMLEditorKit) output.getEditorKit();
		
		String novo = "<font face='courier new'><b>" + posiljatelj + "</b>" + ": " + 
						sporocilo + "&nbsp &nbsp </font>" + "<font face='courier new' size='3'>ob " + cas + "</font>";
		if (posiljatelj.equals(ChatFrame.jaz_klepetalec)) {
			novo = "&nbsp &nbsp &nbsp &nbsp" + novo;
		}
		try {
        	editorKit.insertHTML(doc, doc.getLength(), novo, 0, 0, null);

        } 
        catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void izpisSporocil() {
		List<Sporocilo> sporocila = ChatFrame.sporocila; 
		if (sporocila.isEmpty()) { 			
		} else {
			for (Sporocilo sporocilo : sporocila) {
				String posiljatelj = sporocilo.getSender();
				if (posiljatelj.equals(uporabnik)) {
					System.out.println(posiljatelj);
					if (sporocilo.getGlobal().equals(false)) {
						String besedilo = sporocilo.getText();
						Date cas = sporocilo.getSent_at();
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");
						String cas_string = df.format(cas);
						this.addMessage(posiljatelj, besedilo, cas_string);
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
					this.addMessage(ChatFrame.jaz_klepetalec, this.input.getText(), Naloge.trenutniCas());
					this.input.setText("");
					}
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}