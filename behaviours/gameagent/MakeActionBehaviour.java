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

	public static final int FINAL = 0;
	public static final int CONT = 1;

	private GameAgentFaseBehaviour b;
	private FinalBehaviour f;
	private ArrayList<AID> p;
	AtackAndContinueFSM ata;

	public MakeActionBehaviour(GameAgentFaseBehaviour behaviour,
			FinalBehaviour fin) {
		this.b = behaviour;
		this.f = fin;
		p = fin.getPlayers();
		ata=null;
	}

	@Override
	public void action() {
		
		if(ata!= null){
			return;
		}
			
		
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
		System.out.println(((PerformAtackAction) b.getAction()).getFrom()
				+ " atacks " + ((PerformAtackAction) b.getAction()).getTo());

		ata = new AtackAndContinueFSM(myAgent, b.to,
				(PerformAtackAction) b.getAction(), p);
		
		myAgent.addBehaviour(ata);
	}

	private void fortifyAction() {
		System.out.println("Fortify");
		PerformFortificationAction action = (PerformFortificationAction) b
				.getAction();

		Board.getInstance().getTerritory(action.getFrom())
				.removeSoldiers(action.getN());
		Board.getInstance().getTerritory(action.getFrom())
				.addSoldiers(action.getN());
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
			Board.getInstance().getTerritory(terr)
					.addSoldiers(soldiers.get(terr));
		}
		f.setChanged();
	}

	@Override
	public boolean done() {
		if(ata!= null){
			if( ata.e )
				f.setChanged();
			
			return ata.e;
		}
		else
			return true;
	}

	@Override
	public int onEnd() {

		if (b.getAction().getClass().equals(R.PERFORM_ATACK))
			return CONT;
		else
			return FINAL;
	}

	public void resetAtack() {
		ata=null;
	}

}
