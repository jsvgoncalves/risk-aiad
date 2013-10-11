package communication;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class PlayRequestResponder extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562921973707430856L;

	public PlayRequestResponder(Agent a, MessageTemplate mt) {
		super(a, mt);
	}
	
	public static MessageTemplate getMessageTemplate(){
		return AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}
	
	protected ACLMessage handleRequest(ACLMessage request){
		ACLMessage play = request.createReply();
		play.setPerformative(ACLMessage.INFORM);
		System.out.println("I don't know how to play");
		//TODO Make play
		play.setContent("I don't know how to play");
		
		return play;
	}

}
