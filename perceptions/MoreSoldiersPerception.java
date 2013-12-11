package perceptions;

import java.io.Serializable;

import logic.Territory;

public class MoreSoldiersPerception extends Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2888368542820994913L;
	private Territory t;
	
	public MoreSoldiersPerception(Territory t){
		this.t =t;
	}
	
	public Territory getTerritory(){
		return t;
	}
}
