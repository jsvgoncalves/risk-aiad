package agents;

import actions.AtackAction;
import actions.ContinueAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public abstract class PlayerAgentBehaviours {
	
	public abstract ReceiveAction receiveSoldiers(int n);
	
	public abstract AtackAction atack();
	
	public abstract ContinueAction continueAtack(boolean wonLast, int mySoldiers, int hisSoldiers);
	
	public abstract FortifyAction fortify();
}
