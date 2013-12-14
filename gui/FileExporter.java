package gui;

import jade.core.AID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import agents.GameAgent;
import logic.Board;

public class FileExporter extends ObserverGUI {
	PrintWriter writer;
	private GameAgent gameAgent;
	private ArrayList<AID> players;
	private String filePrefix;
	
	public FileExporter(GameAgent gameAgent, String prefix) {
		this.gameAgent = gameAgent;
		this.filePrefix = prefix;
		gameAgent.addListener(this);
	}

	private void closeWriter() {
		writer.close();
	}

	private void openWriter() throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(filePrefix + "-result.csv", true)));
	}

	@Override
	public void notifyTurnEnded() {
		printTotalSoldiersCSV();
	}

	@Override
	public void notifyGameStarted() {
		try {
			openWriter();
			writer.print("Round");
			players = gameAgent.getAllAgentNames();
			for (int i = 0; i < players.size(); i++) {
				writer.print("," + players.get(i).getLocalName());
			}
			writer.println();
			closeWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** 
	 * Every round prints a nice CSV formatted output
	 */
	private void printTotalSoldiersCSV() {
		try {
			openWriter();
			Board b = gameAgent.getBoard();
			players = gameAgent.getAllAgentNames();
			writer.print(gameAgent.getCurrentRound());
			for (int i = 0; i < players.size(); i++) {
				writer.print("," + b.getPlayerTotalSoldiers(players.get(i).getLocalName()));
			}
			writer.println("");
			closeWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
