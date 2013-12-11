package agents;

import java.util.ArrayList;

import behaviours.gameagent.AllocateTerritoriesBehaviour;
import behaviours.gameagent.AtackBehaviour;
import behaviours.gameagent.GameFortify;
import behaviours.gameagent.NewRoundsBehaviour;
import behaviours.gameagent.PositionSoldiers;
import behaviours.gameagent.RequestActionBehaviour;

import communication.RequestResponder;

import util.R;
import jade.core.AID;
import jade.core.Agent;
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
	private static final String ALLOCATE = "allocate";

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137655407030225340L;


	ArrayList<AID> players = new ArrayList<AID>();

	int currentRound = 0;
	int currentPlayer = 0;
	int gameStatus = R.GAME_WAITING;
	private Board board;
	
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

		board = new Board();
		
		printHeadMessage("WAITING FOR PLAYERS");

		GameAgentBehaviour fsmBehaviour = new GameAgentBehaviour(this);

		fsmBehaviour
				.registerFirstState(new WaitingForPlayers(5), WAITING_STATE);
		fsmBehaviour
				.registerState(new NewRoundsBehaviour(this, players), ROUND);
		fsmBehaviour.registerState(new AllocateTerritoriesBehaviour(this),
				ALLOCATE);

		fsmBehaviour.registerTransition(WAITING_STATE, ALLOCATE, 1);
		fsmBehaviour.registerDefaultTransition(ALLOCATE, ROUND);

		addBehaviour(fsmBehaviour);

	}

	public ArrayList<AID> getPlayers() {
		return players;
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

		addBehaviour(new RequestActionBehaviour(new PositionSoldiers(this,
				players.get(currentPlayer), 1), players,this));
		addBehaviour(new RequestActionBehaviour(new AtackBehaviour(this,
				players.get(currentPlayer)), players,this));
		addBehaviour(new RequestActionBehaviour(new GameFortify(this,
				players.get(currentPlayer)), players,this));

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
	 * Inner class WaitingForPlayers
	 */
	private class WaitingForPlayers extends SimpleBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6448494715301721314L;
		private int max;
		private boolean added;

		public WaitingForPlayers(int maxPlayers) {
			this.max = maxPlayers;
			added=false;
		}

		@Override
		public void action() {
			if(added)
				return;
			addBehaviour(new RequestResponder(myAgent,
					RequestResponder.getMessageTemplate(), players, max));
			
			added=true;
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

	public Board getBoard() {
		return board;
	}
}
