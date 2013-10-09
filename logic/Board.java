package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Board {
	// List of all territories in the world
	HashMap<String, Territory> territories = new HashMap<String, Territory>();
	// List of allocations of every player
	// T -> P
	private HashMap<String, String> allocations = new HashMap<String, String>();

	private static Board instance = new Board();

	/**
	 * Singleton
	 * @return
	 */
	public static Board getInstance() {
		return instance;
	}
	
	private Board () {
		initTerritories();
	}

	public Territory getTerritory(String string) {
		return territories.get(string);
	}
	
	public HashMap<String, Territory> getTerritories() {
		return territories;
	}


	/**
	 * Initializes all the territories and their adjacencies
	 */
	private void initTerritories() {
		Territory IBE = new Territory("Iberia", "IBE");
		Territory UKR = new Territory("Ukrain", "UKR");
		
		territories.put(IBE.getKey(), IBE);
		territories.put(UKR.getKey(), UKR);

		IBE.setAdjacent(UKR);
		
	}

	@Override
	public String toString() {
		return territories.toString();
	}

	public void setTerritoryPlayer(String player, String territory) {
		allocations.put(territory, player);
	}

	public void getPlayerTerritories(String player) {
		ArrayList<String> playerTerritories = new ArrayList<String>();
		// TODO Auto-generated method stub
		for(Entry<String, String> e: allocations.entrySet()) {
				if(e.getValue().equals(player)) {
				playerTerritories.add(e.getKey());
			}
		}
	}
}
