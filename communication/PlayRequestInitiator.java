package communication;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class PlayRequestInitiator extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7496756007560381872L;

	public PlayRequestInitiator(Agent a, ACLMessage msg) {
		super(a, msg);
	}
	
	/*
	 * Use this static method to construct the msg in the constructor
	 */
	public static ACLMessage getRequestMessage(String to){
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.addReceiver(new AID(to,AID.ISLOCALNAME));
		
		return request;
	}
	
	protected void handleInform(ACLMessage inform){
		//TODO Do something with inform
		System.out.println("Protocol Finished! Do something with " + inform);
	}

}
