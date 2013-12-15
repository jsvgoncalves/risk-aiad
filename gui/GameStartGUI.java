package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import cli.Launcher;


public class GameStartGUI extends JPanel
						  implements ActionListener {

	private static final long serialVersionUID = -269374791210838235L;
	private JButton b1;
	ArrayList<JComboBox> agentCombos = new ArrayList<JComboBox>();
	String[] agentTypes = { "Random", "Agressive", "Reactive", "Human", "Deliberative" };
	private BufferedImage board_img;
	private TextField filePrefix;
	private String appType;
	
	public GameStartGUI(int numOfAgents, String appType) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.appType = appType;
		try {
		    board_img = ImageIO.read(BoardGUI.class.getResource("res/config.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < numOfAgents; i++) {
			addComboBox(i);
		}
		addFilePrefixTextField();
		add(Box.createVerticalStrut(15));
		addStartButton();
	}

	private void addFilePrefixTextField() {
		JLabel label = new JLabel("CSV files prefix");
		label.setForeground(Color.white);
		add(label);
		filePrefix = new TextField("risk");
		filePrefix.setSize(new Dimension(400, 30));
		filePrefix.setMinimumSize(new Dimension(400, 30));
		filePrefix.setMaximumSize(new Dimension(400, 30));
		add(filePrefix);
	}

	private void addComboBox(int i) {
		setBorder(new EmptyBorder(10, 50, 10, 50) ); // TLBR
		agentCombos.add(new JComboBox(agentTypes));
		agentCombos.get(i).setOpaque(false);
		agentCombos.get(i).setSize(new Dimension(400, 35));
		agentCombos.get(i).setMaximumSize(new Dimension(400, 35));
		agentCombos.get(i).setPreferredSize(new Dimension(400, 35));
		agentCombos.get(i).setSelectedIndex(0);
		agentCombos.get(i).setActionCommand("combo");
		agentCombos.get(i).addActionListener(this);
		add(agentCombos.get(i));
		add(Box.createVerticalStrut(15));
	}

	private void addStartButton() {
		b1 = new JButton("Start game");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
		b1.setMnemonic(KeyEvent.VK_D);
		b1.setActionCommand("start");
		
		b1.addActionListener(this);
		add(b1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("start".equals(e.getActionCommand())) {
			ArrayList<String> agentTypes = compactAgentTypes();
			if(appType.equals("server")) {
				Launcher.startServer(agentTypes, filePrefix.getText());				
			} else {
				Launcher.startClient(agentTypes, filePrefix.getText());
			}
		} else if ("combo".equals(e.getActionCommand())) {
			JComboBox cb = (JComboBox)e.getSource();

		}
	}

	private ArrayList<String> compactAgentTypes() {
		ArrayList<String> result = new ArrayList<String>();
		for (JComboBox combo : agentCombos) {
			result.add((String) combo.getSelectedItem());
		}
		return result;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(board_img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	

}
