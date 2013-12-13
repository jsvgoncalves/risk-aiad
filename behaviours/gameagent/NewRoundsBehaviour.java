package behaviours.gameagent;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class NewRoundsBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583197343317366877L;

	private static final String UPDATE = "update";
	private static final String POSITION = "position";
	private static final String ATACK = "atack";
	private static final String FORTIFY = "fortify";
	private static final String FINAL = "final";

	public NewRoundsBehaviour(Agent a, ArrayList<AID> players) {
		super(a);
		
		RequestActionBehaviour position = new RequestActionBehaviour(
				new PositionSoldiers(myAgent), players,a);
		RequestActionBehaviour atack = new RequestActionBehaviour(
				new AtackBehaviour(myAgent), players,a);
		RequestActionBehaviour fortify = new RequestActionBehaviour(
				new GameFortify(myAgent), players,a);

		registerFirstState(new UpdateRoundBehaviour(myAgent, players, position,
				atack, fortify), UPDATE);
		registerState(position, POSITION);
		registerState(atack, ATACK);
		registerState(fortify, FORTIFY);
		registerLastState(new FinalBehaviour(players,true,a), FINAL);
		
		registerTransition(UPDATE, FINAL, UpdateRoundBehaviour.FINAL);
		registerTransition(UPDATE, POSITION, UpdateRoundBehaviour.CONT);
		//registerDefaultTransition(UPDATE, POSITION);
		registerDefaultTransition(POSITION, ATACK);
		registerDefaultTransition(ATACK, FORTIFY);
		registerDefaultTransition(FORTIFY, UPDATE);

	}
}
