package behaviours.gameagent;

import actions.Action;
import actions.ReceiveAction;
import actions.ValidateAction;
import agents.GameAgent;
import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import logic.Board;

public class PositionSoldiers extends GameAgentFaseBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2942255668390928078L;
	private int n;
	
	public PositionSoldiers(Agent a, AID to){
		super(a,to);
		n=0;
	}

	public PositionSoldiers(Agent a) {
		super(a,null);
		n=0;
	}

	@Override
	public void action() {		
		Board b = ((GameAgent)myAgent).getBoard();
		String agentName = ((GameAgent)myAgent).getAgentNames().get(to);
		
		n = 3 + b.getContinentBonus(agentName);
		
		if(!waiting)
			myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getReceiveMessage(to, n),this));
		
		waiting =true;
	}

	@Override
	public void handleAction(Action a) {
		this.action = a;
		end=true;
		//waiting =false;
	}
	
	@Override
	public int onEnd(){
		if(ValidateAction.validate( n, (ReceiveAction)action, to.getLocalName(), ((GameAgent)myAgent).getBoard())){
			return 1;
		}
		else{
			return 0;
		}
	}

}
