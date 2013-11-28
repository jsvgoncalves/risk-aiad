package communication;

import behaviours.playeragent.SensorBehaviour;
import util.R;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class PlayRequestResponder extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7663285688046592434L;
	
	private SensorBehaviour b;

	public PlayRequestResponder(Agent a, MessageTemplate mt, SensorBehaviour behaviour) {
		super(a, mt);
		b=behaviour;
	}

	public static MessageTemplate getMessageTemplate() {
		return AchieveREResponder
				.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}

	protected ACLMessage handleRequest(ACLMessage request) {

		String content = request.getContent();

		switch (content) {
		case R.PLAY:
			return handlePlayRequest(request);
		}

		ACLMessage error = request.createReply();
		error.setPerformative(ACLMessage.FAILURE);
		error.setContent("Content not valid!");

		return error;
	}

	private ACLMessage handlePlayRequest(ACLMessage request) {
		ACLMessage join = request.createReply();
		join.setPerformative(ACLMessage.INFORM);
		System.out.println("Played");
		join.setContent(R.JOIN);

		return join;
	}

}
