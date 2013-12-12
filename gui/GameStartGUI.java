package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

import cli.Launcher;


public class GameStartGUI extends JPanel
						  implements ActionListener {

	private static final long serialVersionUID = -269374791210838235L;
	private JButton b1;
	
	
	public GameStartGUI() {
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
//			this.setA
		}
		Launcher.startGame();
	}
	
	

}
