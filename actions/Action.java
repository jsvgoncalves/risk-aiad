package actions;

import java.io.Serializable;

public class Action implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6265716266007175290L;

	private String s;
	
	public Action(String s){
		this.s=s;
	}
	
	public String getS(){
		return s;
	}
	
}
