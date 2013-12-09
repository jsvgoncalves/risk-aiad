package behaviours.gameagent;

import java.util.ArrayList;
import java.util.Hashtable;

import actions.PerformAtackAction;
import actions.PerformFortificationAction;
import actions.ReceiveAction;
import util.R;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import logic.Board;

public class MakeActionBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828003215072330114L;

	private GameAgentFaseBehaviour b;
	private FinalBehaviour f;
	private ArrayList<AID> p;

	public MakeActionBehaviour(GameAgentFaseBehaviour behaviour, FinalBehaviour fin) {
		this.b = behaviour;
		this.f = fin;
		p = fin.getPlayers();
	}
	
	public void changedBoard(){
		f.setChanged();
	}

	@Override
	public void action() {
		switch (b.getAction().getClass().getName()) {
		case R.RECEIVE_ACTION:
			receiveAction();
			break;
		case R.DONT_FORTIFY:
			dontFortify();
			break;
		case R.PERFORM_FORTIFICATION:
			fortifyAction();
			break;
		case R.PERFORM_ATACK:
			atackAction();
			break;
		case R.DONT_ATACK:
			dontAtack();
			break;
		}
	}

	private void dontAtack() {
		System.out.println("Don't atack");
	}

	private void atackAction() {
		myAgent.addBehaviour(new AtackAndContinueFSM(myAgent, b.to,
				(PerformAtackAction) b.getAction(),p));
	}

	private void fortifyAction() {
		PerformFortificationAction action = (PerformFortificationAction) b
				.getAction();

		Board.getInstance().getTerritory(action.getFrom())
				.removeSoldiers(action.getN());
		Board.getInstance().getTerritory(action.getFrom())
				.addSoldiers(action.getN());
	}

	private void dontFortify() {
		System.out.println("Don't fortify");
	}

	private void receiveAction() {
		ReceiveAction action = (ReceiveAction) b.getAction();
		Hashtable<String, Integer> soldiers = action.getSoldiersByTerritory();

		for (String terr : soldiers.keySet()) {
			Board.getInstance().getTerritory(terr)
					.addSoldiers(soldiers.get(terr));
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
