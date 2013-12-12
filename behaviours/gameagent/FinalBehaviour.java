package behaviours.gameagent;

import java.util.ArrayList;

import perceptions.Perception;

import agents.GameAgent;

import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class FinalBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1109933473079103804L;

	private boolean changed,aux;
	private ArrayList<AID> p;
	private Perception perception;
	
	public FinalBehaviour(ArrayList<AID> players, Agent a){
		super(a);
		changed=false;
		this.p = players;
		perception=null;
	}

	public FinalBehaviour(ArrayList<AID> players, boolean b, Agent a) {
		super(a);
		this.p = players;
		aux=b;
		perception=null;
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
			
			if(perception!=null)
				myAgent.addBehaviour(new RequestInitiator(myAgent, RequestInitiator.getPerceptionMessage(p, perception)));
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

	public void setChanged(Perception perception) {
		changed=true;
		this.perception=perception;
	}

}
