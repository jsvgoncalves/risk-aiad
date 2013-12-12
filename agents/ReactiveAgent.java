package agents;

import java.util.ArrayList;

import perceptions.Perception;

import logic.Board;
import actions.AtackAction;
import actions.FortifyAction;
import actions.ReceiveAction;

public class ReactiveAgent extends PlayerAgentBehaviours{

	
	private ArrayList<Perception> getAllPerceptions(){
		ArrayList<Perception> perceptions = new ArrayList<Perception>();
		
		while(!myAgent.perceptEmpty()){
			
		}
		
		return perceptions;
	}
	
	@Override
	public ReceiveAction receiveSoldiers(Board b, int n) {
		
		return null;
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
