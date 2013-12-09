package behaviours.gameagent;

import java.util.ArrayList;

import actions.Action;
import actions.ReceiveAction;
import actions.ValidateAction;
import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;

public class PositionSoldiers extends GameAgentFaseBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2942255668390928078L;

	private int n;
	
	public PositionSoldiers(Agent a, AID to, int n){
		super(a,to);
		this.n = n;
	}

	public PositionSoldiers(Agent a, int n) {
		super(a,null);
		this.n = n;
	}

	@Override
	public void action() {
		myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getReceiveMessage(to, n),this));
	}

	@Override
	public void handleAction(Action a) {
		this.action = a;
		end=true;
	}
	
	@Override
	public int onEnd(){
		if(ValidateAction.validate( n, (ReceiveAction)action, to.getLocalName())){
			return 1;
		}
		else{
			return 0;
		}
	}

}
