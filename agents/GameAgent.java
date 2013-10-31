package agents;

import java.util.ArrayList;
import java.util.Collections;

import communication.PlayRequestInitiator;

import util.R;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
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

	Board b;

	protected void setup() {
		
		printHeadMessage("WAITING FOR PLAYERS");
		
		GameAgentBehaviour fsmBehaviour = new GameAgentBehaviour(this);
		
		fsmBehaviour.registerFirstState(getSubscriptionBehaviour(), WAITING_STATE);
		fsmBehaviour.registerState(new RoundsBehaviour(this), ROUND);
		
		fsmBehaviour.registerTransition(WAITING_STATE, ROUND,1);
		
		
		addBehaviour(fsmBehaviour);

	}

	private void printHeadMessage(String message) {
		System.out.println("#############");
		System.out.println(message);
		System.out.println("#############");
	}

	private SubscriptionBehaviour getSubscriptionBehaviour() {
		// Build the description used as template for the subscription
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription templateSd = new ServiceDescription();
		templateSd.setType("player");
		template.addServices(templateSd);

		SearchConstraints sc = new SearchConstraints();
		// We want to receive 10 results at most
		//sc.setMaxResults(new Long(10));

		return new SubscriptionBehaviour(this, DFService.createSubscriptionMessage(this, getDefaultDF(), template, sc));

	}

	protected void receivedPlayers() {
		// If there are the minimum players, start the game
		if(players.size() >= R.MIN_PLAYERS) {
			startGame();
		}
	}

	protected void startGame() {
		if(gameStatus != R.GAME_WAITING) {
			return;
		}
		gameStatus = R.GAME_LAUNCHING;
		// Shuffles the players to generate random playing order
		printHeadMessage("SHUFFLING PLAYERS");

		Collections.shuffle(players);

		prettyPrintOrder();

		printHeadMessage("GAME STARTING");

		b = Board.getInstance();
		addBehaviour(new RoundsBehaviour(this));
	}

	/**
	 * Implements a game turn.
	 */
	protected void gameTurn() {

		if(currentPlayer > players.size() - 1) {
			currentPlayer = 0;
		}

		/*ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.addReceiver(players.get(currentPlayer).getName());
		msg.setContent("play");
		send(msg);*/

		addBehaviour(new PlayRequestInitiator(this, PlayRequestInitiator.getRequestMessage(players.get(currentPlayer))));
		
		currentPlayer++;
	}

	private void prettyPrintOrder() {
		//System.out.println(players.get(0).getName().toString() + " gets to go first!");

		for (AID player : players) {
			System.out.println(player.getLocalName() + "");
		}

	}
	
	private class GameAgentBehaviour extends FSMBehaviour{

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
			// For each agent, send him a message to play and wait response or timeout.
			System.out.println("Round " + currentRound++);
			gameTurn();
			block();
		}

	}    // END of inner class RoundsBehaviour

	/**
	 * Inner class SubscriptionBehaviour
	 */
	private class SubscriptionBehaviour extends SubscriptionInitiator {

		/**
		 * 
		 */
		private static final long serialVersionUID = -214667283251111849L;

		public SubscriptionBehaviour(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		protected void handleInform(ACLMessage inform) {
			
			try {
				DFAgentDescription[] results = DFService.decodeNotification(inform.getContent());
				if (results.length > 0) {
					for (int i = 0; i < results.length; ++i) {
						players.add(results[i].getName());
					}
				}
				// Notifies the game that new players are ready.
				receivedPlayers();
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
		
		public int onEnd(){
			return 1;
		}

	} // END of inner class SubscriptionBehaviour
}
