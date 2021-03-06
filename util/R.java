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
	public static final String CONTINUE_ACTION = "actions.ContinueAction";
	public static final Object BOARD_CLASS = "logic.Board";
	public static final Object PERCEPTION_CLASS = "perceptions.Perception";
	public static final String PERCEPTION_ATACK = "perceptions.AtackPerception";
	public static final String PERCEPTION_FORTIFY = "perceptions.FortifyPerception";
	
	// Territory keys

	// EU
	public static final String EU_IBE_KEY = "EU_IBE";
	public static final String EU_FR_KEY = "EU_FR";
	public static final String EU_IT_KEY = "EU_IT";
	public static final String EU_CEN_KEY = "EU_CEN";
	public static final String EU_BAL_KEY = "EU_BAL";
	public static final String EU_BRI_KEY = "EU_BRI";
	public static final String EU_ICE_KEY = "EU_ICE";
	public static final String EU_SCA_KEY = "EU_SCA";
	public static final String EU_EAS_KEY = "EU_EAS";
	public static final String EU_RUS_KEY = "EU_RUS";
	
	// AS
	public static final String AS_YAM_KEY = "AS_YAM";
	public static final String AS_STA_KEY = "AS_STA";
	public static final String AS_MEA_KEY = "AS_MEA";
	public static final String AS_ARA_KEY = "AS_ARA";
	public static final String AS_SAK_KEY = "AS_SAK";
	public static final String AS_MON_KEY = "AS_MON";
	public static final String AS_CHI_KEY = "AS_CHI";
	public static final String AS_IND_KEY = "AS_IND";
	public static final String AS_INO_KEY = "AS_INO";
	public static final String AS_MAC_KEY = "AS_MAC";
	public static final String AS_CHU_KEY = "AS_CHU";
	public static final String AS_JAP_KEY = "AS_JAP";
	
	// OC
	public static final String OC_BOR_KEY = "OC_BOR";
	public static final String OC_EAS_KEY = "OC_EAS";
	public static final String OC_OUT_KEY = "OC_OUT";
	public static final String OC_AUS_KEY = "OC_AUS";
	public static final String OC_NEW_KEY = "OC_NEW";
	
	// AN
	public static final String AN_WIL_KEY = "AN_WIL";
	public static final String AN_QUE_KEY = "AN_QUE";
	public static final String AN_MAR_KEY = "AN_MAR";
	
	// AF
	public static final String AF_NIL_KEY = "AF_NIL";
	public static final String AF_THE_KEY = "AF_THE";
	public static final String AF_CAP_KEY = "AF_CAP";
	public static final String AF_MAD_KEY = "AF_MAD";
	public static final String AF_ZAI_KEY = "AF_ZAI";
	public static final String AF_MAG_KEY = "AF_MAG";
	
	// SA
	public static final String SA_PAR_KEY = "SA_PAR";
	public static final String SA_AMA_KEY = "SA_AMA";
	public static final String SA_BOL_KEY = "SA_BOL";
	public static final String SA_ARG_KEY = "SA_ARG";
	public static final String SA_PER_KEY = "SA_PER";
	public static final String SA_COL_KEY = "SA_COL";
	
	// NA
	public static final String NA_MEX_KEY = "NA_MEX";
	public static final String NA_CAR_KEY = "NA_CAR";
	public static final String NA_SUN_KEY = "NA_SUN";
	public static final String NA_PAC_KEY = "NA_PAC";
	public static final String NA_PLA_KEY = "NA_PLA";
	public static final String NA_EAS_KEY = "NA_EAS";
	public static final String NA_LAW_KEY = "NA_LAW";
	public static final String NA_NUN_KEY = "NA_NUN";
	public static final String NA_KLO_KEY = "NA_KLO";
	public static final String NA_GRE_KEY = "NA_GRE";
	
	public static final String NORTH_AMERICA = "NA";
	public static final String SOUTH_AMERICA = "SA";
	public static final String AFRICA = "AF";
	public static final String ASIA = "AS";
	public static final String ANTARTICA = "AN";
	public static final String OCEANIA = "OC";
	public static final String EUROPE = "EU";
	public static final int NUM_NORTH_AMERICA=5;
	public static final int NUM_SOUTH_AMERICA=3;
	public static final int NUM_AFRICA=3;
	public static final int NUM_ASIA=8;
	public static final int NUM_ANTARTICA=1;
	public static final int NUM_OCEANIA=2;
	public static final int NUM_EUROPE=5;
	public static final int NUM_TERR_NORTH_AMERICA=5;
	public static final int NUM_TERR_SOUTH_AMERICA=3;
	public static final int NUM_TERR_AFRICA=3;
	public static final int NUM_TERR_ASIA=8;
	public static final int NUM_TERR_ANTARTICA=1;
	public static final int NUM_TERR_OCEANIA=2;
	public static final int NUM_TERR_EUROPE=5;
	
	
}
