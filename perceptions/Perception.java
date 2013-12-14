package perceptions;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1783470818685315508L;
	
	public Perception(){
	}

	/**
	 * returns the players involved in the perception except the player passed as argument
	 * @param player
	 * @return
	 */
	public abstract ArrayList<String> getAllPlayersInvolved(String player);
}
