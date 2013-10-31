package communication;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class JoinGameRequestResponder extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<DFAgentDescription> players;

	public JoinGameRequestResponder(Agent a, MessageTemplate mt, ArrayList<DFAgentDescription> players) {
		super(a, mt);
		this.players = players;
	}
	
	public static MessageTemplate getMessageTemplate(){
		return AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}
	
	protected ACLMessage handleRequest(ACLMessage request){
		
		ACLMessage join = request.createReply();
		join.setPerformative(ACLMessage.INFORM);
		System.out.println("Joined");
		//TODO Make play
		join.setContent("join");
		
		AID sender = request.getSender();
	
		return join;
	}

}
