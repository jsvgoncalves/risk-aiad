package gui;

import jade.core.AID;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agents.GameAgent;
import logic.Board;

public class StatsGUI extends ObserverGUI {
	
	public static final int PANEL_WIDTH = 200;
	public static final int PANEL_HEIGHT = 200;
	private ArrayList<String> colors = new ArrayList<String>();
	// Maps the players to a color.
	private HashMap<String, String> playerColors = new HashMap<String, String>();
	// The labels from the players.
	private ArrayList<PlayersLabel> playerLabels = new ArrayList<PlayersLabel>();
	ArrayList<AID> players;
	// The current gameAgent being observed.
	private GameAgent gameAgent;
	
	public StatsGUI(GameAgent gameAgent) {
		this.gameAgent = gameAgent;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		gameAgent.addListener(this);
	}
	
	public void notifyGameStarted() {
		initPlayerColors();
		for (JLabel label : playerLabels) {
			add(label);
		}
	}
	
	
	private void initPlayerColors() {
		colors.add("blue");
		colors.add("green");
		colors.add("orange");
		colors.add("purple");
		colors.add("red");
		colors.add("yellow");
		players = gameAgent.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			String playerText = "<html><font color='" + colors.get(i) + "'>" + players.get(i).getLocalName() + "</font></html>";
			PlayersLabel playerLabel = new PlayersLabel(playerText);
			playerLabel.setBackground(colors.get(i));
			playerLabels.add(playerLabel);
			playerColors.put(players.get(i).getLocalName(), colors.get(i));
			
		}
	}

	private void updatePlayers(Board b) {
		players = gameAgent.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			String playerName = players.get(i).getLocalName();
			String playerText = "<html><font color='" + colors.get(i) + "'>" + playerName + ": "
								+ b.getPlayerTotalSoldiers(playerName)+ " (" +
								+ b.getPlayerTerritories(playerName).size()  + ")</font></html>";
			playerLabels.get(i).revalidate();
			playerLabels.get(i).repaint();
			playerLabels.get(i).setText(playerText);
		}
	}

	@Override
	public void notifyTurnEnded() {
		Board b = gameAgent.getBoard();
		updatePlayers(b);
	}

}

