package actions;

import java.util.Hashtable;

import logic.Board;
import logic.Territory;

public class ValidateAction {
	public static boolean validate(int availableSoldiers, ReceiveAction action,
			String name, Board b) {

		Hashtable<String, Integer> s = action.getSoldiersByTerritory();
		int soma = 0;
		for (String terr : s.keySet()) {
			soma += s.get(terr);

			if (!b.isPlayersTerritory(name, terr))
				return false;

			if (b.getTerritory(terr) == null)
				return false;
		}

		return soma == availableSoldiers;
	}

	public static boolean validate(PerformAtackAction action, String name, Board b) {
		if (b.getTerritory(action.getFrom()) == null
				|| b.getTerritory(action.getTo()) == null) {
			return false;
		}

		Territory from = b.getTerritory(action.getFrom());
		if( from.getNumSoldiers() <= 1)
			return false;
		
		if (!b.isPlayersTerritory(name, action.getFrom()))
			return false;

		for (Territory t : b.getTerritory(action.getFrom())
				.getAdjacents()) {
			if (t.getKey().equals(action.getTo())){
				if( t.getNumSoldiers() > 0 )
					return true;
				else 
					return false;
			}
				
		}

		return false;
	}

	public static boolean validate(PerformFortificationAction action,
			String name, Board b) {

		if (!b.isPlayersTerritory(name, action.getFrom())
				|| !b
						.isPlayersTerritory(name, action.getTo()))
			return false;
//b.getTerritory(action.getFrom()).getAdjacents()
		for (Territory t : b.getReachables(b.getTerritory(action.getFrom()), name)) {
			if (t.getKey().equals(action.getTo())
					&& b.getTerritory(action.getFrom())
							.getNumSoldiers() > action.getN())
				return true;
		}

		return false;
	}
}
