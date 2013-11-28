package agents;

import util.R;
import behaviours.playeragent.JoinGameBehaviour;
import behaviours.playeragent.SensorBehaviour;
import communication.PlayRequestResponder;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class PlayerAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5347118089332895954L;

	private static final String SENSOR = "Sensor"; 
	
	private PlayRequestResponder responder;
	
	protected void setup() {
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType(R.PLAYER_AGENT);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(getLocalName() + " reporting in.");
		
		
		
		PlayerAgentBehaviour behaviour = new PlayerAgentBehaviour(this);
		SensorBehaviour sensor = new SensorBehaviour();
		responder = new PlayRequestResponder(this, PlayRequestResponder.getMessageTemplate(), sensor);
		
		behaviour.registerFirstState(new JoinGameBehaviour(this), R.JOIN);
		behaviour.registerState(sensor, SENSOR);
		behaviour.registerTransition(R.JOIN, SENSOR,1);
		
		addBehaviour(responder);
		addBehaviour(behaviour);
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inner class PlayerAgentBehaviour
	 */
	private class PlayerAgentBehaviour extends FSMBehaviour{

		/**
		 * 
		 */
		private static final long serialVersionUID = 591976149860561195L;
		
		public PlayerAgentBehaviour(Agent agent){
			super(agent);
		}
		
	}
}
