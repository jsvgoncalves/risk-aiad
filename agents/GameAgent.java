package agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class GameAgent extends Agent {
	int i = 0;
	public final int TICK_TIME = 1000;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137655407030225340L;
	
	protected void setup() {
		System.out.println("Game launching.");
		addBehaviour(new RoundsBehaviour(this, TICK_TIME));
		
		
	}
	
	protected void searchPlayers() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType("player");
		template.addServices(sd1);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			// envia mensagem "pong" inicial a todos os agentes "ping"
			ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
			for(int i=0; i<result.length; ++i) {
				msg.addReceiver(result[i].getName());	
				System.out.println("Found player " + result[i].getName());
			}
			msg.setContent("alive");
			send(msg);
			
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inner class RoundsBehaviour
	 */
	private class RoundsBehaviour extends TickerBehaviour {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2897444297823568576L;

		public RoundsBehaviour(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		protected void onTick() {
			// For each agent, send him a message to play and wait response or timeout.
			System.out.println("Playing.." + i++);
			searchPlayers();
			
			
		}

	}    // END of inner class RoundsBehaviour
}
