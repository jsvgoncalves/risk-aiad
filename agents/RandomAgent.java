package agents;

import logic.Board;
import actions.AtackAction;
import actions.ContinueAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public class RandomAgent extends PlayerAgentBehaviours {

	public RandomAgent(PlayerAgent agent) {
		super(agent);
	}
	
	@Override
	public ReceiveAction receiveSoldiers(Board b,int n) {
		return new ReceiveAction();
	}

	@Override
	public AtackAction atack(Board b) {
		return new DontAtackAction();
	}

	@Override
	public ContinueAction continueAtack(Board b,boolean wonLast, int mySoldiers, int hisSoldiers) {
		return new ContinueAction(false);
	}

	@Override
	public FortifyAction fortify(Board b) {
		return new DontFortifyAction();
	}

}
