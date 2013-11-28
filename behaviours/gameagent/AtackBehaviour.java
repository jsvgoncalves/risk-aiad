package behaviours.gameagent;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class AtackBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2042356053011233963L;
	private AID to;

	public AtackBehaviour(Agent a, AID to){
		super(a);
		this.to = to;
	}
	
	@Override
	public void action() {
		myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getAtackMessage(to)));
	}

	@Override
	public boolean done() {
		return true;
	}

}
