package agents;

import java.util.ArrayList;

import perceptions.AtackPerception;
import perceptions.Perception;

import userbehaviours.User;
import userbehaviours.UserBehaviours;
import util.R;

import logic.Board;
import logic.Territory;
import actions.AtackAction;
import actions.DontAtackAction;
import actions.DontFortifyAction;
import actions.FortifyAction;
import actions.PerformAtackAction;
import actions.ReceiveAction;

public class DeliberativeAgent extends PlayerAgentBehaviours {

	private final double ATACK_AGRESSIVE = 2;
	private final double ATACK_REACTIVE = 1;
	private final double ATACK_RANDOM = 2;
	private final double RECEIVE_AGRESSIVE = 2;
	private final double RECEIVE_REACTIVE = -1;
	private final double RECEIVE_RANDOM = 1;

	private ArrayList<User> players = new ArrayList<User>();
	private UserBehaviours userB;
	private String player;
	private ArrayList<String> lastRoundAtacked;
	
	public DeliberativeAgent(){
		userB = new UserBehaviours(players);
		lastRoundAtacked = new ArrayList<String>();
	}

	private boolean isNewPlayer(String player) {
		for (User user : players) {
			if (user.getName().equals(player))
				return false;
		}

		return true;
	}

	private ArrayList<Perception> getAllPerceptions() {
		ArrayList<Perception> perceptions = new ArrayList<Perception>();

		while (!myAgent.perceptEmpty()) {
			perceptions.add(myAgent.getPerception());
		}

		return perceptions;
	}

	@Override
	public ReceiveAction receiveSoldiers(Board b, int n) {
		ArrayList<Perception> perceptions = getAllPerceptions();
		ReceiveAction receive = new ReceiveAction();
		player = myAgent.getLocalName();

		// Updates other playerInformation
		checkNewPlayers(perceptions,b);
		userB = new UserBehaviours(players);
		
		

		// Sort players by their behaviours
		ArrayList<String> agressive = getAgressivePlayers();
		ArrayList<String> random = getRandomPlayers();
		ArrayList<String> reactive = getReactivePlayers();
		
		System.err.println("Agressive");
		for(String ag:agressive)
			System.err.println(ag);
		
		System.err.println("Random");
		for(String ag:random)
			System.err.println(ag);
		
		System.err.println("Reactive");
		for(String ag:reactive)
			System.err.println(ag);

		// Get all the frontlineTerritories
		ArrayList<String> readyTerritories = b
				.getFortifyReadyPlayerTerritories(player);

		while (n > 0) {
			double maxFA = Double.MIN_VALUE;
			int pos = 0;
			// Expand all territories
			for (int i = 0; i < readyTerritories.size(); i++) {
				double fa = faReceive(readyTerritories.get(i), b, agressive,
						reactive, random);
				if (fa > maxFA) {
					maxFA = fa;
					pos = i;
				}
			}

			b.getTerritory(readyTerritories.get(pos)).addSoldiers(1);
			receive.addSoldiersTerritory(1, readyTerritories.get(pos));
			n--;
		}

		return receive;
	}

	private double faReceive(String territory, Board b,
			ArrayList<String> agressive, ArrayList<String> reactive,
			ArrayList<String> random) {

		ArrayList<Territory> enemies = b.getEnemyAdjacents(
				b.getTerritory(territory), player);

		double hisS = 0;
		for (Territory t : enemies) {
			hisS += t.getNumSoldiers();

			// Get the player of territory
			String terOwner = b.getPlayerFromTerritory(t);
			if (agressive.contains(terOwner)) {
				hisS = hisS * RECEIVE_AGRESSIVE;
			} else if (reactive.contains(terOwner)) {
				hisS = hisS * RECEIVE_REACTIVE;
			} else if (random.contains(terOwner)) {
				hisS = hisS * RECEIVE_RANDOM;
			} else
				System.err.println("Player not found");

		}

		return hisS;
	}

	private ArrayList<String> getReactivePlayers() {
		ArrayList<String> reactive = new ArrayList<String>();
		for (User user : players) {
			if (userB.isReactive(user))
				reactive.add(user.getName());
		}
		return reactive;
	}

	private ArrayList<String> getRandomPlayers() {
		ArrayList<String> random = new ArrayList<String>();
		for (User user : players) {
			if (userB.isRandom(user))
				random.add(user.getName());
		}
		return random;
	}

	private ArrayList<String> getAgressivePlayers() {
		ArrayList<String> agressive = new ArrayList<String>();
		for (User user : players) {
			if (userB.isAgressive(user))
				agressive.add(user.getName());
		}
		return agressive;
	}

