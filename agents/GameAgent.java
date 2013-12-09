package agents;

import java.util.ArrayList;

import behaviours.gameagent.AtackBehaviour;
import behaviours.gameagent.GameFortify;
import behaviours.gameagent.NewRoundsBehaviour;
import behaviours.gameagent.PositionSoldiers;
import behaviours.gameagent.RequestActionBehaviour;

import communication.RequestResponder;

import test.logic.BoardTerritoryTest;
import util.R;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import logic.Board;

public class GameAgent extends Agent {
	private static final String ROUND = "ROUND";
	private static final String WAITING_STATE = "Waiting for players";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137655407030225340L;

	ArrayList<AID> players = new ArrayList<AID>();

	int currentRound = 0;
	int currentPlayer = 0;
	int gameStatus = R.GAME_WAITING;

	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType(R.GAME_AGENT);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		printHeadMessage("WAITING FOR PLAYERS");

		GameAgentBehaviour fsmBehaviour = new GameAgentBehaviour(this);

		fsmBehaviour
				.registerFirstState(new WaitingForPlayers(5), WAITING_STATE);
		fsmBehaviour.registerState(new NewRoundsBehaviour(this,players), ROUND);

		fsmBehaviour.registerTransition(WAITING_STATE, ROUND, 1);

		addBehaviour(fsmBehaviour);

	}

	private void printHeadMessage(String message) {
		System.out.println("#############");
		System.out.println(message);
		System.out.println("#############");
	}


	/**
	 * Implements a game turn.
	 */
	protected void gameTurn() {

		if (currentPlayer > players.size() - 1) {
			currentPlayer = 0;
		}

		addBehaviour(new RequestActionBehaviour(new PositionSoldiers(this, players.get(currentPlayer), 1),players));
		addBehaviour(new RequestActionBehaviour(new AtackBehaviour(this,players.get(currentPlayer)),players));
		addBehaviour(new RequestActionBehaviour(new GameFortify(this, players.get(currentPlayer)),players));

		currentPlayer++;
	}

	private class GameAgentBehaviour extends FSMBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6646543101529043020L;

		public GameAgentBehaviour(GameAgent gameAgent) {
			super(gameAgent);
		}

	}

	/**
	 * Inner class RoundsBehaviour
	 */
	private class RoundsBehaviour extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2897444297823568576L;

		public RoundsBehaviour(Agent a) {
			super(a);
		}

		@Override
		public void action() {
			// For each agent, send him a message to play and wait response or
			// timeout.
			System.out.println("Round " + currentRound++);
			gameTurn();
			block();
		}

	} // END of inner class RoundsBehaviour

	/**
	 * Inner class WaitingForPlayers
	 */
	private class WaitingForPlayers extends SimpleBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6448494715301721314L;
		private int max;

		public WaitingForPlayers(int maxPlayers) {
			this.max = maxPlayers;
		}

		@Override
		public void action() {
			addBehaviour(new RequestResponder(myAgent,
					RequestResponder.getMessageTemplate(), players, max));
		}

		@Override
		public boolean done() {
			return players.size() >= max;
		}

		@Override
		public int onEnd() {
			return 1;
		}

	}
}
