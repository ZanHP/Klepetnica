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
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Zasebni_klepet extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea output; //sporocila
	private JTextField input; //vnos sporocila

	
	public Zasebni_klepet(String uporabnik) {
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
	}
	
		
	
//		super();
//		Container pane = this.getContentPane();
//		pane.setLayout(new GridLayout(2,0));
//		this.setTitle(uporabnik);
//		this.setMinimumSize(new Dimension(300,200));
//		
////		JPanel panel = new JPanel();
////		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
////		pane.add(panel);
//		
//		JPanel panel = new JPanel();
//		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		
//		GridBagConstraints panelConstraint = new GridBagConstraints();
//		panelConstraint.weightx = 1;
//		panelConstraint.weighty = 0;
//		panelConstraint.gridx = 0;
//		panelConstraint.gridy = 0;
//		panelConstraint.fill = GridBagConstraints.HORIZONTAL;
//		
//		pane.add(panel, panelConstraint);
//		
//		this.input = new JTextField(20);
//		GridBagConstraints inputConstraint = new GridBagConstraints();
//		inputConstraint.weightx = 1;
//		inputConstraint.weighty = 0;
//		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
//		inputConstraint.gridx = 0;
//		inputConstraint.gridy = 2;
//		pane.add(input, inputConstraint);
//		input.addKeyListener(this);
//		
//		this.output = new JTextArea(20,20);
//		this.output.setEditable(false);
//		
//		JScrollPane scroll = new JScrollPane(output);
//        
//		GridBagConstraints scrollConstraint = new GridBagConstraints();
//		scrollConstraint.weightx = 1;
//		scrollConstraint.weighty = 1;
//		scrollConstraint.fill = GridBagConstraints.BOTH;
//		scrollConstraint.gridx = 0;
//		scrollConstraint.gridy = 1;
//		pane.add(scroll, scrollConstraint);
//	}
	
	public static void zasebni_pogovor(String uporabnik) {
		 EventQueue.invokeLater(new Runnable()
	        {
	            @Override
	            public void run()
	            {
	                JFrame frame = new JFrame(uporabnik);
	                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                
	                JPanel panel = new JPanel();
	                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	                //panel.setOpaque(true);
	                JTextArea textArea = new JTextArea(15, 20);
	                textArea.setWrapStyleWord(true);
	                textArea.setEditable(false);
	                textArea.setFont(Font.getFont(Font.SANS_SERIF));
	                JScrollPane scroller = new JScrollPane(textArea);

	                JPanel inputpanel = new JPanel();
	                inputpanel.setLayout(new FlowLayout());
	                JTextField input = new JTextField(20);
	                //JButton button = new JButton("Enter");
	                panel.add(scroller);
	                inputpanel.add(input);
	                //inputpanel.add(button);
	                panel.add(inputpanel);
	                frame.getContentPane().add(BorderLayout.CENTER, panel);
	                frame.pack();
	                frame.setLocationByPlatform(true);
	                frame.setVisible(true);
	                frame.setResizable(false);
	                input.requestFocus();
	            }
	        });
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
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
