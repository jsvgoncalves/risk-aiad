package behaviours.gameagent;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class GameFortify extends SimpleBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841088529800033879L;
	private AID to;

	public GameFortify(Agent a, AID to){
		super(a);
		this.to = to;
	}

	@Override
	public void action() {
		myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getFortifyMessage(to)));
	}

	@Override
	public boolean done() {
		return true;
	}

}
