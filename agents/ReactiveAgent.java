package agents;

import java.util.ArrayList;
import java.util.Random;

import perceptions.AtackPerception;
import perceptions.FortifyPerception;
import perceptions.Perception;
import util.PossibleToAtack;
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

	private int x;
	private Random random;

	/**
	 * 
	 * @param x
	 *            The bigger x is, the smaller is the probability the agent will
	 *            atack.
	 */
	public ReactiveAgent(int x) {
		this.x = x;
		random = new Random();
	}

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
			ArrayList<String> territories = b
					.getFortifyReadyPlayerTerritories(myAgent.getLocalName());
			// Put available soldiers in the frontline with less soldiers
			if (territories.size() > 0) {
				while (n > 0) {
					String min = getMinSoldiers(b, territories);
					receive.addSoldiersTerritory(1, min);
					n--;
				}
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

		int factor = x
				/ (b.getTotalNumSoldiers(myAgent.getLocalName()) / b
						.getTotalNumTerritories(myAgent.getLocalName()));

		// If territory was atacked and has more soldiers than the oposing
		// territory then atacks
		ArrayList<AtackPerception> atacks = getValidAtackPerceptions(
				perceptions, b);

		// All the fortifications that moved soldiers away from a player's
		// territory
		ArrayList<FortifyPerception> fortifications = getFortifyAwayPerceptions(
				perceptions, b);

		if (atacks.size() > 0) {
			// Attacks back
			int i = random.nextInt(atacks.size() + factor);

			// If i is a valid index of atacks and if player's territory has
			// more or equal number of soldiers than the opposing territory then
			// atacks
			if (i < atacks.size()
					&& b.getTerritory(atacks.get(i).getTo()).getNumSoldiers() >= b
							.getTerritory(atacks.get(i).getFrom())
							.getNumSoldiers()) {
				AtackPerception at = atacks.get(i);
				perceptions.remove(i);
				return new PerformAtackAction(at.getTo(), at.getFrom());
			}
		}

		if (fortifications.size() > 0) {
			// If fortified away
			ArrayList<PossibleToAtack> possibleAtacks = getPossibleAtacksFromFortifyAway(
					b, fortifications);

			if (possibleAtacks.size() > 0) {
				int i = random.nextInt(possibleAtacks.size() + factor);

				if (i < possibleAtacks.size()) {
					perceptions.remove(possibleAtacks.get(i).getPerception());
					return new PerformAtackAction(possibleAtacks.get(i)
							.getFrom(), possibleAtacks.get(i).getTo());
				}
			}
		}

		// Atacks an enemy territory with some probability. Otherwise, in a game
		// only with reactive agents, none would atack
		ArrayList<String> terr = b.getReadyPlayerTerritories(myAgent
				.getLocalName());
		if (terr.size() > 0) {
			int from = random.nextInt(terr.size() + factor);
			if (from < terr.size()) {
				ArrayList<Territory> enemies = b.getEnemyAdjacents(
						b.getTerritory(terr.get(from)), myAgent.getLocalName());

				if (enemies.size() > 0) {
					int to = random.nextInt(enemies.size() + factor);
					if (to < enemies.size()) {
						return new PerformAtackAction(terr.get(from), enemies
								.get(to).getKey());
					}
				}
			}
		}

		// Put back the perceptions so the agent can get them in the next atack
		// and in the fortify fase
		putPerceptions(perceptions);
		return new DontAtackAction();
	}

	private ArrayList<PossibleToAtack> getPossibleAtacksFromFortifyAway(
			Board b, ArrayList<FortifyPerception> fortifications) {
		ArrayList<PossibleToAtack> possibleAtacks = new ArrayList<PossibleToAtack>();
		for (FortifyPerception p : fortifications) {
			ArrayList<Territory> myAdjacents = b.getPlayerAdjacents(
					p.getFrom(), myAgent.getLocalName());
			for (Territory t : myAdjacents) {
				if (t.getNumSoldiers() >= b.getTerritory(p.getFrom())
						.getNumSoldiers())
					possibleAtacks.add(new PossibleToAtack(t.getKey(), p
							.getFrom(), p));
			}
		}
		return possibleAtacks;
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

	private ArrayList<FortifyPerception> getFortifyAwayPerceptions(
			ArrayList<Perception> perceptions, Board b) {
		ArrayList<FortifyPerception> fortifications = getFortifyPerceptions(perceptions);
		ArrayList<FortifyPerception> fortifyAway = new ArrayList<FortifyPerception>();
		
		for (FortifyPerception p : fortifications) {
			if (b.getEnemyAdjacents(myAgent.getLocalName()).contains(
					p.getFrom()))
				fortifyAway.add((FortifyPerception) p);
		}

		return fortifyAway;
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
