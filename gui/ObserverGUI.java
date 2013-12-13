package gui;

import javax.swing.JPanel;


public abstract class ObserverGUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1387656780357424370L;

	/**
	 * Method called by the GameAgent observable.
	 */
	public abstract void notifyTurnEnded();
	
	/**
	 * Method called by the GameAgent observable.
	 */
	public abstract void notifyGameStarted();
}
