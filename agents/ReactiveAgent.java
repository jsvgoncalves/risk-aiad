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

		ArrayList<String> atackedTerritories = getAtackedTerritories(b,
				perceptions);

		// If player was atacked last round
		if (atackedTerritories.size() > 0) {
			// Put available soldiers in the territories that were atacked and
			// survived
			// While player has available soldiers
			while (n > 0) {
				// Gets the territory that was attacked and is most vulnerable
				String min = getMaxDifSoldiers(b, atackedTerritories);
				receive.addSoldiersTerritory(1, min);
				// Updates board so it doesn't always choose the same territory
				b.getTerritory(min).addSoldiers(1);
				n--;
			}
			putPerceptions(perceptions);
			return receive;
		}

		ArrayList<FortifyPerception> fortifications = getFortifyClosePerceptions(
				perceptions, b);
		if (fortifications.size() > 0) {
			// If fortified close
			ArrayList<String> possibleFortify = getPossibleFortifyFromFortifyClose(
					b, fortifications);

			if (possibleFortify.size() > 0) {
				while (n > 0) {
					// Gets the territory that was attacked and is most
					// vulnerable
					String min = getMaxDifSoldiers(b, possibleFortify);
					receive.addSoldiersTerritory(1, min);
					// Updates board so it doesn't always choose the same
					// territory
					b.getTerritory(min).addSoldiers(1);
					n--;
				}
			}
		}

		ArrayList<String> territories = b
				.getFortifyReadyPlayerTerritories(myAgent.getLocalName());
		// Put available soldiers in the frontline with less soldiers
		if (territories.size() > 0) {
			while (n > 0) {
				String min = getMaxDifSoldiers(b, territories);
				receive.addSoldiersTerritory(1, min);
				b.getTerritory(min).addSoldiers(1);
				n--;
			}
		}

		// Put back the perceptions so the agent can get them in the atack fase
		putPerceptions(perceptions);
		return receive;
	}

	/**
	 * 
	 * @param b
	 *            Board
	 * @param atackedTerritories
	 *            All the attacked territories in the control of the player that
	 *            were attacked
	 * @return Returns the territories that have the most difference in soldiers
	 *         number with the surrounding opposing territories
	 */
	private String getMaxDifSoldiers(Board b,
			ArrayList<String> atackedTerritories) {
		if (atackedTerritories.size() == 0)
			return null;

		String min = null;
		int nMin = Integer.MAX_VALUE;
		int maxDif = Integer.MIN_VALUE;

		// Forall atacked territories
		for (String t : atackedTerritories) {
			Territory territory = b.getTerritory(t);
			// Forall enemy territories adjacent
			for (Territory terr : b.getEnemyAdjacents(territory,
					myAgent.getLocalName())) {
				// If the difference in soldiers for the adjacent is the biggest
				// or
				// if equals the biggest and the number of soldiers is smaller
				// than in min then
				// min changes
				if (terr.getNumSoldiers() - territory.getNumSoldiers() > maxDif) {
					min = t;
					nMin = territory.getNumSoldiers();
					maxDif = terr.getNumSoldiers() - territory.getNumSoldiers();
				} else if (terr.getNumSoldiers() - territory.getNumSoldiers() == maxDif
						&& territory.getNumSoldiers() < nMin) {
					min = t;
					nMin = territory.getNumSoldiers();
					maxDif = terr.getNumSoldiers() - territory.getNumSoldiers();
				}
			}
		}

		return min;
	}

	private String getMinPossibleSoldiers(Board b,
			ArrayList<PossibleToAtack> possibleAtacks) {
		if (possibleAtacks.size() == 0)
			return null;

		String min = possibleAtacks.get(0).getFrom();
		int nMin = b.getTerritory(min).getNumSoldiers();

		for (int i = 1; i < possibleAtacks.size(); i++) {
			if (b.getTerritory(possibleAtacks.get(i).getFrom())
					.getNumSoldiers() < nMin) {
				nMin = b.getTerritory(possibleAtacks.get(i).getFrom())
						.getNumSoldiers();
				min = possibleAtacks.get(i).getFrom();
			}
		}

		return min;
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

	/**
	 * 
	 * @param b
	 *            Board
	 * @param perceptions
	 *            All perceptions
	 * @return Returns all the territories that were attacked and are still in
	 *         the control of the player
	 */
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

		int div;

		if (b.getTotalNumTerritories(myAgent.getLocalName()) == 0)
			div = 1;
		else
			div = b.getTotalNumSoldiers(myAgent.getLocalName())
					/ b.getTotalNumTerritories(myAgent.getLocalName());

		if (div == 0)
			div = 1;

		int factor = x / div;
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
			if (i < atacks.size()) {
				AtackPerception at = atacks.get(i);
				perceptions.remove(i);
				putPerceptions(perceptions);
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
					putPerceptions(perceptions);
					return new PerformAtackAction(possibleAtacks.get(i)
							.getFrom(), possibleAtacks.get(i).getTo());
				}
			}
		}

		// Atacks an enemy territory with some probability. Otherwise, in a game
		// only with reactive agents, none would atackW
		ArrayList<String> terr = b.getReadyPlayerTerritories(myAgent
				.getLocalName());
		if (terr.size() > 0) {
			int from = random.nextInt(terr.size() + factor);
			if (from < terr.size()) {
				ArrayList<Territory> enemies = b.getEnemyAdjacents(
						b.getTerritory(terr.get(from)), myAgent.getLocalName());

				if (enemies.size() > 0) {
					int to = random.nextInt(enemies.size() + factor);
					// If the player has more soldiers than the opposing one
					if (to < enemies.size()) {
						putPerceptions(perceptions);
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

	/**
	 * Name is not the best
	 * 
	 * @param b
	 *            Board
	 * @param fortifications
	 *            Array with fortifications close to the agent
	 * @return Returns all the possible moves that an agent can make in order to
	 *         fortify close to the territories in danger
	 */
	private ArrayList<PossibleToAtack> getPossibleAtacksFromFortifyClose(
			Board b, ArrayList<FortifyPerception> fortifications) {
		ArrayList<PossibleToAtack> possibleAtacks = new ArrayList<PossibleToAtack>();
		// Forall fortification Perceptions that fortified close
		for (FortifyPerception p : fortifications) {
			// Get my adjacent territories
			ArrayList<Territory> myAdjacents = b.getPlayerAdjacents(p.getTo(),
					myAgent.getLocalName());

			for (Territory t : myAdjacents) {
				// Get all the adjacents with more than 1 soldier
				for (Territory t2 : b.getReachables(t,
						myAgent.getLocalName())) {
					if (t2.getNumSoldiers() > 1)
						possibleAtacks.add(new PossibleToAtack(t2.getKey(), t
								.getKey(), p));
				}
			}
		}
		return possibleAtacks;
	}

	private ArrayList<String> getPossibleFortifyFromFortifyClose(Board b,
			ArrayList<FortifyPerception> fortifications) {
		ArrayList<String> possibleFortify = new ArrayList<String>();
		for (FortifyPerception p : fortifications) {
			if (!b.isPlayersTerritory(myAgent.getLocalName(), p.getFrom())) {
				ArrayList<Territory> myAdjacents = b.getPlayerAdjacents(
						p.getTo(), myAgent.getLocalName());
				for (Territory t : myAdjacents) {
					if (t.getNumSoldiers() < b.getTerritory(p.getTo())
							.getNumSoldiers())
						possibleFortify.add(t.getKey());
				}
			}
		}
		return possibleFortify;
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
					&& b.numSoldiersAround(p.getFrom(), myAgent.getLocalName()) > b
							.getTerritory(p.getFrom()).getNumSoldiers()) {
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

	private ArrayList<FortifyPerception> getFortifyClosePerceptions(
			ArrayList<Perception> perceptions, Board b) {
		ArrayList<FortifyPerception> fortifications = getFortifyPerceptions(perceptions);
		ArrayList<FortifyPerception> fortifyClose = new ArrayList<FortifyPerception>();

		for (FortifyPerception p : fortifications) {
			if (b.getEnemyAdjacents(myAgent.getLocalName()).contains(p.getTo()))
				fortifyClose.add((FortifyPerception) p);
		}

		return fortifyClose;
	}

	@Override
	public FortifyAction fortify(Board b) {
		// No need to put the perceptions back
		ArrayList<Perception> perceptions = getAllPerceptions();

		ArrayList<String> insideTerritories = b.getInsideTerritories(myAgent
				.getLocalName());
		if (insideTerritories.size() > 0) {
			String max = getMaxSoldiers(insideTerritories, b);
			String min = getMinSoldiersClose(max, b);
			int n = b.getTerritory(max).getNumSoldiers() - 1;
			return new PerformFortificationAction(max, min, n);
		}

		ArrayList<FortifyPerception> fortifications = getFortifyClosePerceptions(
				perceptions, b);

		if (fortifications.size() == 0)
			return new DontFortifyAction();

		if (fortifications.size() > 0) {
			// If fortified close
			ArrayList<PossibleToAtack> possibleMoves = getPossibleAtacksFromFortifyClose(
					b, fortifications);

			// Verificar qual o melhor movimento. Tem que verificar qual tem
			// maior diferenca
			if (possibleMoves.size() > 0) {
				PossibleToAtack move = getBestFortifyMove(possibleMoves, b);
				int n = b.getTerritory(move.getFrom()).getNumSoldiers() - 1;
				return new PerformFortificationAction(move.getFrom(),
						move.getTo(), n);
			}
		}

		return new DontFortifyAction();
	}

	/**
	 * Returns the territory that has less number of soldiers from the adjacents
	 * of max
	 * 
	 * @param insideTerritories
	 * @param b
	 * @return
	 */
	private String getMinSoldiersClose(String max, Board b) {

		int minS = Integer.MAX_VALUE;
		String min = null;

		for (Territory t : b.getPlayerAdjacents(max, myAgent.getLocalName())) {
			if (t.getNumSoldiers() < minS) {
				minS = t.getNumSoldiers();
				min = t.getKey();
			}
		}

		return min;

	}

	/**
	 * Returns the territory that has the most number of soldiers from the list
	 * 
	 * @param insideTerritories
	 * @param b
	 * @return
	 */
	private String getMaxSoldiers(ArrayList<String> insideTerritories, Board b) {

		int maxS = Integer.MIN_VALUE;
		String max = insideTerritories.get(0);

		for (String t : insideTerritories) {
			if (b.getTerritory(t).getNumSoldiers() > maxS) {
				maxS = b.getTerritory(t).getNumSoldiers();
				max = b.getTerritory(t).getKey();
			}
		}

		return max;
	}

	/**
	 * 
	 * @param possibleMoves
	 *            all possible fortification moves
	 * @param b
	 *            Board
	 * @return Returns the move with the biggest diference between the soldiers
	 *         of the frontline territory and the opposing territory
	 */
	private PossibleToAtack getBestFortifyMove(
			ArrayList<PossibleToAtack> possibleMoves, Board b) {

		PossibleToAtack min = possibleMoves.get(0);
		int maxDif = Integer.MIN_VALUE;

		for (PossibleToAtack p : possibleMoves) {
			if (b.getTerritory(p.getPerception().getTo()).getNumSoldiers()
					- b.getTerritory(p.getTo()).getNumSoldiers() > maxDif) {
				min = p;
				maxDif = b.getTerritory(p.getPerception().getTo())
						.getNumSoldiers()
						- b.getTerritory(p.getTo()).getNumSoldiers();
			}
		}

		return min;
	}

}
