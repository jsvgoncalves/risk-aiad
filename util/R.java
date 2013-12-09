package util;

public class R {

	public final static int TICK_TIME = 1000;
	public final static int MIN_PLAYERS = 5;

	public final static int GAME_WAITING = 0;
	public final static int GAME_LAUNCHING = 1;

	
	// JADE port configuration
	public final static String PORT_CONFIG = "port";
	public final static String PORT = "8000";
	// JADE either to show the JADE GUI
	public final static String GUI_CONFIG = "gui";
	public final static String GUI_ON = "true";
	public final static String GUI_OFF = "false";
	
	//Content of messages
	public static final String SUBSCRIPTION = "subscription";
	public static final String UPDATED = "updated";
	
	public final static String GAME_AGENT = "Game Agent";
	public final static String PLAYER_AGENT = "Player Agent";
	public static final String JOIN = "Join";
	public static final String PLAY = "Play";
	public static final String RECEIVE = "Receive";
	public static final String ATACK = "Atack";
	public static final String FORTIFY = "Fortify";
	public static final String CONTINUE = "Continue";
	public static final String RECEIVE_ACTION = "actions.ReceiveAction";
	public static final String PERFORM_ATACK = "actions.PerformAtackAction";
	public static final String DONT_FORTIFY = "actions.DontFortifyAction";
	public static final String DONT_ATACK = "actions.DontAtackAction";
	public static final String PERFORM_FORTIFICATION = "actions.PerformFortificationAction";
}
