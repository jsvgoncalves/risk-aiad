package agents;

import util.R;
import behaviours.playeragent.JoinGameBehaviour;
import behaviours.playeragent.SensorBehaviour;
import communication.PlayRequestResponder;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class PlayerAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5347118089332895954L;
	
	private PlayRequestResponder responder;
	private PlayerAgentBehaviours a;
	
	public PlayerAgent(PlayerAgentBehaviours a){
		this.a = a;
	}
	
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
		
		responder = new PlayRequestResponder(this, PlayRequestResponder.getMessageTemplate());
		SensorBehaviour sensor = new SensorBehaviour(this,a);
		responder.setSensor(sensor);
		addBehaviour(responder);
		addBehaviour(new JoinGameBehaviour(this));
		addBehaviour(sensor);
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
}
