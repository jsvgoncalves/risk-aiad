package behaviours.gameagent;

import java.util.ArrayList;

import communication.RequestInitiator;

import agents.GameAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class UpdateRoundBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4252143295214188705L;

	private int currentRound, currentPlayer;
	private ArrayList<AID> players;
	private RequestActionBehaviour position, atack, fortify;

	public UpdateRoundBehaviour(Agent a, ArrayList<AID> players,
			RequestActionBehaviour position, RequestActionBehaviour atack,
			RequestActionBehaviour fortify) {
		super(a);
		currentRound = 0;
		currentPlayer = 0;
		this.players = players;
		this.position = position;
		this.atack = atack;
		this.fortify = fortify;
	}

	@Override
	public void action() {

		GameAgent agent = (GameAgent) myAgent;

		myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator
				.getChangedBoardMessage(players, agent.getBoard())));

		if (agent.getBoard().getPlayerTerritories(
						agent.getAgentNames().get(players.get(currentPlayer))).size() == 0){
			players.remove(currentPlayer);
			if( currentPlayer >= players.size())
				currentPlayer=0;
		}

		currentRound++;
		System.out.println("Round " + currentRound);
		System.out.println("Current Player: " + currentPlayer);
		System.out.println("Size: " + players.size());

		position.setPlayer(players.get(currentPlayer));
		position.reset();
		position.resetCount();
		atack.setPlayer(players.get(currentPlayer));
		atack.reset();
		atack.resetCount();
		fortify.setPlayer(players.get(currentPlayer));
		fortify.reset();
		fortify.resetCount();

		currentPlayer++;
		if (currentPlayer >= players.size()) {
			currentPlayer = 0;
		}
		agent.notifyTurnEnded();
		agent.setCurrentRound(currentRound);
	}

	@Override
	public boolean done() {
		return true;
	}

}
