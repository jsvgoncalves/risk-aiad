package gui;

import java.awt.Color;

import javax.swing.JLabel;

public class PlayersLabel extends JLabel{

	public PlayersLabel(String localName) {
		super(localName);
	}

	public void setTextColor(String color) {
		if(color.equals("blue")) {
			setForeground(Color.blue);
		} else if(color.equals("green")) {
			setForeground(Color.green);
		} else if(color.equals("orange")) {
			setForeground(Color.orange);
		} else if(color.equals("purple")) {
//			setForeground(Color.);
		} else if(color.equals("red")) {
			setForeground(Color.red);
		} else if(color.equals("yellow")) {
			setForeground(Color.yellow);
		} 
			
		
		
	}

	public void setBackground(String color) {
		if(color.equals("blue")) {
			setBackground(Color.blue);
		} else if(color.equals("green")) {
			setBackground(Color.green);
		} else if(color.equals("orange")) {
			setBackground(Color.orange);
		} else if(color.equals("purple")) {
//					setBackground(Color.);
		} else if(color.equals("red")) {
			setBackground(Color.red);
		} else if(color.equals("yellow")) {
			setBackground(Color.yellow);
		} 		
	}

	
}
