package perceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReceivedSoldiersPerception extends Perception implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6200559451499172518L;

	private String player;
	private Hashtable<String, Integer> initial, finalS, moved;

	public ReceivedSoldiersPerception(String player,
			Hashtable<String, Integer> movedSoldiers,
			Hashtable<String, Integer> initial,
			Hashtable<String, Integer> finalS) {
		this.initial = initial;
		this.moved = movedSoldiers;
		this.finalS = finalS;
		this.player = player;
	}

	public String getPlayer() {
		return player;
	}

	public Hashtable<String, Integer> getFinalS() {
		return finalS;
	}

	public Hashtable<String, Integer> getMoved() {
		return moved;
	}

	public Hashtable<String, Integer> getInitial() {
		return initial;
	}

	@Override
	public ArrayList<String> getAllPlayersInvolved(String player2) {
		ArrayList<String> players = new ArrayList<String>();
		if (!player2.equals(player))
			players.add(player);
		return players;
	}

}
