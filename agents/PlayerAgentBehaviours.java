package agents;

import actions.Action;

public abstract class PlayerAgentBehaviours {
	
	public abstract Action receiveSoldiers(int n);
	
	public abstract Action atack();
	
	public abstract Action continueAtack(boolean wonLast, int mySoldiers, int hisSoldiers);
	
	public abstract Action fortify();
}
