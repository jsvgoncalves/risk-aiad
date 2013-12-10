package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Territory;

public class BoardGUI extends JPanel implements Observer{
	public static final int PANEL_WIDTH = 1004;
	public static final int PANEL_HEIGHT= 659;
	private static final int LABEL_WIDTH = 29;
	private static final int LABEL_HEIGHT = 20;
	private static final Color LABEL_BORDER_COLOR = Color.gray;
	
	private HashMap<String, BoardLabel> labels = new HashMap<String, BoardLabel>();
	
	private static final long serialVersionUID = 8906083247845612415L;
	private BufferedImage board;
	public BoardGUI() {
		// TODO Auto-generated constructor stub
		board = null;
		try {
		    board = ImageIO.read(BoardGUI.class.getResource("res/board.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setLayout(null);
		
		// AMERICA LABELS
		labels.put("NA_NUN", new BoardLabel("1", 167, 126, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_NUN"));
		labels.put("NA_KLO", new BoardLabel("1", 58, 111, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_KLO"));
		labels.put("NA_PAC", new BoardLabel("1", 107, 213, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_PAC"));
		labels.put("NA_GRN", new BoardLabel("1", 335, 71, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_GRN"));
		labels.put("NA_GRP", new BoardLabel("1", 171, 215, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_GRP"));
		labels.put("NA_SUN", new BoardLabel("1", 222, 257, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_SUN"));
		labels.put("NA_STL", new BoardLabel("1", 234, 171, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_STL"));
		labels.put("NA_EAS", new BoardLabel("1", 270, 208, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_EAS"));
		labels.put("NA_CAR", new BoardLabel("1", 289, 332, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_CAR"));
		labels.put("NA_MEX", new BoardLabel("1", 178, 324, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("NA_MEX"));
		
		// SOUTH AMERICA
		
		// EUROPE
		labels.put("EU_IBE", new BoardLabel("1", 395, 275, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_IBE"));
		labels.put("EU_FR", new BoardLabel("1", 425, 231, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_FR"));
		labels.put("EU_IT", new BoardLabel("1", 458, 266, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_IT"));
		labels.put("EU_CEN", new BoardLabel("1", 467, 207, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_CEN"));
		labels.put("EU_BAL", new BoardLabel("1", 505, 235, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_BAL"));
		labels.put("EU_BRI", new BoardLabel("1", 411, 159, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_BRI"));
		labels.put("EU_ICE", new BoardLabel("1", 423, 99, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_ICE"));
		labels.put("EU_SCA", new BoardLabel("1", 524, 64, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_SCA"));
		labels.put("EU_EAS", new BoardLabel("1", 543, 197, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_EAS"));
		labels.put("EU_RUS", new BoardLabel("1", 582, 164, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("EU_RUS"));
		
		// ASIA
		labels.put("AS_YAM", new BoardLabel("1", 661, 128, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_YAM"));
		labels.put("AS_STA", new BoardLabel("1", 633, 234, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_STA"));
		labels.put("AS_MEA", new BoardLabel("1", 547, 263, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_MEA"));
		labels.put("AS_ARA", new BoardLabel("1", 568, 323, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_ARA"));
		labels.put("AS_SAK", new BoardLabel("1", 778, 116, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_SAK"));
		labels.put("AS_MON", new BoardLabel("1", 754, 208, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_MON"));
		labels.put("AS_CHI", new BoardLabel("1", 757, 272, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_CHI"));
		labels.put("AS_IND", new BoardLabel("1", 675, 320, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_IND"));
		labels.put("AS_INO", new BoardLabel("1", 772, 333, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_INO"));
		labels.put("AS_MAC", new BoardLabel("1", 831, 207, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_MAC"));
		labels.put("AS_CHU", new BoardLabel("1", 902, 91, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_CHU"));
		labels.put("AS_JAP", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("AS_JAP"));
		
		// OCEANIA
		labels.put("OC_BOR", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("OC_BOR"));
		labels.put("OC_EAS", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("OC_EAS"));
		labels.put("OC_OUT", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("OC_OUT"));
		labels.put("OC_AUS", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("OC_AUS"));
		labels.put("OC_NEW", new BoardLabel("1", 893, 245, LABEL_WIDTH, LABEL_HEIGHT, Color.red, LABEL_BORDER_COLOR));
		this.add(labels.get("OC_NEW"));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(board, 0, 0, this.getWidth(), this.getHeight(), this);
	
	}

	@Override
	public void update(Observable o, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println(arg1);
	}
}
