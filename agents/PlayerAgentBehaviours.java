package agents;

import logic.Board;
import actions.AtackAction;
import actions.ContinueAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public abstract class PlayerAgentBehaviours {
	
	public abstract ReceiveAction receiveSoldiers(Board b, int n);
	
	public abstract AtackAction atack(Board b);
	
	public abstract ContinueAction continueAtack(Board b,boolean wonLast, int mySoldiers, int hisSoldiers);
	
	public abstract FortifyAction fortify(Board b);
}
