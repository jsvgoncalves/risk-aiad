package behaviours.gameagent;


import jade.core.behaviours.SimpleBehaviour;

public class MakeActionBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828003215072330114L;
	
	private GameAgentFaseBehaviour b;
	
	public MakeActionBehaviour(GameAgentFaseBehaviour behaviour){
		this.b=behaviour;
	}

	@Override
	public void action() {
		//TODO enforce action
		System.out.println(b.getAction().getS());
	}

	@Override
	public boolean done() {
		return true;
	}

}
