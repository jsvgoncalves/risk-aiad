package logic;

import java.util.ArrayList;

public class Territory {

	String name, key;
	ArrayList<Territory> adjacents = new ArrayList<Territory>();
	
	int numSoldiers;
	
	// Redudant
	private String player;
	
	public Territory (String name, String key) {
		this.name = name;
		this.key = key;
	}
	
	public boolean setAdjacent(Territory t) {
		if(adjacents.contains(t)) {
			return true;
		}
		adjacents.add(t);
		t.setAdjacent(this);
		return true;
	}
	
	public ArrayList<Territory> getAdjacents() {
		return adjacents;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}
	
}
