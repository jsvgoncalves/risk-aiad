package behaviours.gameagent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import actions.Action;
import actions.PerformAtackAction;
import actions.PerformFortificationAction;
import actions.ReceiveAction;
import agents.GameAgent;
import util.R;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import logic.Board;

public class MakeActionBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828003215072330114L;

	public static final int FINAL = 0;
	public static final int CONT = 1;

	private GameAgentFaseBehaviour b;
	private FinalBehaviour f;
	private ArrayList<AID> p;
	private Board bo;

	public MakeActionBehaviour(GameAgentFaseBehaviour behaviour,
			FinalBehaviour fin, Agent a) {
		super(a);
		this.b = behaviour;
		this.f = fin;
		p = fin.getPlayers();
		bo = ((GameAgent) myAgent).getBoard();
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
		case R.CONTINUE:
			System.out.println("Make continue");
			break;
		}
	}

	private void dontAtack() {
		System.out.println("Don't atack");
	}

	private void atackAction() {

		PerformAtackAction action = (PerformAtackAction) b.getAction();

		System.out.println(bo.getTerritory(action.getFrom()).getName()
				+ " atacks " + bo.getTerritory(action.getTo()).getName());

		// ata = new AtackAndContinueFSM(myAgent, b.to,
		// (PerformAtackAction) b.getAction(), p);

		// myAgent.addBehaviour(ata);

		Random r = new Random();
		int p1 = 0;
		int p2 = 0;

		p1 = r.nextInt(12) + 1;
		p2 = r.nextInt(12) + 1;

		System.out.println("Player 1 rolled: " + p1);
		System.out.println("Player 2 rolled: " + p2);
		System.out.println();

		if (p1 > p2) {
			bo.getTerritory(action.getTo()).removeSoldiers(1);
			//Verifica se o territorio e conquistado no metodo conquer
			bo.conquer(action.getFrom(), action.getTo());
		} else if (p2 >= p1) {
			bo.getTerritory(action.getFrom()).removeSoldiers(1);
		}

	}

	private void fortifyAction() {
		System.out.println("Fortify");
		PerformFortificationAction action = (PerformFortificationAction) b
				.getAction();

		bo.getTerritory(action.getFrom()).removeSoldiers(action.getN());
		bo.getTerritory(action.getFrom()).addSoldiers(action.getN());
		f.setChanged();
	}

	private void dontFortify() {
		System.out.println("Don't fortify");
	}

	private void receiveAction() {
		System.out.println("Receive");
		ReceiveAction action = (ReceiveAction) b.getAction();
		Hashtable<String, Integer> soldiers = action.getSoldiersByTerritory();

		for (String terr : soldiers.keySet()) {
			bo.getTerritory(terr).addSoldiers(soldiers.get(terr));
		}
		f.setChanged();
	}

	@Override
	public boolean done() {
		return true;
	}

	@Override
	public int onEnd() {

		if (b.getAction().getClass().getName().equals(R.PERFORM_ATACK)) {
			b.reset();
			return CONT;
		} else {
			return FINAL;
		}

	}

}
