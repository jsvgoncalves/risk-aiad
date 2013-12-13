package util;

import perceptions.FortifyPerception;

public class PossibleToAtack {

	private String from;
	private String to;
	private FortifyPerception p;

	public PossibleToAtack(String from, String to, FortifyPerception p){
		this.setFrom(from);
		this.setTo(to);
		this.setPerception(p);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public FortifyPerception getPerception() {
		return p;
	}

	public void setPerception(FortifyPerception p) {
		this.p = p;
	}
	
	
}