	/**
	 * Iterates through the perceptions and if finds a new player adds it to
	 * players
	 * 
	 * @param perceptions
	 *            all perceptions
	 */
	private void checkNewPlayers(ArrayList<Perception> perceptions,Board b) {
		for (Perception p : perceptions) {
			ArrayList<String> ps = p.getAllPlayersInvolved(myAgent
					.getLocalName());
			for (String player : ps) {
				if (isNewPlayer(player))
					this.players.add(new User(player));
			}
		}
		
		if(lastRoundAtacked.size() == 0)
			return;
		
		ArrayList<AtackPerception> atacks = getMyAtacks(perceptions,b);
		for(AtackPerception a: atacks){
			if(atackedLastRound(a.getFrom()) && a.getPlayerTo().equals(player)){
				userAtackedBack(a.getPlayerFrom());
			}else if( !atackedLastRound(a.getFrom()) && a.getPlayerTo().equals(player))
				userAtackedAlone(a.getPlayerFrom());
		}
		
	}

	private void userAtackedAlone(String playerFrom) {
		for(User user:players){
			if( user.getName().equals(playerFrom)){
				user.addAtackAlone();
			}
		}
	}

	private void userAtackedBack(String playerFrom) {
		for(User user:players){
			if( user.getName().equals(playerFrom)){
				user.addAtackBack();
			}
		}
		
	}

	private boolean atackedLastRound(String from) {
		for(String l:lastRoundAtacked){
			if(l.equals(from))
				return true;
		}
		return false;
	}

	private ArrayList<AtackPerception> getMyAtacks(
			ArrayList<Perception> perceptions,Board b) {
		
		ArrayList<AtackPerception> ata = new ArrayList<AtackPerception>();
		
		for(Perception p:perceptions){
			if( p.getClass().getName().equals(R.PERCEPTION_ATACK))
			{
				AtackPerception a = (AtackPerception)p;
				if( b.isPlayersTerritory(player, a.getTo()))
					ata.add(a);
			}
		}
		
		return ata;
	}

	@Override
	public AtackAction atack(Board b) {

		// Sort players by their behaviours
		ArrayList<String> agressive = getAgressivePlayers();
		ArrayList<String> random = getRandomPlayers();
		ArrayList<String> reactive = getReactivePlayers();

		// Get all the frontlineTerritories
		ArrayList<String> readyTerritories = b
				.getReadyPlayerTerritories(player);

		double maxFA = 0;
		int posI = 0;
		int posJ = 0;
		for (int i = 0; i < readyTerritories.size(); i++) {
			ArrayList<Territory> enemyAdjacents = b.getEnemyAdjacents(
					b.getTerritory(readyTerritories.get(i)), player);
			for (int j = 0; j < enemyAdjacents.size(); j++) {
				double fa = faAtack(readyTerritories.get(i),
						enemyAdjacents.get(j), b, agressive, reactive, random);
				if (fa > maxFA) {
					maxFA = fa;
					posI = i;
					posJ = j;
				}
			}
		}
		
		if(maxFA == 0){
			return new DontAtackAction();
		}

		String from = readyTerritories.get(posI);
		ArrayList<Territory> enemyAdjacents = b.getEnemyAdjacents(
				b.getTerritory(from), player);
		String to = enemyAdjacents.get(posJ).getKey(); 
		lastRoundAtacked.add(to);
		
		return new PerformAtackAction(from, to);
	}

	private double faAtack(String string, Territory territory, Board b,
			ArrayList<String> agressive, ArrayList<String> reactive,
			ArrayList<String> random) {

		double fa = 0;

		
		for (String continent : b.getContinentList()) {
			if (b.isOfContinent(territory, continent))
				fa += (b.numTerritoriesContinent(player, continent, territory) / b
						.getContinent(continent).size())
						* b.getContinentValue(continent);
		}

		ArrayList<Territory> enemies = b.getPlayerAdjacents(territory.getKey(), player);
		int myS =0;
		for(Territory t: enemies){
			myS+=t.getNumSoldiers();
		}
		
		
		double hisS = territory.getNumSoldiers();
		hisS = myS-hisS;
		String terOwner = b.getPlayerFromTerritory(territory);
		if (agressive.contains(terOwner)) {
			hisS = hisS * ATACK_AGRESSIVE;
		} else if (reactive.contains(terOwner)) {
			hisS = hisS * ATACK_REACTIVE;
		} else if (random.contains(terOwner)) {
			hisS = hisS * ATACK_RANDOM;
		} else
			System.err.println("Player not found");
		
		fa += hisS;
		
		return fa;
	}

	@Override
	public FortifyAction fortify(Board b) {
		return new DontFortifyAction();
	}

}
