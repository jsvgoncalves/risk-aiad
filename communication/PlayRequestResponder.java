package communication;

import java.io.IOException;

import behaviours.playeragent.SensorBehaviour;
import util.R;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.AchieveREResponder;
import logic.Board;

public class PlayRequestResponder extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7663285688046592434L;

	private SensorBehaviour b;

	public PlayRequestResponder(Agent a, MessageTemplate mt) {
		super(a, mt);
	}

	public void setSensor(SensorBehaviour sensor) {
		b = sensor;
	}

	public static MessageTemplate getMessageTemplate() {
		return AchieveREResponder
				.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_REQUEST);
	}

	protected ACLMessage handleRequest(ACLMessage request) {

		try {
			Board board = (Board)request.getContentObject();
			return handleUpdate(request,board);
		} catch (UnreadableException e) {
			String content = request.getContent();
			String type = content.split(" ")[0];

			switch (type) {
			case R.PLAY:
				return handlePlayRequest(request);
			case R.RECEIVE:
				return handleReceiveRequest(request);
			case R.ATACK:
				return handleAtackRequest(request);
			case R.FORTIFY:
				return handleFortifyRequest(request);
			case R.CONTINUE:
				return handleContinueRequest(request);
			}

			ACLMessage error = request.createReply();
			error.setPerformative(ACLMessage.FAILURE);
			error.setContent("Content not valid!");

			return error;
		}
	}

	private ACLMessage handleUpdate(ACLMessage request, Board board) {
		ACLMessage inform = request.createReply();
		inform.setPerformative(ACLMessage.INFORM);
		inform.setContent(R.UPDATED);
		
		b.updateBoard(board);
		
		return inform;
	}

	private ACLMessage handleContinueRequest(ACLMessage request) {
		ACLMessage cont = request.createReply();

		try {
			String won = request.getContent().split(" ")[1];
			boolean wonLast = Boolean.parseBoolean(won);
			String my = request.getContent().split(" ")[2];
			int mySoldiers = Integer.parseInt(my);
			String his = request.getContent().split(" ")[3];
			int hisSoldiers = Integer.parseInt(his);
			
			cont.setPerformative(ACLMessage.INFORM);
			cont.setContentObject(b.cont(wonLast,mySoldiers,hisSoldiers));
		} catch (IOException e) {
			cont.setPerformative(ACLMessage.FAILURE);
			cont.setContent("Could not serialize action!");
		}

		return cont;
	}

	private ACLMessage handleFortifyRequest(ACLMessage request) {
		ACLMessage fortify = request.createReply();

		try {
			fortify.setPerformative(ACLMessage.INFORM);
			fortify.setContentObject(b.fortify());
		} catch (IOException e) {
			fortify.setPerformative(ACLMessage.FAILURE);
			fortify.setContent("Could not serialize action!");
		}

		return fortify;
	}

	private ACLMessage handleAtackRequest(ACLMessage request) {

		ACLMessage atack = request.createReply();

		try {
			atack.setPerformative(ACLMessage.INFORM);
			atack.setContentObject(b.atack());
		} catch (IOException e) {
			atack.setPerformative(ACLMessage.FAILURE);
			atack.setContent("Could not serialize action!");
		}

		return atack;
	}

	private ACLMessage handleReceiveRequest(ACLMessage request) {
		ACLMessage receive = request.createReply();

		try {
			String nS = request.getContent().split(" ")[1];
			int n = Integer.parseInt(nS);

			receive.setPerformative(ACLMessage.INFORM);
			receive.setContentObject(b.receive(n));
		} catch (IOException e) {
			receive.setPerformative(ACLMessage.FAILURE);
			receive.setContent("Could not serialize action!");
		}

		return receive;
	}

	private ACLMessage handlePlayRequest(ACLMessage request) {
		ACLMessage join = request.createReply();

		try {
			join.setPerformative(ACLMessage.INFORM);
			join.setContentObject(b.respond());
		} catch (IOException e) {
			join.setPerformative(ACLMessage.FAILURE);
			join.setContent("Could not serialize action!");
		}

		return join;
	}

}
