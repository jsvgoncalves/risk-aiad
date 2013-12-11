package perceptions;

import java.io.Serializable;

import logic.Territory;

public class AtackedPerception extends Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8588208690825140989L;

	private Territory atacked, atacker;
	private String player;
	private boolean won;

	public AtackedPerception(Territory atacked, Territory atacker, String player, boolean won) {
		this.player = player;
		this.atacked = atacked;
		this.atacker = atacker;
		this.won = won;
	}
	
	//Ganhei batalha. Se não então já sei que perdi um soldado
	public boolean iWon(){
		return won;
	}

	public Territory getAtacker() {
		return atacker;
	}

	public Territory getAtacked() {
		return atacked;
	}

	//Player que atacou
	public String getPlayer() {
		return player;
	}

}
