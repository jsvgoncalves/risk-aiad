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
		/*
		 * Territories
		 */
		
		//EUROPE
		Territory EU_IBE = new Territory("Iberia", "EU_IBE");
		Territory EU_FR = new Territory("France", "EU_FR");
		Territory EU_IT = new Territory("Italy", "EU_IT");
		Territory EU_CEN = new Territory("CEN Europe", "EU_CEN");
		Territory EU_BAL = new Territory("Balkans", "EU_BAL");
		Territory EU_BRI = new Territory("British Isles", "EU_BRI");
		Territory EU_ICE = new Territory("Iceland", "EU_ICE");
		Territory EU_SCA = new Territory("Scandinavia", "EU_SCA");
		Territory EU_EAS = new Territory("Eastern Europe", "EU_EAS");
		Territory EU_RUS = new Territory("European Russia", "EU_RUS");
		
		//ASIA
		Territory AS_YAM = new Territory("Yamalia", "AS_YAM");
		Territory AS_STA = new Territory("The Stans", "AS_STA");
		Territory AS_MEA = new Territory("Middle East", "AS_MEA");
		Territory AS_ARA = new Territory("Arabia", "AS_ARA");
		Territory AS_SAK = new Territory("Sakha", "AS_SAK");
		Territory AS_MON = new Territory("Mongolia", "AS_MON");
		Territory AS_CHI = new Territory("China", "AS_CHI");
		Territory AS_IND = new Territory("India", "AS_IND");
		Territory AS_INO = new Territory("Indochina", "AS_INO");
		Territory AS_MAC = new Territory("Manchuria", "AS_MAC");
		Territory AS_CHU = new Territory("Chukotka", "AS_CHU");
		Territory AS_JAP = new Territory("Japan", "AS_JAP");
		
		//OCEANIA
		Territory OC_BOR = new Territory("Borneo", "OC_BOR");
		Territory OC_EAS = new Territory("East Indies", "OC_EAS");
		Territory OC_OUT = new Territory("Outback", "OC_OUT");
		Territory OC_AUS = new Territory("Eastern Australia", "OC_AUS");
		Territory OC_NEW = new Territory("New Zealand", "OC_NEW");
		
		//ANTARCTICA
		Territory AN_WIL = new Territory("Wilkes Land", "AN_WIL");
		Territory AN_QUE = new Territory("Queen Maud Land", "AN_QUE");	
		Territory AN_MAR = new Territory("Marie Byrd Land", "AN_MAR");
		
		/*
		 * Add
		 */
		
		//EUROPE
		territories.put(EU_IBE.getKey(), EU_IBE);
		territories.put(EU_FR.getKey(), EU_FR);
		territories.put(EU_IT.getKey(), EU_IT);
		territories.put(EU_CEN.getKey(), EU_CEN);
		territories.put(EU_BAL.getKey(), EU_BAL);
		territories.put(EU_BRI.getKey(), EU_BRI);
		territories.put(EU_ICE.getKey(), EU_ICE);
		territories.put(EU_SCA.getKey(), EU_SCA);
		territories.put(EU_EAS.getKey(), EU_EAS);
		territories.put(EU_RUS.getKey(), EU_RUS);

		//Asia
		territories.put(AS_YAM.getKey(), AS_YAM);
		territories.put(AS_STA.getKey(), AS_STA);
		territories.put(AS_MEA.getKey(), AS_MEA);
		territories.put(AS_ARA.getKey(), AS_ARA);
		territories.put(AS_SAK.getKey(), AS_SAK);
		territories.put(AS_MON.getKey(), AS_MON);
		territories.put(AS_MAC.getKey(), AS_MAC);
		territories.put(AS_CHI.getKey(), AS_CHI);
		territories.put(AS_IND.getKey(), AS_IND);
		territories.put(AS_INO.getKey(), AS_INO);
		territories.put(AS_CHU.getKey(), AS_CHU);
		territories.put(AS_JAP.getKey(), AS_JAP);
		
		//Oceania
		territories.put(OC_BOR.getKey(), OC_BOR);
		territories.put(OC_EAS.getKey(), OC_EAS);
		territories.put(OC_OUT.getKey(), OC_OUT);
		territories.put(OC_AUS.getKey(), OC_AUS);
		territories.put(OC_NEW.getKey(), OC_NEW);
		
		//Antarctica
		territories.put(AN_WIL.getKey(), AN_WIL);
		territories.put(AN_QUE.getKey(), AN_QUE);
		territories.put(AN_MAR.getKey(), AN_MAR);
		
		/*
		 * Borders
		 */
		
		//EUROPE
		EU_IBE.setAdjacent(EU_FR);
		EU_FR.setAdjacent(EU_CEN);
		EU_FR.setAdjacent(EU_BRI);
		EU_FR.setAdjacent(EU_IT);
		EU_IT.setAdjacent(EU_CEN);
		EU_CEN.setAdjacent(EU_BAL);
		EU_CEN.setAdjacent(EU_EAS);
		EU_BRI.setAdjacent(EU_ICE);
		EU_BRI.setAdjacent(EU_SCA);
		EU_ICE.setAdjacent(EU_SCA);
		EU_SCA.setAdjacent(EU_RUS);
		EU_BAL.setAdjacent(EU_EAS);
		EU_EAS.setAdjacent(EU_RUS);
		
		//ASIA
		AS_YAM.setAdjacent(AS_STA);
		AS_YAM.setAdjacent(AS_SAK);
		AS_YAM.setAdjacent(EU_RUS);
		AS_YAM.setAdjacent(AS_MON);
		AS_STA.setAdjacent(AS_CHI);
		AS_STA.setAdjacent(AS_MEA);
		AS_STA.setAdjacent(EU_RUS);
		AS_STA.setAdjacent(AS_IND);
		AS_MEA.setAdjacent(AS_ARA);
		AS_MEA.setAdjacent(EU_RUS);
		AS_MEA.setAdjacent(EU_BAL);
		AS_SAK.setAdjacent(AS_MON);
		AS_SAK.setAdjacent(AS_MAC);
		AS_SAK.setAdjacent(AS_CHU);
		AS_MON.setAdjacent(AS_CHI);
		AS_MON.setAdjacent(AS_MAC);
		AS_CHI.setAdjacent(AS_IND);
		AS_CHI.setAdjacent(AS_INO);
		AS_CHI.setAdjacent(AS_MAC);
		AS_CHI.setAdjacent(AS_JAP);
		AS_JAP.setAdjacent(AS_MAC);
		AS_JAP.setAdjacent(AS_CHU);
		
		//Oceania
		OC_BOR.setAdjacent(AS_INO);
		OC_BOR.setAdjacent(OC_EAS);
		OC_BOR.setAdjacent(OC_OUT);
		OC_EAS.setAdjacent(OC_NEW);
		OC_OUT.setAdjacent(OC_AUS);
		OC_AUS.setAdjacent(OC_NEW);
		
		//Antarctica
		AN_WIL.setAdjacent(AN_QUE);
		AN_WIL.setAdjacent(OC_AUS);
		AN_QUE.setAdjacent(AN_MAR);
	}

	@Override
	public String toString() {
		return territories.toString();
	}

	public void setTerritoryPlayer(String player, String territory) {
		allocations.put(territory, player);
	}

	public ArrayList<String> getPlayerTerritories(String player) {
		ArrayList<String> playerTerritories = new ArrayList<String>();
		// TODO Auto-generated method stub
		for(Entry<String, String> e: allocations.entrySet()) {
				if(e.getValue().equals(player)) {
				playerTerritories.add(e.getKey());
			}
		}
		return playerTerritories;
	}
}
