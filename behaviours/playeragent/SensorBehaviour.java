package behaviours.playeragent;

import java.io.Serializable;

import actions.Action;
import agents.PlayerAgentBehaviours;

import jade.core.behaviours.SimpleBehaviour;

public class SensorBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852062724207667965L;
	
	private PlayerAgentBehaviours a;
	
	public SensorBehaviour(PlayerAgentBehaviours a){
		super();
		this.a = a;
	}

	public Action respond(){
		String input = "Ola";
		
		Action ret =  new Action(input);
		return ret;
	}
	
	
	@Override
	public void action() {
	}

	@Override
	public boolean done() {
		return false;
	}

	public Action receive(int n) {
		return a.receiveSoldiers(n);
	}

	public Action atack() {
		return a.atack();
	}

	public Action fortify() {
		return a.fortify();
	}

}
