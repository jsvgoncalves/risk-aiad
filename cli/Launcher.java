package cli;

import gui.BoardGUI;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import agents.HumanAgent;
import agents.RandomAgent;
import util.R;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static void main(String[] args) {
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(R.GUI_CONFIG, R.GUI_ON);
		profile.setParameter(R.PORT_CONFIG, R.PORT);
		jade.wrapper.AgentContainer container = runtime
				.createMainContainer(profile);
		try {
			ArrayList<String> names = util.NameGenerator.randomName(3);
			container.acceptNewAgent(names.get(0), new agents.PlayerAgent(new HumanAgent()))
					.start();
			container.acceptNewAgent(names.get(1), new agents.PlayerAgent(new RandomAgent()))
					.start();
			container.acceptNewAgent(names.get(2), new agents.PlayerAgent(new RandomAgent()))
					.start();

			container.acceptNewAgent("Board", new agents.GameAgent()).start();

			container.acceptNewAgent(names.get(3), new agents.PlayerAgent(new RandomAgent()))
					.start();
			container.acceptNewAgent(names.get(4), new agents.PlayerAgent(new RandomAgent()))
					.start();
			container.acceptNewAgent(names.get(5), new agents.PlayerAgent(new RandomAgent()))
					.start();

		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		JFrame f = new JFrame("RISK");
		f.setSize(new Dimension(BoardGUI.PANEL_WIDTH, BoardGUI.PANEL_HEIGHT));
		f.setMinimumSize(new Dimension(BoardGUI.PANEL_WIDTH, BoardGUI.PANEL_HEIGHT));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new BoardGUI());
		
		f.setVisible(true);

	}

}
