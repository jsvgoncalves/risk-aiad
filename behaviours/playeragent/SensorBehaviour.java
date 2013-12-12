package behaviours.playeragent;


import perceptions.Perception;
import actions.Action;
import actions.DontFortifyAction;
import agents.PlayerAgent;
import agents.PlayerAgentBehaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import logic.Board;

public class SensorBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852062724207667965L;
	
	private PlayerAgentBehaviours a;
	private PlayerAgent agent;
	
	public SensorBehaviour(Agent my,PlayerAgentBehaviours a){
		super(my);
		this.a = a;
		agent = (PlayerAgent)myAgent;
	}

	public Action respond(){
		Action ret =  new DontFortifyAction();
		return ret;
	}
	
	
	@Override
	public void action() {
	}

	@Override
	public boolean done() {
		return false;
	}

	public Action receive(int n) {
		((PlayerAgent)myAgent).newRound();
		return a.receiveSoldiers(agent.getBoard(),n);
	}

	public Action atack() {
		return a.atack(agent.getBoard());
	}

	public Action fortify() {
		return a.fortify(agent.getBoard());
	}

	public void updateBoard(Board board) {
		agent.setBoard(board);
	}

	public void addPerception(Perception p) {
		agent.addPerception(p);
	}

}
