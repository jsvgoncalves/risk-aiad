package behaviours.gameagent;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;

public class RequestActionBehaviour extends FSMBehaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 117753136276001826L;

	private static final String REQUEST = "request";
	private static final String CHECK = "check";
	private static final String FINAL = "final";
	private static final String ACTION = "action";
	
	private GameAgentFaseBehaviour b;
	
	public RequestActionBehaviour(GameAgentFaseBehaviour behaviour, ArrayList<AID> players){
		b = behaviour;
		
		b.reset();
		
		FinalBehaviour f = new FinalBehaviour(players);
		
		registerFirstState(b, REQUEST);
		registerState(new CheckEndBehaviour(b), CHECK);
		registerState(new MakeActionBehaviour(b,f), ACTION);
		registerLastState(f, FINAL);
		
		registerTransition(REQUEST, CHECK, 0); //Se acao invalida
		registerTransition(REQUEST, ACTION, 1); //Se acao valida
		registerTransition(CHECK, REQUEST, 0); //Se tem mais tentativas
		registerTransition(CHECK, FINAL, 1); //Se nao tem mais tentativas
		registerDefaultTransition(ACTION, FINAL);
	}

	public void setPlayer(AID aid) {
		b.setPlayer(aid);
	}

}
