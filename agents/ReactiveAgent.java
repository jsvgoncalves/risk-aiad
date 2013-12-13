package agents;

import java.util.ArrayList;

import perceptions.AtackPerception;
import perceptions.FortifyPerception;
import perceptions.Perception;
import util.R;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.PerformFortificationAction;
import actions.ReceiveAction;

public class ReactiveAgent extends PlayerAgentBehaviours {

	private ArrayList<Perception> getAllPerceptions() {
		ArrayList<Perception> perceptions = new ArrayList<Perception>();

		while (!myAgent.perceptEmpty()) {
			perceptions.add(myAgent.getPerception());
		}

		return perceptions;
	}

	private void putPerceptions(ArrayList<Perception> perceptions) {
		for (Perception p : perceptions) {
			myAgent.addPerception(p);
		}
	}

	@Override
	public ReceiveAction receiveSoldiers(Board b, int n) {
		final ArrayList<Perception> perceptions = getAllPerceptions();
		ReceiveAction receive = new ReceiveAction();

		// TODO Territorios que fortificaram para perto dos meus como segunda
		// linha e que
		// colocaram soldados perto dos meus territorios como terceira linha
		ArrayList<String> atackedTerritories = getAtackedTerritories(b,
				perceptions);

		// If player was atacked last round
		if (atackedTerritories.size() > 0) {
			// Put available soldiers in the territories that were atacked and
			// survived
			while (n > 0) {
				String min = getMinSoldiers(b, atackedTerritories);
				receive.addSoldiersTerritory(1, min);
				n--;
			}
		} else {
			ArrayList<String> territories = b.getFortifyReadyPlayerTerritories(myAgent
					.getLocalName());
			// Put available soldiers in the frontline with less soldiers
			while (n > 0) {
				String min = getMinSoldiers(b, territories);
				receive.addSoldiersTerritory(1, min);
				n--;
			}
		}

		// Put back the perceptions so the agent can get them in the atack fase
		putPerceptions(perceptions);
		return receive;
	}

	private String getMinSoldiers(Board b, ArrayList<String> atackedTerritories) {
		if (atackedTerritories.size() == 0)
			return null;

		String min = atackedTerritories.get(0);
		int nMin = b.getTerritory(min).getNumSoldiers();

		for (int i = 1; i < atackedTerritories.size(); i++) {
			if (b.getTerritory(atackedTerritories.get(i)).getNumSoldiers() < nMin) {
				nMin = b.getTerritory(atackedTerritories.get(i))
						.getNumSoldiers();
				min = atackedTerritories.get(i);
			}
		}

		return min;
	}

	private ArrayList<String> getAtackedTerritories(Board b,
			final ArrayList<Perception> perceptions) {
		ArrayList<String> atackedTerritories = new ArrayList<String>();

		for (Perception p : perceptions) {
			if (p.getClass().getName().equals(R.PERCEPTION_ATACK)) {
				AtackPerception percept = (AtackPerception) p;
				if (b.isPlayersTerritory(myAgent.getLocalName(),
						percept.getTo())) {
					atackedTerritories.add(percept.getTo());
				}
			}
		}
		return atackedTerritories;
	}

	@Override
	public AtackAction atack(Board b) {
		ArrayList<Perception> perceptions = getAllPerceptions();

		// If territory was atacked and has more soldiers than the oposing
		// territory then atacks
		ArrayList<AtackPerception> atacks = getValidAtackPerceptions(
				perceptions, b);
		if (atacks.size() > 0) {
			// Atacks back
			return new PerformAtackAction(atacks.get(0).getTo(), atacks.get(0)
					.getFrom());
		}

		// TODO Atacar territorios que fortificaram de um territorio acessivel
		// de um territorio de player para longe

		// Put back the perceptions so the agent can get them in the fortify
		// fase
		putPerceptions(perceptions);
		return new DontAtackAction();
	}

	// Returns all the Perceptions that atacked this player and have less
	// soldiers
	private ArrayList<AtackPerception> getValidAtackPerceptions(
			ArrayList<Perception> perceptions, Board b) {
		ArrayList<AtackPerception> atacks = getAtackPerceptions(perceptions);
		ArrayList<AtackPerception> ret = new ArrayList<AtackPerception>();

		for (AtackPerception p : atacks) {
			// If the territory atacked is from player and the territory that
			// atacked is not
			// and player has more soldiers than the adversary
			if (b.isPlayersTerritory(myAgent.getLocalName(), p.getTo())
					&& !b.isPlayersTerritory(myAgent.getLocalName(),
							p.getFrom())
					&& b.getTerritory(p.getFrom()).getNumSoldiers() < b
							.getTerritory(p.getTo()).getNumSoldiers()) {
				ret.add(p);
			}
		}

		return ret;
	}

	private ArrayList<AtackPerception> getAtackPerceptions(
			ArrayList<Perception> perceptions) {
		ArrayList<AtackPerception> atacks = new ArrayList<AtackPerception>();

		for (Perception p : perceptions) {
			if (p.getClass().getName().equals(R.PERCEPTION_ATACK))
				atacks.add((AtackPerception) p);
		}
		return atacks;
	}

	private ArrayList<FortifyPerception> getFortifyPerceptions(
			ArrayList<Perception> perceptions) {
		ArrayList<FortifyPerception> fortifications = new ArrayList<FortifyPerception>();

		for (Perception p : perceptions) {
			if (p.getClass().getName().equals(R.PERCEPTION_FORTIFY))
				fortifications.add((FortifyPerception) p);
		}

		return fortifications;
	}

	@Override
	public FortifyAction fortify(Board b) {
		// No need to put the perceptions back
		ArrayList<Perception> perceptions = getAllPerceptions();
		ArrayList<FortifyPerception> fortifications = getFortifyPerceptions(perceptions);

		if (fortifications.size() == 0)
			return new DontFortifyAction();

		// TODO Optimizar fortificacao. Tem que encntrar territorio que nao faz
		// fronteira com p.getTo() e que tenha maior numero de soldados

		for (FortifyPerception p : fortifications) {
			ArrayList<Territory> myAdjacents = b.getPlayerAdjacents(p.getTo(),
					myAgent.getLocalName());
			if (myAdjacents.size() > 0) {
				for (Territory t : myAdjacents) {
					for (Territory t2 : b.getPlayerAdjacents(t.getKey(),
							myAgent.getLocalName())) {
						if (t2.getNumSoldiers() > 1) {
							return new PerformFortificationAction(t2.getKey(),
									t.getKey(), t2.getNumSoldiers() - 1);
						}
					}
				}
			}
		}

		return new DontFortifyAction();
	}

}
