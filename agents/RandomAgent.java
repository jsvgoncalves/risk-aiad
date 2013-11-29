package agents;

import actions.AtackAction;
import actions.ContinueAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.ReceiveAction;

public class RandomAgent extends PlayerAgentBehaviours {

	@Override
	public ReceiveAction receiveSoldiers(int n) {
		return new ReceiveAction("Received " + n + " soldiers");
	}

	@Override
	public AtackAction atack() {
		return new PerformAtackAction("Going to atack","From","To");
	}

	@Override
	public ContinueAction continueAtack(boolean wonLast, int mySoldiers, int hisSoldiers) {
		return new ContinueAction("Continuing atack", true);
	}

	@Override
	public FortifyAction fortify() {
		return new DontFortifyAction("Fortifiying");
	}

}
