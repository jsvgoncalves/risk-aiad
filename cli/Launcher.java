package cli;

import java.util.ArrayList;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static void main(String[] args) {
		jade.core.Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter("gui", "true");
		jade.wrapper.AgentContainer container = runtime.createMainContainer(profile);
		try {
			ArrayList<String> names = util.NameGenerator.randomName(3);
			container.acceptNewAgent(names.get(0), new agents.PlayerAgent()).start();
			container.acceptNewAgent(names.get(1), new agents.PlayerAgent()).start();
			container.acceptNewAgent(names.get(2), new agents.PlayerAgent()).start();

			container.acceptNewAgent("Board", new agents.GameAgent()).start();

			container.acceptNewAgent(names.get(3), new agents.PlayerAgent()).start();
			container.acceptNewAgent(names.get(4), new agents.PlayerAgent()).start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

	}

}
