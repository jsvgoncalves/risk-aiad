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
	private Board b; // FIXME! isto provavelmente vai ser passado na comunicação? Mudar isto.
	
	public SensorBehaviour(Agent my,PlayerAgentBehaviours a){
		super(my);
		this.a = a;
		
		b = ((PlayerAgent)my).getBoard();
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
		return a.receiveSoldiers(b,n);
	}

	public Action atack() {
		return a.atack(b);
	}

	public Action fortify() {
		return a.fortify(b);
	}

	public Action cont(boolean wonLast, int mySoldiers, int hisSoldiers) {
		return a.continueAtack(b, wonLast, mySoldiers, hisSoldiers);
	}

	public void updateBoard(Board board) {
		b = board;
	}

	public void addPerception(Perception p) {
	
	}

}
