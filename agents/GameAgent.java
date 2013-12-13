package agents;

import gui.BoardGUI;
import gui.ObserverGUI;

import java.util.ArrayList;
import java.util.HashMap;

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
	private ArrayList<ObserverGUI> listeners = new ArrayList<ObserverGUI>();
	//Maps AID -> agentName
	private HashMap<AID, String> agentName = new HashMap<AID,String>();
	//
	private boolean closed;
	
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
		closed=false;
		
		printHeadMessage("WAITING FOR PLAYERS");

		GameAgentBehaviour fsmBehaviour = new GameAgentBehaviour(this);

		fsmBehaviour.registerFirstState(new WaitingForPlayers(5), WAITING_STATE);
		fsmBehaviour.registerState(new AllocateTerritoriesBehaviour(this), ALLOCATE);
		fsmBehaviour.registerLastState(new NewRoundsBehaviour(this, players), ROUND);

		fsmBehaviour.registerTransition(WAITING_STATE, ALLOCATE, 1);
		fsmBehaviour.registerDefaultTransition(ALLOCATE, ROUND);

		addBehaviour(fsmBehaviour);

	}

	public void close(){
		closed=true;
	}
	
	public boolean isClosed(){
		return closed;
	}
	
	public ArrayList<AID> getPlayers() {
		return players;
	}
	
	public HashMap<AID, String> getAgentNames(){
		return agentName;
	}

	private void printHeadMessage(String message) {
		System.out.println("#############");
		System.out.println(message);
		System.out.println("#############");
	}
	
	/** 
	 * Notifies the listeners that a turn has ended
	 */
	public void notifyTurnEnded() {

		for (ObserverGUI listener : listeners) {
			listener.notifyTurnEnded();
		}
		
	}
	
	/** 
	 * Notifies the listeners that the game has started
	 */
	public void notifyGameStarted() {

		for (ObserverGUI listener : listeners) {
			listener.notifyGameStarted();
		}
		
	}
	
	public void addListener(ObserverGUI boardGUI) {
		this.listeners.add(boardGUI);
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
					RequestResponder.getMessageTemplate(), players,agentName, max));
			
			added=true;
		}

		@Override
		public boolean done() {
			return players.size() >= max;
		}

		@Override
		public int onEnd() {
			closed = true;
			return 1;
		}

	}

	public Board getBoard() {
		return board;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	
	public int getCurrentRound() {
		return currentRound;
	}
}
