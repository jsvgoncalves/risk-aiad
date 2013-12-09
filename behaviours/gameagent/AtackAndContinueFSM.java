package behaviours.gameagent;

import java.util.ArrayList;
import java.util.Random;

import communication.RequestInitiator;

import actions.Action;
import actions.ContinueAction;
import actions.PerformAtackAction;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import logic.Board;

public class AtackAndContinueFSM extends FSMBehaviour {

	private static final String FINAL = "Final";
	private static final String REQUEST = "Request";
	private static final String ATACK = "Atack";
	private static final int CONTINUE = 1;
	private static final int END = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 467195171770227699L;

	private ContinueAction c;

	public AtackAndContinueFSM(Agent a, AID to, PerformAtackAction action, ArrayList<AID> players) {
		super(a);
		c = new ContinueAction(true);

		registerFirstState(new PerformAtack(action), ATACK);
		registerState(new RequestActionBehaviour(new ContinueBehaviour(myAgent,
				to),players), REQUEST);
		registerLastState(new FinalBehaviour(players), FINAL);

		registerTransition(ATACK, FINAL, END);
		registerTransition(ATACK, REQUEST, CONTINUE);
		registerDefaultTransition(REQUEST, ATACK);
	}

	private class ContinueBehaviour extends GameAgentFaseBehaviour {

		protected ContinueBehaviour(Agent a, AID to) {
			super(a, to);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 4038928887592038291L;

		@Override
		public void handleAction(Action a) {
			System.out.println("Continue");
			c = (ContinueAction) a;
			this.action = a;
			end = true;
		}

		@Override
		public void action() {
			myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator
					.getContinueMessage(to), this));
		}

	}

	private class PerformAtack extends SimpleBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9167059621407144090L;
		private PerformAtackAction action;

		public PerformAtack(PerformAtackAction action) {
			this.action = action;
		}

		@Override
		public void action() {
			if (!c.willContinue())
				return;

			Random r = new Random();
			int p1 = 0;
			int p2 = 0;

			p1 = r.nextInt(12) + 1;
			p2 = r.nextInt(12) + 1;
			
			if (p1 > p2) {
				Board.getInstance().getTerritory(action.getTo())
						.removeSoldiers(1);
			} else if (p2 >= p1) {
				Board.getInstance().getTerritory(action.getFrom())
						.removeSoldiers(1);
			}
		}

		@Override
		public boolean done() {
			return true;
		}

		@Override
		public int onEnd() {
			if (Board.getInstance().getTerritory(action.getFrom())
					.getNumSoldiers() == 1
					|| Board.getInstance().getTerritory(action.getTo())
							.getNumSoldiers() == 0 || !c.willContinue())
				return END; // FIM
			else
				return CONTINUE; // Pode continuar
		}

	}
}
