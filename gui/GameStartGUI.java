package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cli.Launcher;


public class GameStartGUI extends JPanel
						  implements ActionListener {

	private static final long serialVersionUID = -269374791210838235L;
	private JButton b1;
	ArrayList<JComboBox> agentCombos = new ArrayList<JComboBox>();
	String[] agentTypes = { "Random", "Agressive", "Reactive", "Human" };
	
	public GameStartGUI() {
		for (int i = 0; i < 5; i++) {
			addComboBox(i);
		}
		addStartButton();
	}

	private void addComboBox(int i) {
		agentCombos.add(new JComboBox(agentTypes));
		agentCombos.get(i).setSelectedIndex(0);
		agentCombos.get(i).setActionCommand("combo");
		agentCombos.get(i).addActionListener(this);
		add(agentCombos.get(i));
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
			System.err.println("starting");
			ArrayList<String> agentTypes = compactAgentTypes();
			Launcher.startGame(agentTypes);
		} else if ("combo".equals(e.getActionCommand())) {
			JComboBox cb = (JComboBox)e.getSource();
	        System.err.println(cb.getSelectedItem());

		}
	}

	private ArrayList<String> compactAgentTypes() {
		ArrayList<String> result = new ArrayList<String>();
		for (JComboBox combo : agentCombos) {
			result.add((String) combo.getSelectedItem());
		}
		return result;
	}
	
	

}
