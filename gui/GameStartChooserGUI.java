package gui;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import cli.Launcher;

public class GameStartChooserGUI extends JPanel
								 implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3292536930741347247L;

	JSpinner spinner;
	JTextField ip, port;
	
	public GameStartChooserGUI() {
		setBorder(new EmptyBorder(10, 50, 10, 50) ); // TLBR
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		SpinnerNumberModel model = new SpinnerNumberModel(5,0,5,1);
		spinner = new JSpinner(model);
		JLabel label = new JLabel("Select number of agents");
		spinner.setSize(new Dimension(400, 35));
		spinner.setMaximumSize(new Dimension(400, 35));
		spinner.setPreferredSize(new Dimension(400, 35));
		add(label);
		add(spinner);
		add(Box.createVerticalStrut(15));
		
		ip = new JTextField("127.0.0.1");
		ip.setSize(new Dimension(400, 35));
		ip.setMaximumSize(new Dimension(400, 35));
		ip.setPreferredSize(new Dimension(400, 35));
		add(ip);
		
		port = new JTextField("8000");
		port.setSize(new Dimension(400, 35));
		port.setMaximumSize(new Dimension(400, 35));
		port.setPreferredSize(new Dimension(400, 35));
		add(port);
		
		add(Box.createVerticalStrut(15));
		JButton client = new JButton("Risk client only");
		JButton server = new JButton("Risk server and client");

		client.setActionCommand("client");
		server.setActionCommand("server");
		add(client);
		add(server);
		
		client.addActionListener(this);
		server.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int numOfAgents = (int) spinner.getValue();
		if ("client".equals(e.getActionCommand())) {
			Launcher.setupClient(numOfAgents, ip.getText(), port.getText());
		} else if ("server".equals(e.getActionCommand())) {
			Launcher.setupServer(numOfAgents);
		}
	}
}
