package perceptions;

import java.io.Serializable;
import java.util.ArrayList;

public class FortifyPerception extends Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5445035733531457058L;

	private String from, to, player;
	private int initSoldFrom, initSoldTo, movedSold;

	public FortifyPerception(String from, String to, String player,
			int initSoldFrom, int initSoldTo, int movedSold) {
		this.from = from;
		this.initSoldFrom = initSoldFrom;
		this.initSoldTo = initSoldTo;
		this.movedSold = movedSold;
		this.player = player;
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public String getPlayer() {
		return player;
	}

	public int getInitSoldFrom() {
		return initSoldFrom;
	}

	public int getInitSoldTo() {
		return initSoldTo;
	}

	public int getMovedSold() {
		return movedSold;
	}

	@Override
	public ArrayList<String> getAllPlayersInvolved(String player2) {
		ArrayList<String> players = new ArrayList<String>();
		if( !player2.equals(player))
			players.add(player);
		return players;
	}
}
