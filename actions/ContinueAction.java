package actions;

import java.io.Serializable;

public class ContinueAction extends Action implements Serializable {

	private boolean c;
	
	public ContinueAction(String s, boolean c) {
		super(s);
		this.c = c;
	}
	
	public boolean willContinue(){
		return c;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5964656819296518145L;

}
