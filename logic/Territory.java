package logic;

import java.util.ArrayList;

public class Territory {

	String name;
	ArrayList<Territory> adjacent;
	
	public Territory (String name) {
		this.name = name;
	}
	
	public boolean setAdjacent(Territory t) {
		adjacent.add(t);
		return true;
	}
	
	public ArrayList<Territory> getAdjacents() {
		return adjacent;
	}
}
