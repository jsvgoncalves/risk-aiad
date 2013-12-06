package agents;

import logic.Board;
import actions.AtackAction;
import actions.ContinueAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.ReceiveAction;

public class RandomAgent extends PlayerAgentBehaviours {

	@Override
	public ReceiveAction receiveSoldiers(Board b,int n) {
		return new ReceiveAction();
	}

	@Override
	public AtackAction atack(Board b) {
		return new PerformAtackAction("From","To");
	}

	@Override
	public ContinueAction continueAtack(Board b,boolean wonLast, int mySoldiers, int hisSoldiers) {
		return new ContinueAction( true);
	}

	@Override
	public FortifyAction fortify(Board b) {
		return new DontFortifyAction();
	}

}
