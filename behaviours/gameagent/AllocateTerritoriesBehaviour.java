package behaviours.gameagent;

import agents.GameAgent;
import jade.core.behaviours.SimpleBehaviour;
import logic.Board;

public class AllocateTerritoriesBehaviour extends SimpleBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308825758539849957L;
	
	private GameAgent a;
	
	public AllocateTerritoriesBehaviour(GameAgent agent){
		super(agent);
		a=agent;
	}

	@Override
	public void action() {
		Board.getInstance().allocateRandomTerritories(a.getPlayers());
	}

	@Override
	public boolean done() {
		return true;
	}

}
