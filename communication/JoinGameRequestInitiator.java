package communication;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class JoinGameRequestInitiator extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724330788000174384L;
	
	public JoinGameRequestInitiator(Agent a, ACLMessage msg, AID agentId) {
		super(a, msg);
	}
	
	public static ACLMessage getRequestMessage(AID to){
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.addReceiver(to);
		
		return request;
	}
	
	protected void handleInform(ACLMessage inform){
		//TODO Do something with inform
		System.out.println("Joined with success!" + inform);
	}
	
	protected void handleFailure(ACLMessage failure){
		//TODO Do something with failure
		System.out.println("Couldn't join!" + failure);
	}

}
