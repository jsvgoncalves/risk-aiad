package cli;

import gui.BoardGUI;
import gui.FileExporter;
import gui.GameStartGUI;
import gui.StatsGUI;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

import agents.AgressiveAgent;
import agents.DeliberativeAgent;
import agents.GameAgent;
import agents.HumanAgent;
import agents.RandomAgent;
import agents.ReactiveAgent;
import util.R;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class Launcher {
	
	static Runtime runtime;
	private static AgentContainer container;
	protected static GameAgent gameAgent;
	private static JFrame configFrame;
	private static JFrame statsFrame;
	private static JFrame gameFrame;
	private static JFrame fileFrame;
	
	public static void main(String[] args) {
		
		try { // Set System L&F 
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) {
			// handle exception
		}
		GameAgent gameAgent = setupJADE();

		configGame();
		

	}

	private static void configGame() {
		configFrame = new JFrame("RISK");
		configFrame.setSize(new Dimension(400, 400));
		configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final GameStartGUI startGameGui = new GameStartGUI();
		configFrame.add(startGameGui);
		configFrame.setVisible(true);
	}

	private static GameAgent setupJADE() {
		runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(R.GUI_CONFIG, R.GUI_OFF);
		profile.setParameter(R.PORT_CONFIG, R.PORT);
		container = runtime.createMainContainer(profile);
		
		gameAgent = new GameAgent();
		try {
			container.acceptNewAgent("Board", gameAgent).start();
		} catch (StaleProxyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return gameAgent;
	}
	
	public static void startGame(ArrayList<String> agentTypes, String filePrefix) {
		createStatsFrame();
		createGameFrame();
		createFilePrinter(filePrefix);
		
		try {
			ArrayList<String> names = util.NameGenerator.randomName(agentTypes.size());
			for (int i = 0; i < agentTypes.size(); i++) {
				addAgent(names.get(i), agentTypes.get(i));
			}
			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		gameFrame.setVisible(true);
		statsFrame.setVisible(true);
		configFrame.setVisible(false);
	}

	private static void createFilePrinter(String filePrefix) {
		fileFrame = new JFrame("RISK");
		fileFrame.setSize(new Dimension(StatsGUI.PANEL_WIDTH, StatsGUI.PANEL_HEIGHT));
		fileFrame.setMinimumSize(new Dimension(StatsGUI.PANEL_WIDTH, StatsGUI.PANEL_HEIGHT));
		fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final FileExporter gui = new FileExporter(gameAgent, filePrefix);
		fileFrame.add(gui);
		fileFrame.setVisible(false);
		
	}

	private static void createStatsFrame() {
		statsFrame = new JFrame("RISK");
		statsFrame.setSize(new Dimension(StatsGUI.PANEL_WIDTH, StatsGUI.PANEL_HEIGHT));
		statsFrame.setMinimumSize(new Dimension(StatsGUI.PANEL_WIDTH, StatsGUI.PANEL_HEIGHT));
		statsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final StatsGUI gui = new StatsGUI(gameAgent);
		statsFrame.add(gui);
		
	}

	private static void createGameFrame() {
		gameFrame = new JFrame("RISK");
		gameFrame.setSize(new Dimension(BoardGUI.PANEL_WIDTH, BoardGUI.PANEL_HEIGHT));
		gameFrame.setMinimumSize(new Dimension(BoardGUI.PANEL_WIDTH, BoardGUI.PANEL_HEIGHT));
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final BoardGUI gui = new BoardGUI(gameAgent);
		gameFrame.add(gui);
		
	}

	private static void addAgent(String name, String type) throws StaleProxyException {
		if(type.equals("Random")) {
			container.acceptNewAgent(name + "-Rand", new agents.PlayerAgent(new RandomAgent())).start();
		} else if(type.equals("Human")) {
			container.acceptNewAgent(name, new agents.PlayerAgent(new HumanAgent())).start();
		} else if(type.equals("Agressive")) {
			container.acceptNewAgent(name + "-A", new agents.PlayerAgent(new AgressiveAgent())).start();
		} else if(type.equals("Reactive")) {
			container.acceptNewAgent(name + "-React", new agents.PlayerAgent(new ReactiveAgent(5))).start();
		} else if(type.equals("Deliberative")){
			container.acceptNewAgent(name + "-D", new agents.PlayerAgent(new DeliberativeAgent())).start();
		}
	}

}
