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

public class AgressiveAgent extends PlayerAgentBehaviours {
	Random r = new Random();
	/**
	 * Receives soldiers and places them in territories.
	 */
	@Override
	public ReceiveAction receiveSoldiers(Board b, int numSoldiers) {
		ArrayList<String> playerTerritories = b.getFortifyReadyPlayerTerritories(myAgent.getLocalName());
		// Can't place soldiers without territories
		if(playerTerritories.size() == 0) {
			return new ReceiveAction();
		}
		
		int index, size = playerTerritories.size();
		ReceiveAction action = new ReceiveAction();
		Territory t = b.getTerritory(playerTerritories.get(selectBestTerritory(playerTerritories, b)));
		action.addSoldiersTerritory(numSoldiers, t.getKey() );
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

//		int from = r.nextInt(playerTerritories.size() + 1);
		int from = selectBestTerritory(playerTerritories, b);
		
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
	 * Selects the best territory from where to attack (maximum players).
	 * @param playerTerritories
	 * @param b 
	 * @return
	 */
	private int selectBestTerritory(ArrayList<String> playerTerritories, Board b) {
		int max = 0;
		int index = 0;
		for (int i = 0; i < playerTerritories.size(); i++) {
			int numSoldiers = b.getTerritory(playerTerritories.get(i)).getNumSoldiers();
			if(numSoldiers > max ) {
				max = numSoldiers;
				index = i;
			}
		}
		System.err.println("Escolhi " + b.getTerritory(playerTerritories.get(index)).getName() + " " + b.getTerritory(playerTerritories.get(index)).getNumSoldiers());
		return index;
	}
	
	/**
	 * Selects a useless territory (has no enemy adjacents and more soldiers than 1)
	 * @param playerTerritories
	 * @param b 
	 * @return
	 */
	private int selectUselessTerritory(ArrayList<String> playerTerritories, Board b) {
		int max = 0;
		int index = 0;
		for (int i = 0; i < playerTerritories.size(); i++) {
			Territory terr = b.getTerritory(playerTerritories.get(i));
			if(	terr.getNumSoldiers() > max
				&& b.getEnemyAdjacents(terr, myAgent.getLocalName()).size() == 0
				) {
					max = terr.getNumSoldiers();
					index = i;
			}
		}
		return index;
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
		// Selects a useless territory. Or index = 0 if it can't find one.
		int from = selectUselessTerritory(playerTerritories, b);
		
		// Get a random territory with route from the previously selected territory
		Territory init = b.getTerritory(playerTerritories.get(from)); // Convert from string to Territory
		
		ArrayList<Territory> reachables = b.getReachables(init, myAgent.getLocalName());
		// If the player doesn't have reachable territories from the init, then don't fortify.
		if(reachables.size() == 0) {
			return new DontFortifyAction();
		}
		ArrayList<String> str_reachables = new ArrayList<String>();
		for (Territory territory : reachables) {
			str_reachables.add(territory.getKey());
		}
		int to = selectBestTerritory(str_reachables, b);
		int amount = init.getNumSoldiers() -1;
		return new PerformFortificationAction(init.getKey(), reachables.get(to).getKey(), amount);
	}

}
