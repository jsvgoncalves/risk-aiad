package communication;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class PlayRequestResponder extends AchieveREResponder {

	public PlayRequestResponder(Agent a, MessageTemplate mt) {
		super(a, mt);
	}
	
	public static MessageTemplate getMessageTemplate(){
		return AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}
	
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response){
		ACLMessage play = request.createReply();
		play.setPerformative(ACLMessage.INFORM);
		System.out.println("I don't know how to play");
		//TODO Make play
		play.setContent("I don't know how to play");
		
		return play;
	}

}
