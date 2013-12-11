package behaviours.gameagent;

import java.util.ArrayList;
import java.util.Random;

import util.R;

import communication.RequestInitiator;

import actions.Action;
import actions.ContinueAction;
import actions.PerformAtackAction;
import agents.GameAgent;
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

	public boolean e;
	public ContinueBehaviour cb;
	public RequestActionBehaviour rc;

	public AtackAndContinueFSM(Agent a, AID to, PerformAtackAction action,
			ArrayList<AID> players) {
		super(a);
		c = new ContinueAction(true);
		e = false;
		cb = new ContinueBehaviour(myAgent, to);
		rc = new RequestActionBehaviour(cb, players,a);

		registerFirstState(new PerformAtack(action,myAgent), ATACK);
		registerState(rc, REQUEST);
		registerLastState(new FinalBehaviour(players,myAgent), FINAL);

		registerTransition(ATACK, FINAL, END);
		registerTransition(ATACK, REQUEST, CONTINUE);
		registerDefaultTransition(REQUEST, ATACK);
	}

	@Override
	public int onEnd() {
		e = true;
		return 1;
	}

	private class ContinueBehaviour extends GameAgentFaseBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4038928887592038291L;

		public boolean wonLast;
		public int mySoldiers;
		public int hisSoldiers;

		protected ContinueBehaviour(Agent a, AID to) {
			super(a, to);
			wonLast = false;
			mySoldiers = 0;
			hisSoldiers = 0;
		}

		@Override
		public void handleAction(Action a) {
			c = (ContinueAction) a;
			this.action = a;
			end = true;
			// waiting = false;
		}

		@Override
		public void action() {
			if (!waiting)
				myAgent.addBehaviour(new RequestInitiator(myAgent,
						RequestInitiator.getContinueMessage(to, wonLast,
								mySoldiers, hisSoldiers), this));

			waiting = true;
		}

		@Override
		public int onEnd() {

			if (action.getClass().getName().equals(R.CONTINUE_ACTION)) {
				return 1;
			} else
				return 0;
		}

	}

	private class PerformAtack extends SimpleBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = -9167059621407144090L;
		private PerformAtackAction action;
		private Board b;
		
		public PerformAtack(PerformAtackAction action, Agent a) {
			super(a);
			this.action = action;
			b = ((GameAgent)a).getBoard();
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

			System.out.println("Player 1 rolled: " + p1);
			System.out.println("Player 2 rolled: " + p2);
			System.out.println();

			if (p1 > p2) {
				b.getTerritory(action.getTo())
						.removeSoldiers(1);
				cb.wonLast = true;
			} else if (p2 >= p1) {
				b.getTerritory(action.getFrom())
						.removeSoldiers(1);
			}

			cb.mySoldiers = b.getTerritory(action.getFrom())
					.getNumSoldiers();
			cb.hisSoldiers = b.getTerritory(action.getTo())
					.getNumSoldiers();
		}

		@Override
		public boolean done() {
			return true;
		}

		@Override
		public int onEnd() {
			if (b.getTerritory(action.getFrom())
					.getNumSoldiers() == 1
					|| b.getTerritory(action.getTo())
							.getNumSoldiers() == 0) {
				
				b.conquer(action.getFrom(),action.getTo());
				return END; // FIM
			}
			if (!c.willContinue()) {
				return END;
			} else {
				rc.reset();
				rc.resetCount();
				return CONTINUE; // Pode continuar
			}
		}

	}
}
