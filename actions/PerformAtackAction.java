package actions;

import java.io.Serializable;

public class PerformAtackAction extends AtackAction implements Serializable {

	private String from,to;
	
	public PerformAtackAction(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getTo(){
		return to;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5582450723722582658L;

}
