package logic;

import java.util.HashMap;

public class Board {
	static HashMap<String, Territory> territories = new HashMap<String, Territory>();
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
}
