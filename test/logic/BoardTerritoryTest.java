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
	@Before
	public void setUp() throws Exception {
		b = Board.getInstance();
	}

	@Test
	public void test() {
		Territory IBE = b.getTerritory("EU_IBE");
		Territory UKR = b.getTerritory("AN_WIL");
		ArrayList<Territory> IBE_adjacents = IBE.getAdjacents();
		ArrayList<Territory> UKR_adjacents = UKR.getAdjacents();
		
//		System.out.println("IBE has " + IBE_adjacents.size() + " adjacencies");
//		System.out.println("IBE has adjacent: " + IBE_adjacents.get(0).getKey());
//		System.out.println("UKR has " + UKR_adjacents.size() + " adjacencies");
//		System.out.println("UKR has adjacent: " + UKR_adjacents.get(0).getKey());
		
		HashMap<String, Territory> territories =  b.getTerritories();
		
		for (Territory tt : territories.values()) {
//			System.out.println(tt.getName());
		}
		
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
	
	@Test
	public void testReachables() {
		// Get all territories
		HashMap<String, Territory> territories =  b.getTerritories();
		ArrayList<Territory> reachables = territories.get("EU_IBE").getReachable(territories.get("EU_IBE"));
		assertTrue(reachables.size() == 1);
		assertTrue(reachables.get(0).getKey() == "EU_FR");
		fail("Not yet implemented");
	}

}
