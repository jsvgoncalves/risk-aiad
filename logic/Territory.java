package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Territory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4852948895519742685L;
	String name, key;
	ArrayList<Territory> adjacents = new ArrayList<Territory>();

	private int numSoldiers;

	public Territory(String name, String key) {
		this.name = name;
		this.key = key;
		adjacents = new ArrayList<Territory>();
	}

	public boolean setAdjacent(Territory t) {
		if (adjacents.contains(t)) {
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

	public int getNumSoldiers() {
		return numSoldiers;
	}

	public void addSoldiers(int n) {
		numSoldiers += n;
	}

	public void removeSoldiers(int n) {
		if (numSoldiers <= 0 )
			numSoldiers=0;
		else
			numSoldiers -= n;
	}

	public void conquer(Territory tTo) {
		tTo.addSoldiers(numSoldiers-1);
		numSoldiers=1;
	}
	
	@Override
	public String toString() {
		return key;
	}

}
