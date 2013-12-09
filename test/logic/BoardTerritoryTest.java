package test.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import logic.Board;
import logic.Territory;

import org.junit.Before;
import org.junit.Test;

public class BoardTerritoryTest {

	Board b;
	String player;
	
	@Before
	public void setUp() throws Exception {
		b = Board.getInstance();
		player = "Joao";
		b.setTerritoryPlayer(player, "EU_RUS");
		b.setTerritoryPlayer(player, "EU_CEN");
		b.setTerritoryPlayer(player, "EU_EAS");
		b.setTerritoryPlayer(player, "EU_IBE");
		b.setTerritoryPlayer(player, "EU_BRI");
		b.setTerritoryPlayer(player, "EU_FR");
		b.setTerritoryPlayer(player, "AS_MEA");
		b.setTerritoryPlayer(player, "EU_IT");
	}

	@Test
	public void test() {
		Territory IBE = b.getTerritory("EU_IBE");
		Territory UKR = b.getTerritory("AN_WIL");
		ArrayList<Territory> IBE_adjacents = IBE.getAdjacents();
		ArrayList<Territory> UKR_adjacents = UKR.getAdjacents();
				
		HashMap<String, Territory> territories =  b.getTerritories();
		
//		for (Territory tt : territories.values()) {
//			System.out.println(tt.getName());
//		}
		
		fail("Not yet implemented");
	}
	
	/**
	 * Checks if the graph is bidirectional
	 */
	@Test
	public void testAdjancencies() {
		// Get all territories
		HashMap<String, Territory> territories =  b.getTerritories();
		
		// For each adjacent territory of each territory
		for (Territory t : territories.values()) {
			//String key = t.getKey();
			for(Territory a: t.getAdjacents()) {
				// Verify that the adjacency is bidirectional
				assertTrue(a.getAdjacents().contains(t));
			}
		}
	}
	
	/**
	 * Tests if the method returns the correct immediatly adjacent territories.
	 */
	@Test
	public void testgetPlayerAdjacents() {
		String player = "Joao";
		/*
		 * EU_IBE <-> EU_FR <-> EU_IT   |  AS_JAP
		 */
		
		assertEquals(1, b.getPlayerAdjacents("EU_IBE", player).size());
		assertEquals(4, b.getPlayerAdjacents("EU_FR", player).size());
		assertEquals(2, b.getPlayerAdjacents("EU_IT", player).size());
		
		assertEquals("EU_FR", b.getPlayerAdjacents("EU_IT", player).get(0).getKey());
		assertEquals("EU_IBE", b.getPlayerAdjacents("EU_FR", player).get(0).getKey());
		assertEquals("EU_CEN", b.getPlayerAdjacents("EU_FR", player).get(1).getKey());
	}
	
	@Test
	public void testReachables() {
		// Get all territories
		HashMap<String, Territory> territories =  b.getTerritories();
		ArrayList<Territory> reachables = b.getReachables(territories.get("EU_IBE"), player);
//		for (Territory territory : reachables) {
//			System.err.println("I can reach " + territory.getKey());
//		}
		assertEquals(7, reachables.size());
		assertTrue(reachables.get(0).getKey() == "EU_FR");
	}

}
