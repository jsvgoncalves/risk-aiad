package agents;

import logic.Board;
import actions.AtackAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public abstract class PlayerAgentBehaviours {
	
	protected PlayerAgent myAgent;
	
	public void setPlayerAgent(PlayerAgent agent){
		myAgent = agent;
	}
	
	public abstract ReceiveAction receiveSoldiers(Board b, int n);
	
	public abstract AtackAction atack(Board b);
	
	public abstract FortifyAction fortify(Board b);
}
