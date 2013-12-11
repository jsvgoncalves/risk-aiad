package perceptions;

import java.io.Serializable;

import logic.Territory;

public class LessSoldiersPerception extends Perception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -954288531553791230L;
	private Territory t;

	public LessSoldiersPerception(Territory t) {
		this.t = t;
	}

	public Territory getTerritory() {
		return t;
	}
}
