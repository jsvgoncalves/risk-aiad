package behaviours.gameagent;

import java.util.ArrayList;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import logic.Board;

public class FinalBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1109933473079103804L;

	private boolean changed;
	private ArrayList<AID> p;
	
	public FinalBehaviour(ArrayList<AID> players){
		changed=false;
		this.p = players;
	}

	@Override
	public void action() {
		if(changed){
			ACLMessage m = RequestInitiator.getChangedBoardMessage(p, Board.getInstance());
			if( m.getPerformative() == ACLMessage.FAILURE)
				return;
			
			myAgent.addBehaviour(new RequestInitiator(myAgent, m));
		}
		System.out.println(changed);
	}

	@Override
	public boolean done() {
		return true;
	}

	public void setChanged() {
		changed = true;
	}

	public ArrayList<AID> getPlayers() {
		return p;
	}

}
