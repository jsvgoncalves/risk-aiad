package test.logic;

import static org.junit.Assert.*;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.HashMap;

import logic.Board;
import logic.Territory;

import org.junit.Before;
import org.junit.Test;

import agents.RandomAgent;

import util.R;

public class BoardTerritoryTest {

	Board b;

	@Before
	public void setUp() throws Exception {
		b = Board.getInstance();
	}

	@Test
	public void test() {
		
		
		Territory IBE = b.getTerritory("EU_IBE");
		Territory CHI = b.getTerritory("AS_CHI");
		ArrayList<Territory> IBE_adjacents = IBE.getAdjacents();
		ArrayList<Territory> CHI_adjacents = CHI.getAdjacents();

		System.out.println("IBE has " + IBE_adjacents.size() + " adjacencies");
		System.out
				.println("IBE has adjacent: " + IBE_adjacents.get(0).getKey());
		System.out.println("CHI has " + CHI_adjacents.size() + " adjacencies");
		System.out
				.println("CHI has adjacent: " + CHI_adjacents.get(0).getKey());

		HashMap<String, Territory> territories = b.getTerritories();

		for (Territory tt : territories.values()) {
			System.out.println(tt.getName());
		}

		fail("Not yet implemented");
	}

	/**
	 * Checks if the graph is bidirectional
	 */
	@Test
	public void testAdjancencies() {
		// Get all territories
		HashMap<String, Territory> territories = b.getTerritories();

		// For each adjacent territory of each territory
		for (Territory t : territories.values()) {
			// String key = t.getKey();
			for (Territory a : t.getAdjacents()) {
				// Verify that the adjacency is bidirectional
				assertTrue(a.getAdjacents().contains(t));
			}
		}
	}

	@Test
	public void testRandomAllocation() {

		ArrayList<AID> players = new ArrayList<AID>();

		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(R.GUI_CONFIG, R.GUI_OFF);
		profile.setParameter(R.PORT_CONFIG, R.PORT);
		jade.wrapper.AgentContainer container = runtime
				.createMainContainer(profile);

		try {
			ArrayList<String> names = util.NameGenerator.randomName(3);
			Agent a = new Agent();
			Agent b = new Agent();
			Agent c = new Agent();
			Agent d = new Agent();
			Agent e = new Agent();
			container.acceptNewAgent(names.get(0), a).start();
			container.acceptNewAgent(names.get(1), b).start();
			container.acceptNewAgent(names.get(2), c).start();
			container.acceptNewAgent(names.get(3), d).start();
			container.acceptNewAgent(names.get(4), e).start();
			
			players.add(a.getAID());
			players.add(b.getAID());
			players.add(c.getAID());
			players.add(d.getAID());
			players.add(e.getAID());

			Board.getInstance().allocateRandomTerritories(players);

			assertTrue(Board.getInstance().getEmptyTerritories().size() == 0);
			
			ArrayList<Territory> territories = Board.getInstance().getPlayerTerritoriesT(a.getLocalName());
			int sum = 0;
			for(Territory t:territories){
				sum+=t.getNumSoldiers();
			}
		
			assertTrue(sum == Board.getInstance().getStartingSoldiersNumber(players.size()));
			
		} catch (StaleProxyException e) {
			fail();
		}

		
	}

}
