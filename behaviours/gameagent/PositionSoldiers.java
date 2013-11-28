package behaviours.gameagent;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class PositionSoldiers extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2942255668390928078L;
	
	private AID to;
	private int n,i;
	
	public PositionSoldiers(Agent a, AID to, int n){
		super(a);
		this.to = to;
		this.n = n;
		i=0;
	}

	@Override
	public void action() {
		myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getReceiveMessage(to, n)));
	}

	@Override
	public boolean done() {
		return true;
	}

}
