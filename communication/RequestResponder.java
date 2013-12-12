package communication;

import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<AID, String> agentName;
	private int maxPlayers;

	public RequestResponder(Agent a, MessageTemplate mt,
			ArrayList<AID> players, HashMap<AID, String> agentName, int max) {
		super(a, mt);
		p = players;
		maxPlayers = max;
		this.agentName = agentName;
	}

	public static MessageTemplate getMessageTemplate() {
		return AchieveREResponder
				.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}

	protected ACLMessage handleRequest(ACLMessage request) {

		String[] content = request.getContent().split(" "); 

		switch (content[0]) {
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
		if (p.size() < maxPlayers) {
			join.setPerformative(ACLMessage.INFORM);
			AID sender = request.getSender();
			p.add(sender);
			agentName.put(sender, request.getContent().split(" ")[1]);
		}
		else{
			join.setPerformative(ACLMessage.FAILURE);
		}
		join.setContent(R.JOIN);

		

		return join;
	}

}
