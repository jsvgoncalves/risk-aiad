package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class PlayerAgent extends Agent {
//	private MessageTemplate template = MessageTemplate.and(
//			MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),
//			MessageTemplate.MatchOntology("presence") );
	private MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5347118089332895954L; 
	
	protected void setup() {
		System.out.println(getLocalName() + " reporting in.");
		addBehaviour(new PlayBehaviour());
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("player");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
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
			
			
			ACLMessage msg = myAgent.receive(template);
			if (msg != null) { // The message should match the template.
				//System.out.println("Received QUERY_IF message from agent " + msg.getSender().getName());
				ACLMessage reply = msg.createReply();
				if ("alive".equals(msg.getContent())) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("alive");
					System.out.println(getLocalName() + ": I just said I'm alive.");
				}
				else if("play".equals(msg.getContent())) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("I don't know how :(");
					System.out.println(getLocalName() + ": they say I should play now.");
				}
				else {
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("malformed");
				}
				myAgent.send(reply);
				
			}
			else {
				block();
			}
			
		} 


		public int onEnd() {
			myAgent.doDelete();
			return super.onEnd();
		} 
	}    // END of inner class FourStepBehaviour
}
