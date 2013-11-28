package agents;

import actions.Action;

public class RandomAgent extends PlayerAgentBehaviours {

	@Override
	public Action receiveSoldiers(int n) {
		return new Action("Received " + n + " soldiers");
	}

	@Override
	public Action atack() {
		return new Action("Going to atack");
	}

	@Override
	public Action continueAtack(boolean wonLast, int mySoldiers, int hisSoldiers) {
		return new Action("Continuing atack");
	}

	@Override
	public Action fortify() {
		return new Action("Fortifiying");
	}

}
