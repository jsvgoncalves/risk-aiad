package behaviours.gameagent;

import java.util.ArrayList;

import agents.GameAgent;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import logic.Board;

public class FinalBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1109933473079103804L;

	private boolean changed,aux;
	private ArrayList<AID> p;
	
	public FinalBehaviour(ArrayList<AID> players, Agent a){
		super(a);
		changed=false;
		this.p = players;
	}

	public FinalBehaviour(ArrayList<AID> players, boolean b, Agent a) {
		super(a);
		this.p = players;
		aux=b;
	}

	@Override
	public void action() {
		if(changed){
			ACLMessage m = RequestInitiator.getChangedBoardMessage(p, ((GameAgent)myAgent).getBoard());
			if( m.getPerformative() == ACLMessage.FAILURE){
				System.err.println(m.getContent());
				return;
			}
			
			myAgent.addBehaviour(new RequestInitiator(myAgent, m));
		}
		if(aux)
			System.out.println("Final");
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
