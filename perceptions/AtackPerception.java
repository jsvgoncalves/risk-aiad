package perceptions;

import java.io.Serializable;
import java.util.ArrayList;

public class AtackPerception extends Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8588208690825140989L;

	private String from, to, playerFrom, playerTo;
	private int initSoldFrom, initSoldTo, finalSoldFrom, finalSoldTo;
	private boolean conquered;
	
	public AtackPerception(String from, String to, String playerFrom,
			String playerTo, int initSoldFrom, int initSoldTo,
			int finalSoldFrom, int finalSoldTo, boolean conquered) {
		this.setConquered(conquered);
		this.setFinalSoldFrom(finalSoldFrom);
		this.setFinalSoldTo(finalSoldTo);
		this.setFrom(from);
		this.setInitSoldFrom(initSoldFrom);
		this.setInitSoldTo(initSoldTo);
		this.setPlayerFrom(playerFrom);
		this.setPlayerTo(playerTo);
		this.setTo(to);
	}

	public String getFrom() {
		return from;
	}

	private void setFrom(String from) {
		this.from = from;
	}

	public String getPlayerFrom() {
		return playerFrom;
	}

	private void setPlayerFrom(String playerFrom) {
		this.playerFrom = playerFrom;
	}

	public String getPlayerTo() {
		return playerTo;
	}

	private void setPlayerTo(String playerTo) {
		this.playerTo = playerTo;
	}

	public String getTo() {
		return to;
	}

	private void setTo(String to) {
		this.to = to;
	}

	public int getFinalSoldTo() {
		return finalSoldTo;
	}

	private void setFinalSoldTo(int finalSoldTo) {
		this.finalSoldTo = finalSoldTo;
	}

	public int getInitSoldTo() {
		return initSoldTo;
	}

	private void setInitSoldTo(int initSoldTo) {
		this.initSoldTo = initSoldTo;
	}

	public int getFinalSoldFrom() {
		return finalSoldFrom;
	}

	private void setFinalSoldFrom(int finalSoldFrom) {
		this.finalSoldFrom = finalSoldFrom;
	}

	public int getInitSoldFrom() {
		return initSoldFrom;
	}

	private void setInitSoldFrom(int initSoldFrom) {
		this.initSoldFrom = initSoldFrom;
	}

	public boolean isConquered() {
		return conquered;
	}

	private void setConquered(boolean conquered) {
		this.conquered = conquered;
	}

	@Override
	public ArrayList<String> getAllPlayersInvolved(String player) {
		ArrayList<String> players = new ArrayList<String>();
		
		if(!player.equals(playerFrom))
			players.add(playerFrom);
		
		if(!player.equals(playerTo))
			players.add(playerTo);
		return players;
	}

}
