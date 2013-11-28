package behaviours.playeragent;

import util.R;
import communication.RequestInitiator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class JoinGameBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5782880757461631230L;

	public JoinGameBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void action() {
		AID[] gameAgents = getGameAgents();
		
		System.out.println(gameAgents.length);
		
		myAgent.addBehaviour(new RequestInitiator(myAgent,
				RequestInitiator.getJoinMessage(gameAgents[0])));

	}

	private AID[] getGameAgents() {
		// Build the description used as template for the subscription
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription templateSd = new ServiceDescription();
		templateSd.setType(R.GAME_AGENT);
		template.addServices(templateSd);

		SearchConstraints sc = new SearchConstraints();
		// We want to receive 10 results at most
		// sc.setMaxResults(new Long(10));

		try {
			DFAgentDescription[] results = DFService.search(myAgent, template,
					sc);
			AID[] agents = new AID[results.length];
			if (results.length > 0) {
				for (int i = 0; i < results.length; ++i) {
					agents[i] = results[i].getName();
				}
			}
			return agents;
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	@Override
	public boolean done() {
		return true;
	}

	@Override
	public int onEnd() {
		return 1;
	}

}
