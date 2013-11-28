package communication;

import util.R;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class RequestInitiator extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7797031078781475466L;

	public RequestInitiator(Agent a, ACLMessage msg) {
		super(a, msg);
	}

	public static ACLMessage getJoinMessage(AID to) {
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.setContent(R.SUBSCRIPTION);
		request.addReceiver(to);

		return request;
	}

	public static ACLMessage getPlayMessage(AID to) {
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.setContent(R.PLAY);
		request.addReceiver(to);

		return request;
	}

	protected void handleInform(ACLMessage inform) {
		switch (inform.getContent()) {
		case R.PLAY:
			System.out.println("Played");
			break;
		case R.JOIN:
			System.out.println("Joined with success!");
			break;
		}

	}

	protected void handleFailure(ACLMessage failure) {
		// TODO Do something with failure
		System.out.println("Couldn't join!");
	}

}
