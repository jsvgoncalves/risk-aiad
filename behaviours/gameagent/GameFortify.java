package behaviours.gameagent;

import util.R;
import actions.Action;
import actions.PerformFortificationAction;
import actions.ValidateAction;
import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;

public class GameFortify extends GameAgentFaseBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841088529800033879L;

	public GameFortify(Agent a, AID to){
		super(a,to);
	}

	public GameFortify(Agent a) {
		super(a,null);
	}

	@Override
	public void action() {
		if(!waiting)
			myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getFortifyMessage(to),this));
		
		waiting =true;
	}
	
	@Override
	public void handleAction(Action a) {
		this.action = a;
		end = true;
		//waiting =false;
	}
	
	@Override
	public int onEnd() {
		if (action.getClass().getName().equals(R.DONT_FORTIFY)) {
			return 1;
		}

		if (action.getClass().getName().equals(R.PERFORM_FORTIFICATION)
				&& ValidateAction.validate((PerformFortificationAction) action, to.getLocalName())) {
			return 1;
		} else {
			return 0;
		}
	}

}
