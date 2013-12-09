package actions;

import java.util.Hashtable;

import logic.Board;
import logic.Territory;

public class ValidateAction {
	public static boolean validate(int availableSoldiers, ReceiveAction action,
			String name) {

		Hashtable<String, Integer> s = action.getSoldiersByTerritory();
		int soma = 0;
		for (String terr : s.keySet()) {
			soma += s.get(terr);

			if (!Board.getInstance().isPlayersTerritory(name, terr))
				return false;

			if (Board.getInstance().getTerritory(terr) == null)
				return false;
		}

		return soma == availableSoldiers;
	}

	public static boolean validate(PerformAtackAction action, String name) {
		if (Board.getInstance().getTerritory(action.getFrom()) == null
				|| Board.getInstance().getTerritory(action.getTo()) == null) {
			return false;
		}

		if (!Board.getInstance().isPlayersTerritory(name, action.getFrom()))
			return false;

		for (Territory t : Board.getInstance().getTerritory(action.getFrom())
				.getAdjacents()) {
			if (t.getKey().equals(action.getTo()))
				return true;
		}

		return false;
	}

	public static boolean validate(PerformFortificationAction action,
			String name) {

		if (!Board.getInstance().isPlayersTerritory(name, action.getFrom())
				&& !Board.getInstance()
						.isPlayersTerritory(name, action.getTo()))
			return false;

		for (Territory t : Board.getInstance().getTerritory(action.getFrom())
				.getAdjacents()) {
			if (t.getKey().equals(action.getTo())
					&& Board.getInstance().getTerritory(action.getFrom())
							.getNumSoldiers() >= action.getN())
				return true;
		}

		return true;
	}
}
