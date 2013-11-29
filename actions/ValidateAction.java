package actions;

import java.util.ArrayList;

import logic.Board;

public class ValidateAction {
	public static boolean validate(ArrayList<String> playerTerritories, int availableSoldiers, ReceiveAction action){
		return true;
	}
	
	public static boolean validate(Board board, PerformAtackAction action ){
		return true;
	}
	
	public static boolean validate(Board board, PerformFortificationAction action){
		return true;
	}
}
