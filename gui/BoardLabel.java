package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class BoardLabel extends JLabel{
	private static final long serialVersionUID = -1538790068284672756L;
	
	private double x = 0.0, y = 0.0;
	private Color c;
	private double width = 0.0, height = 0.0;
	private Color bc;

	private HashMap<String, BufferedImage> label_colors = new HashMap<String, BufferedImage>();
	private String playerColor = "red";
	
	BoardLabel(String s, int x, int y, int width, int height, Color back, Color border){
		super(s);
		this.setForeground(Color.white);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 10));
		this.setLocation(x, y);
		this.setSize(new Dimension(width, height));
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c = back;
		this.bc = border;
		loadLabelColors();
	}
	
	BoardLabel(String s, int x, int y, int width, int height){
		super(s);
		this.setForeground(Color.white);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setLocation(x, y);
		this.setSize(new Dimension(width, height));
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		loadLabelColors();
	}
	
	private void loadLabelColors() {
		try {
			label_colors.put("blue", ImageIO.read(BoardGUI.class.getResource("res/labels/blue.png")));
			label_colors.put("green", ImageIO.read(BoardGUI.class.getResource("res/labels/green.png")));
			label_colors.put("orange", ImageIO.read(BoardGUI.class.getResource("res/labels/orange.png")));
			label_colors.put("purple", ImageIO.read(BoardGUI.class.getResource("res/labels/purple.png")));
			label_colors.put("red", ImageIO.read(BoardGUI.class.getResource("res/labels/red.png")));
			label_colors.put("yellow", ImageIO.read(BoardGUI.class.getResource("res/labels/yellow.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Color getC() {
		return c;
	}
	public void setC(Color c) {
		this.c = c;
	}
	
	public Color getBc() {
		return bc;
	}
	
	public void setBc(Color bc) {
		this.bc = bc;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setPlayerColor(String color) {
		this.playerColor = color;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		g.drawImage(label_colors.get(playerColor), 0, 0, this.getWidth(), this.getHeight(), this);
		
		int xtemp = (int)Math.ceil(((double)this.getParent().getWidth() * x)/(double)(BoardGUI.PANEL_WIDTH));
		int ytemp = (int)Math.ceil(((double)this.getParent().getHeight()* y)/(double)(BoardGUI.PANEL_HEIGHT));
	
		double width_ratio = (((double)this.getParent().getWidth()/(double)(BoardGUI.PANEL_WIDTH)));
		double height_ratio = (((double)this.getParent().getHeight()/(double)(BoardGUI.PANEL_HEIGHT)));
		double ratio = 1.0;
		if(width_ratio > height_ratio)
			ratio = width_ratio/height_ratio;
		else
			 ratio = height_ratio/width_ratio;
		
		int widtht  = (int)Math.ceil(width_ratio*width);
		int heightt = (int)Math.ceil(height_ratio*height);

		
//		System.err.println(xtemp + ", " + ytemp);
		this.setLocation(xtemp, ytemp);
		this.setSize(new Dimension(widtht, heightt));
		
		super.paintComponent(g);
	}
}
