package communication;

import java.util.ArrayList;

import util.R;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class RequestResponder extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7663285688046592434L;
	
	private ArrayList<AID> p;

	public RequestResponder(Agent a, MessageTemplate mt, ArrayList<AID> players) {
		super(a, mt);
		p = players;
	}
	
	public static MessageTemplate getWaitingMessage(){
		return AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}
	
	protected ACLMessage handleRequest(ACLMessage request){
		
		String content = request.getContent();
		
		switch(content){
			case R.SUBSCRIPTION:
				return handleSubscriptionRequest(request);
		}
		
		ACLMessage error = request.createReply();
		error.setPerformative(ACLMessage.FAILURE);
		error.setContent("Content not valid!");
		
		return error;
	}

	private ACLMessage handleSubscriptionRequest(ACLMessage request) {
		
		ACLMessage join = request.createReply();
		join.setPerformative(ACLMessage.INFORM);
		System.out.println("Joined");

		join.setContent("join");
		
		AID sender = request.getSender();
		p.add(sender);
	
		return join;
	}

}
