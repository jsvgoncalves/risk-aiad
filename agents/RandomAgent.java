package agents;

import java.util.ArrayList;
import java.util.Random;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.PerformFortificationAction;
import actions.ReceiveAction;

public class RandomAgent extends PlayerAgentBehaviours {
	Random r = new Random();
	/**
	 * Receives soldiers and places them in territories.
	 */
	@Override
	public ReceiveAction receiveSoldiers(Board b, int numSoldiers) {
		ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
		// Can't place soldiers without territories
		if(playerTerritories.size() == 0) {
			return new ReceiveAction();
		}
		
		int index, size = playerTerritories.size();
		ReceiveAction action = new ReceiveAction();

		// Choose a random territory for each soldier received.
		for (int i = 0; i < numSoldiers; i++) {
			index = r.nextInt(size);
			action.addSoldiersTerritory(1, playerTerritories.get(index));
		}
		return action;
	}

	/**
	 * Chooses randomly a territory to attack (or don't attack)
	 * @param b
	 * @return PerformAtackAction(from,to) or dontAtackAction
	 */
	@Override
	public AtackAction atack(Board b) {
		ArrayList<String> playerTerritories = b.getReadyPlayerTerritories(myAgent.getLocalName());
		// Can't attack without territories
		if(playerTerritories.size() == 0) {
			return new DontAtackAction();
		}

		int from = r.nextInt(playerTerritories.size() + 1);
		// If the random == size, then don't attack.
		if(from == playerTerritories.size()) {
			return new DontAtackAction();
		}

		// Choose an adjacent territory to attack.
		Territory terr = b.getTerritory(playerTerritories.get(from));
		ArrayList<Territory> adjacentTerritories = b.getEnemyAdjacents(terr, myAgent.getLocalName());
		if( adjacentTerritories.size() == 0 ){
			return new DontAtackAction();
		}
		
		int to = r.nextInt(adjacentTerritories.size());

		// Return an attack action with (from,to)
		return new PerformAtackAction(playerTerritories.get(from), adjacentTerritories.get(to).getKey());
	}
	
	/**
	 * Chooses randomly where to fortify (or don't fortify)
	 */
	@Override
	public FortifyAction fortify(Board b) {
		// Get a random player territory

		ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
		// Can't attack without territories
		if(playerTerritories.size() == 0) {
			return new DontFortifyAction();
		}
		// If random number is equal to size, don't fortify.
		int from = r.nextInt(playerTerritories.size());
		// If the random == size, then don't fortify.
		if(from == playerTerritories.size() || b.getTerritory(playerTerritories.get(from)).getNumSoldiers() <= 1 ) {
			return new DontFortifyAction();
		}
		// Get a random territory with route from the previously selected territory
		Territory init = b.getTerritory(playerTerritories.get(from)); // Convert from string to Territory
		ArrayList<Territory> reachables = b.getReachables(init, myAgent.getLocalName());
		// If the player doesn't have reachable territories from the init, then don't fortify.
		if(reachables.size() == 0) {
			return new DontFortifyAction();
		}
		int to = r.nextInt(reachables.size());
		int amount = r.nextInt(init.getNumSoldiers()) + 1;
//		rand.nextInt((max - min) + 1) + min;
		// Choose a random number of soldiers from 1 to size-1
		return new PerformFortificationAction(init.getKey(), reachables.get(to).getKey(), amount);
	}

}
