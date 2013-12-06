package actions;

import java.io.Serializable;

public class PerformFortificationAction extends FortifyAction implements Serializable {

	private String from, to;
	private int n;
	
	public PerformFortificationAction(String from, String to, int n) {
		this.from = from;
		this.to = to;
		this.n = n;
	}

	public String getFrom(){
		return from;
	}
	
	public String getTo(){
		return to;
	}
	
	public int getN(){
		return n;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1528085804180457863L;

}
