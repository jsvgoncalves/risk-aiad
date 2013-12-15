/**
 * 
 */
package agents;

import java.util.ArrayList;
import java.util.Scanner;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.PerformFortificationAction;
import actions.ReceiveAction;

public class HumanAgent extends PlayerAgentBehaviours {

	Scanner reader = new Scanner(System.in);

	/*
	 * (non-Javadoc)
	 * 
	 * @see agents.PlayerAgentBehaviours#receiveSoldiers(logic.Board, int)
	 */
	@Override
	public ReceiveAction receiveSoldiers(Board b, int availableSoldiers) {
		ReceiveAction receive = new ReceiveAction();
		ArrayList<String> playerTerritories = b.getPlayerTerritories(myAgent.getLocalName());
		int placedSoldiers = 0;
		
		while (placedSoldiers < availableSoldiers) {
			System.err.println("You have " + (availableSoldiers - placedSoldiers) + " soldiers available!");

			for (int i = 0; i < playerTerritories.size(); i++) {
				System.err.println( (i + 1)
						+ " - "
						+ b.getTerritory(playerTerritories.get(i)).getName()
						+ " ( "
						+ b.getTerritory(playerTerritories.get(i))
								.getNumSoldiers() + " ) ");
			}

			System.err.println("Enter the territory where you want to put the soldiers:");
			int from = reader.nextInt();

			int currentNum = availableSoldiers + 1;
			while (currentNum > availableSoldiers - placedSoldiers) {
				System.err.println("Insert number of soldiers:");
				currentNum = reader.nextInt();
				if (currentNum > availableSoldiers) {
					System.err.println("Invalid number of soldiers!");
				}
			}
			placedSoldiers += currentNum;
			// Add the soldiers to the action and adjust the territory index.
			receive.addSoldiersTerritory(currentNum, playerTerritories.get(from - 1));
		}

		return receive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agents.PlayerAgentBehaviours#atack(logic.Board)
	 */
	@Override
	public AtackAction atack(Board b) {
		ArrayList<String> playerTerritories = b.getReadyPlayerTerritories(myAgent
				.getLocalName());

		System.err.println("0 - Don't attack");
		for (int i = 0; i < playerTerritories.size(); i++) {
			System.err.println(i + 1 + " - "
					+ b.getTerritory(playerTerritories.get(i)).getName()
					+ " ( "
					+ b.getTerritory(playerTerritories.get(i)).getNumSoldiers()
					+ " )");
		}

		System.err.println("Enter the territory from which to attack:");
		int from = reader.nextInt();
		if (from == 0) {
			return new DontAtackAction();
		}
		from -= 1; // Adjust index from user input.

		// Choose an adjacent territory to attack.
		Territory terr = b.getTerritory(playerTerritories.get(from));
		ArrayList<Territory> adjacentTerritories = b.getEnemyAdjacents(terr,
				myAgent.getLocalName());
		System.err.println("Enter the territory to attack:");
		for (int i = 0; i < adjacentTerritories.size(); i++) {
			System.err.println(i + 1 + " - "
					+ adjacentTerritories.get(i).getName() + " ( "
					+ adjacentTerritories.get(i).getNumSoldiers() + " )");
		}
		int to = reader.nextInt();
		to -= 1;
		// Return an attack action with (from,to)
		return new PerformAtackAction(playerTerritories.get(from),
				adjacentTerritories.get(to).getKey());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agents.PlayerAgentBehaviours#fortify(logic.Board)
	 */
	@Override
	public FortifyAction fortify(Board b) {

		ArrayList<String> playerTerritories = b.getPlayerTerritoriesFortifyFrom(myAgent.getLocalName());

		System.err.println("0 - Don't fortify");

		for (int i = 0; i < playerTerritories.size(); i++) {
			System.err.println(i + 1 + " - "
					+ b.getTerritory(playerTerritories.get(i)).getName()
					+ " ( "
					+ b.getTerritory(playerTerritories.get(i)).getNumSoldiers()
					+ " )");
		}

		System.err.println("Enter the territory from which you want to move soldiers:");
		int from = reader.nextInt();

		if (from == 0)
			return new DontFortifyAction();
		
		// Choose an adjacent territory to attack.
		Territory terr = b.getTerritory(playerTerritories.get(from-1));
		ArrayList<Territory> adjacentTerritories = b.getReachables(terr,
				myAgent.getLocalName());
		System.err.println("Enter the territory to move:");
		for (int i = 0; i < adjacentTerritories.size(); i++) {
			System.err.println(i + 1 + " - "
					+ adjacentTerritories.get(i).getName() + " ( "
					+ adjacentTerritories.get(i).getNumSoldiers() + " )");
		}
		int to = reader.nextInt();

		System.err.println("Enter the number of soldiers you want to move:");

		int n = reader.nextInt();

		return new PerformFortificationAction(playerTerritories.get(from - 1),
				adjacentTerritories.get(to - 1).getKey(), n);
	}

}
