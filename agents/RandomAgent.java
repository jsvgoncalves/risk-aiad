package agents;

import java.util.ArrayList;
import java.util.Random;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.ContinueAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.ReceiveAction;

public class RandomAgent extends PlayerAgentBehaviours {

	@Override
	public ReceiveAction receiveSoldiers(Board b,int n) {
		return new ReceiveAction();
	}

	/**
	 * Chooses randomly a territory to attack (or don't attack)
	 * @param b
	 * @return PerformAtackAction(from,to) or dontAtackAction
	 */
	@Override
	public AtackAction atack(Board b) {
		Random r = new Random();

		ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
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
		ArrayList<Territory> adjacentTerritories = terr.getAdjacents();
		int to = r.nextInt(adjacentTerritories.size());

		// Return an attack action with (from,to)
		return new PerformAtackAction(playerTerritories.get(from), adjacentTerritories.get(to).getKey());
	}

	@Override
	public ContinueAction continueAtack(Board b,boolean wonLast, int mySoldiers, int hisSoldiers) {
		Random r = new Random();
		return new ContinueAction(r.nextBoolean());
	}

	@Override
	public FortifyAction fortify(Board b) {
		// Get a random player territory
		
		// If random number is equal to size, don't fortify.
		
		// Get a random territory with route from the previously selected territory
		
		// Choose a random number of soldiers from 1 to size-1
		return new DontFortifyAction();
	}

}
