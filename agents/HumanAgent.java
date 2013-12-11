/**
 * 
 */
package agents;

import java.util.ArrayList;
import java.util.Scanner;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.ContinueAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.ReceiveAction;

public class HumanAgent extends PlayerAgentBehaviours {

	/* (non-Javadoc)
	 * @see agents.PlayerAgentBehaviours#receiveSoldiers(logic.Board, int)
	 */
	@Override
	public ReceiveAction receiveSoldiers(Board b, int n) {
		// TODO Auto-generated method stub
		return new ReceiveAction();
	}

	/* (non-Javadoc)
	 * @see agents.PlayerAgentBehaviours#atack(logic.Board)
	 */
	@Override
	public AtackAction atack(Board b) {
		Scanner reader = new Scanner(System.in);
		ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
		
		System.err.println("0 - Don't attack");
		for (int i = 0; i < playerTerritories.size(); i++) {
			System.err.println(i + 1 + " - " + playerTerritories.get(i) + " ( " + 
					 b.getTerritory(playerTerritories.get(i)).getNumSoldiers() + " )" );
		}
		
		System.err.println("Enter the territory from which to attack:");
		int from = reader.nextInt();
		if(from == 0) {
			return new DontAtackAction();
		}
		from -= 1; // Adjust index from user input.
		
		// Choose an adjacent territory to attack.
		Territory terr = b.getTerritory(playerTerritories.get(from));
		ArrayList<Territory> adjacentTerritories = terr.getAdjacents();
		System.err.println("Enter the territory to attack:");
		for (int i = 0; i < adjacentTerritories.size(); i++) {
			System.err.println(i + 1 + " - " + adjacentTerritories.get(i).getKey() + " ( " + 
					 adjacentTerritories.get(i).getNumSoldiers() + " )" );
		}
		int to = reader.nextInt();
		to -= 1;
		// Return an attack action with (from,to)
		return new PerformAtackAction(playerTerritories.get(from), adjacentTerritories.get(to).getKey());
	}

	/* (non-Javadoc)
	 * @see agents.PlayerAgentBehaviours#continueAtack(logic.Board, boolean, int, int)
	 */
	@Override
	public ContinueAction continueAtack(Board b, boolean wonLast,
			int mySoldiers, int hisSoldiers) {
		// TODO Auto-generated method stub
		return new ContinueAction(false);
	}

	/* (non-Javadoc)
	 * @see agents.PlayerAgentBehaviours#fortify(logic.Board)
	 */
	@Override
	public FortifyAction fortify(Board b) {
		// TODO Auto-generated method stub
		return new DontFortifyAction();
	}

}
