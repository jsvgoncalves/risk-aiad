package gui;

import jade.core.AID;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.R;
import agents.GameAgent;
import logic.Board;
import logic.Territory;

public class BoardGUI extends ObserverGUI{
	private static final long serialVersionUID = 8906083247845612415L;
	
	public static final int PANEL_WIDTH = 1004;
	public static final int PANEL_HEIGHT= 659;
	private static final int LABEL_WIDTH = 29;
	private static final int LABEL_HEIGHT = 20;
	private static final Color LABEL_BORDER_COLOR = Color.gray;
	
	// The labels from the territories.
	private HashMap<String, BoardLabel> labels = new HashMap<String, BoardLabel>();
	// Contains all the available color Strings.
	private ArrayList<String> colors = new ArrayList<String>();
	// Maps the players to a color.
	private HashMap<String, String> playerColors = new HashMap<String, String>();
	// Board background image
	private BufferedImage board_img;
	// The current gameAgent being observed.
	private GameAgent gameAgent;
	
	ArrayList<AID> players;
	
	
	public BoardGUI(GameAgent gameAgent) {

		this.gameAgent = gameAgent;
		board_img = null;
		
		try {
		    board_img = ImageIO.read(BoardGUI.class.getResource("res/board.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setLayout(null);
		
		initBoardLabels();
		gameAgent.addListener(this);
		
		
	}

	private void initPlayerColors() {
		
		colors.add("blue");
		colors.add("green");
		colors.add("orange");
		colors.add("purple");
		colors.add("red");
		colors.add("yellow");
		System.err.println("Setting colors.");
		ArrayList<AID> players = gameAgent.getAllAgentNames();
		System.err.println("Players count: " + players.size());
		for (int i = 0; i < players.size(); i++) {
			System.err.println(players.get(i).getLocalName() + " is " + colors.get(i));
			playerColors.put(players.get(i).getLocalName(), colors.get(i));
		}
	}

	private void initBoardLabels() {
		// NORTH AMERICA LABELS
		labels.put(R.NA_NUN_KEY, new BoardLabel("1", 167, 126, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_NUN_KEY));
		labels.put(R.NA_KLO_KEY, new BoardLabel("1", 58, 111, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_KLO_KEY));
		labels.put(R.NA_PAC_KEY, new BoardLabel("1", 107, 213, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_PAC_KEY));
		labels.put(R.NA_GRE_KEY, new BoardLabel("1", 335, 71, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_GRE_KEY));
		labels.put(R.NA_PLA_KEY, new BoardLabel("1", 171, 215, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_PLA_KEY));
		labels.put(R.NA_SUN_KEY, new BoardLabel("1", 222, 257, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_SUN_KEY));
		labels.put(R.NA_LAW_KEY, new BoardLabel("1", 234, 171, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_LAW_KEY));
		labels.put(R.NA_EAS_KEY, new BoardLabel("1", 270, 208, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_EAS_KEY));
		labels.put(R.NA_CAR_KEY, new BoardLabel("1", 289, 332, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_CAR_KEY));
		labels.put(R.NA_MEX_KEY, new BoardLabel("1", 178, 324, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.NA_MEX_KEY));

		// SOUTH AMERICA

		// EUROPE
		labels.put(R.EU_IBE_KEY, new BoardLabel("1", 395, 275, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_IBE_KEY));
		labels.put(R.EU_FR_KEY, new BoardLabel("1", 425, 231, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_FR_KEY));
		labels.put(R.EU_IT_KEY, new BoardLabel("1", 458, 266, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_IT_KEY));
		labels.put(R.EU_CEN_KEY, new BoardLabel("1", 467, 207, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_CEN_KEY));
		labels.put(R.EU_BAL_KEY, new BoardLabel("1", 505, 235, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_BAL_KEY));
		labels.put(R.EU_BRI_KEY, new BoardLabel("1", 411, 159, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_BRI_KEY));
		labels.put(R.EU_ICE_KEY, new BoardLabel("1", 423, 99, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_ICE_KEY));
		labels.put(R.EU_SCA_KEY, new BoardLabel("1", 524, 64, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_SCA_KEY));
		labels.put(R.EU_EAS_KEY, new BoardLabel("1", 543, 197, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_EAS_KEY));
		labels.put(R.EU_RUS_KEY, new BoardLabel("1", 582, 164, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.EU_RUS_KEY));

		// ASIA
		labels.put(R.AS_YAM_KEY, new BoardLabel("1", 661, 128, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_YAM_KEY));
		labels.put(R.AS_STA_KEY, new BoardLabel("1", 633, 234, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_STA_KEY));
		labels.put(R.AS_MEA_KEY, new BoardLabel("1", 547, 263, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_MEA_KEY));
		labels.put(R.AS_ARA_KEY, new BoardLabel("1", 568, 323, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_ARA_KEY));
		labels.put(R.AS_SAK_KEY, new BoardLabel("1", 778, 116, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_SAK_KEY));
		labels.put(R.AS_MON_KEY, new BoardLabel("1", 754, 208, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_MON_KEY));
		labels.put(R.AS_CHI_KEY, new BoardLabel("1", 757, 272, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_CHI_KEY));
		labels.put(R.AS_IND_KEY, new BoardLabel("1", 675, 320, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_IND_KEY));
		labels.put(R.AS_INO_KEY, new BoardLabel("1", 772, 333, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_INO_KEY));
		labels.put(R.AS_MAC_KEY, new BoardLabel("1", 831, 207, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_MAC_KEY));
		labels.put(R.AS_CHU_KEY, new BoardLabel("1", 902, 91, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_CHU_KEY));
		labels.put(R.AS_JAP_KEY, new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AS_JAP_KEY));

		// OCEANIA
		labels.put(R.OC_BOR_KEY, new BoardLabel("1", 769, 403, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.OC_BOR_KEY));
		labels.put(R.OC_EAS_KEY, new BoardLabel("1", 862, 393, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.OC_EAS_KEY));
		labels.put(R.OC_OUT_KEY, new BoardLabel("1", 847, 468, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.OC_OUT_KEY));
		labels.put(R.OC_AUS_KEY, new BoardLabel("1", 761, 476, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.OC_AUS_KEY));
		labels.put(R.OC_NEW_KEY, new BoardLabel("1", 939, 500, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.OC_NEW_KEY));

		// ANTARTICA
		labels.put(R.AN_WIL_KEY, new BoardLabel("1", 830, 620, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AN_WIL_KEY));
		labels.put(R.AN_QUE_KEY, new BoardLabel("1", 606, 606, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AN_QUE_KEY));
		labels.put(R.AN_MAR_KEY, new BoardLabel("1", 347, 617, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AN_MAR_KEY));
		
		// AFRICA
		labels.put(R.AF_MAG_KEY, new BoardLabel("1", 427, 375, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_MAG_KEY));
		labels.put(R.AF_NIL_KEY, new BoardLabel("1", 515, 372, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_NIL_KEY));
		labels.put(R.AF_THE_KEY, new BoardLabel("1", 572, 415, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_THE_KEY));
		labels.put(R.AF_ZAI_KEY, new BoardLabel("1", 508, 450, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_ZAI_KEY));
		labels.put(R.AF_CAP_KEY, new BoardLabel("1", 514, 530, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_CAP_KEY));
		labels.put(R.AF_MAD_KEY, new BoardLabel("1", 604, 504, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.AF_MAD_KEY));

		// SA
		labels.put(R.SA_COL_KEY, new BoardLabel("1", 211, 382, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_COL_KEY));
		labels.put(R.SA_AMA_KEY, new BoardLabel("1", 241, 423, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_AMA_KEY));
		labels.put(R.SA_PER_KEY, new BoardLabel("1", 179, 437, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_PER_KEY));
		labels.put(R.SA_PAR_KEY, new BoardLabel("1", 296, 443, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_PAR_KEY));
		labels.put(R.SA_BOL_KEY, new BoardLabel("1", 226, 456, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_BOL_KEY));
		labels.put(R.SA_ARG_KEY, new BoardLabel("1", 226, 500, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get(R.SA_ARG_KEY));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(board_img, 0, 0, this.getWidth(), this.getHeight(), this);
	
	}
	
	/**
	 * Updates colors and units on BoardLabels.
	 */
	public void updateAllTerritories() {
		Board b = gameAgent.getBoard();

		for(Entry<String, BoardLabel> e: labels.entrySet()) {
			// Convert to Territory type
			Territory t = b.getTerritory(e.getKey());
			// Update the color with the current player
			String thisColor = playerColors.get(b.getPlayerFromTerritory(t));
			e.getValue().setPlayerColor(thisColor);
			// Updates the total number of soldiers
			e.getValue().setText(t.getNumSoldiers() + "");
			// Forces repaint
			e.getValue().revalidate();
			e.getValue().repaint();
		}
		
	}

	@Override
	public void notifyGameStarted() {
		initPlayerColors();
		
		Board b = gameAgent.getBoard();
	}
	
	/**
	 * Method called by the GameAgent observable.
	 */
	@Override
	public void notifyTurnEnded() {
		updateAllTerritories();
		
	}

	
}
