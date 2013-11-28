package agents;

import util.R;
import behaviours.playeragent.JoinGameBehaviour;
import communication.PlayRequestResponder;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class PlayerAgent extends Agent {
//	private MessageTemplate template = MessageTemplate.and(
//			MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),
//			MessageTemplate.MatchOntology("presence") );
//	private MessageTemplate query_if_template = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);
//	private MessageTemplate proposal_template = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
//	private MessageTemplate inform_template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	/**
	 * 
	 */
	private static final long serialVersionUID = -5347118089332895954L; 
	private static final String JOIN = "Join game";
	
	protected void setup() {
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType(R.PLAYER_AGENT);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(getLocalName() + " reporting in.");
		
		//PlayerAgentBehaviour behaviour = new PlayerAgentBehaviour(this);
		
		//behaviour.registerFirstState(new JoinGameBehaviour(this), JOIN);
		
		
		//addBehaviour(behaviour);
		addBehaviour(new JoinGameBehaviour(this));
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inner class PlayerAgentBehaviour
	 */
	private class PlayerAgentBehaviour extends FSMBehaviour{

		/**
		 * 
		 */
		private static final long serialVersionUID = 591976149860561195L;
		
		public PlayerAgentBehaviour(Agent agent){
			super(agent);
		}
		
	}

	
	/**
	 * Inner class FourStepBehaviour
	 */
	private class PlayBehaviour extends CyclicBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2897444297823568576L;

		/**
		 * Either process an incoming message or block waiting for one.
		 */
		public void action() {
			
			
			addBehaviour(new PlayRequestResponder(myAgent, PlayRequestResponder.getMessageTemplate()));
			
			/*ACLMessage query_if_msg = myAgent.receive(query_if_template);
			ACLMessage proposal_msg = myAgent.receive(proposal_template);
			ACLMessage inform_msg = myAgent.receive(inform_template);
			
			if (query_if_msg != null) { // The message should match the template.
				ACLMessage reply = query_if_msg.createReply();
				if ("alive".equals(query_if_msg.getContent())) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("alive");
					System.out.println(getLocalName() + ": I just said I'm alive.");
				}
				else {
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("malformed");
				}
				myAgent.send(reply);
			} else if(proposal_msg != null) {
				ACLMessage reply = proposal_msg.createReply();
				if("play".equals(proposal_msg.getContent())) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("I don't know how :(");
					System.out.println(getLocalName() + ": they say I should play now.");
				}
				myAgent.send(reply);
			}
			else if(inform_msg != null) {
				ACLMessage reply = inform_msg.createReply();
				if("play".equals(inform_msg.getContent())) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("I don't know how :(");
					System.out.println(getLocalName() + ": they say I should play now.");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}*/
			
		} 


		public int onEnd() {
			myAgent.doDelete();
			return super.onEnd();
		} 
	}    // END of inner class FourStepBehaviour
}
