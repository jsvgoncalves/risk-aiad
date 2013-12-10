package behaviours.gameagent;


import actions.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public abstract class GameAgentFaseBehaviour extends SimpleBehaviour {

	protected AID to;
	protected boolean end, waiting;
	protected Action action;
	
	protected GameAgentFaseBehaviour(Agent a, AID to){
		super(a);
		this.to = to;
		end=false;
		action=null;
		waiting = false;
	}
	
	public void reset(){
		end = false;
		action = null;
		waiting =false;
	}
	
	public Action getAction(){
		return action;
	}
	
	public abstract void handleAction(Action a);
	
	@Override
	public boolean done() {
		return end;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7714268619294335392L;

	public void setPlayer(AID aid) {
		to = aid;
	}

}
