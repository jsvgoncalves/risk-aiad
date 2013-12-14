package agents;

import java.util.ArrayList;

import perceptions.Perception;

import userbehaviours.User;
import userbehaviours.UserBehaviours;

import logic.Board;
import actions.AtackAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public class DeliberativeAgent extends PlayerAgentBehaviours {
	
	private ArrayList<User> players = new ArrayList<User>();
	private UserBehaviours userB = new UserBehaviours(players);
	
	private boolean isNewPlayer(String player){
		for(User user:players){
			if( user.getName().equals(player))
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

	private void putPerceptions(ArrayList<Perception> perceptions) {
		for (Perception p : perceptions) {
			myAgent.addPerception(p);
		}
	}
	
	@Override
	public ReceiveAction receiveSoldiers(Board b, int n) {
		ArrayList<Perception> perceptions = getAllPerceptions();
		
		//Updates other playerInformation
		checkNewPlayers(perceptions);
		userB = new UserBehaviours(players);
		
		//Get agressive Players
		
		putPerceptions(perceptions);
		return null;
	}

	/**
	 * Iterates through the perceptions and if finds a new player adds it to players
	 * @param perceptions
	 * 				all perceptions
	 */
	private void checkNewPlayers(ArrayList<Perception> perceptions) {
		for(Perception p: perceptions){
			ArrayList<String> ps = p.getAllPlayersInvolved(myAgent.getLocalName());
			for(String player:ps){
				if(isNewPlayer(player))
					this.players.add(new User(player));
			}
		}
		
	}

	@Override
	public AtackAction atack(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FortifyAction fortify(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}
