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
	 * Chooses a 
	 * @param b
	 * @return
	 */
	@Override
	public AtackAction atack(Board b) {
		Random r = new Random();

		// FIXME! Obter player correto e retirar daqui os setTerritory
		b.setTerritoryPlayer("Joao", "EU_IBE");
		b.setTerritoryPlayer("Joao", "EU_CEN");
		b.setTerritoryPlayer("Joao", "AS_YAM");
		ArrayList<String> playerTerritories = b.getPlayerTerritories("Joao");
		// Can't attack without territories
		if(playerTerritories.size() == 0) {
			return new PerformAtackAction("NA","NA");
		}

		int from = r.nextInt(playerTerritories.size() + 1);
		// If the random == size, then don't attack.
		if(from == playerTerritories.size()) {
			return new PerformAtackAction("NA","NA");
		}

		// Choose an adjacent territory to attack.
		Territory terr = b.getTerritory(playerTerritories.get(from));
		ArrayList<Territory> adjacentTerritories = terr.getAdjacents();
		int to = r.nextInt(adjacentTerritories.size());

		return new PerformAtackAction(playerTerritories.get(from), adjacentTerritories.get(to).getKey());
	}

	@Override
	public ContinueAction continueAtack(Board b,boolean wonLast, int mySoldiers, int hisSoldiers) {
		return new ContinueAction(false);
	}

	@Override
	public FortifyAction fortify(Board b) {
		return new DontFortifyAction();
	}

}
