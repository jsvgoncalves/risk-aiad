package actions;

import java.io.Serializable;
import java.util.Hashtable;

public class ReceiveAction extends Action implements Serializable {

	private Hashtable<String,Integer> nSoldiersByTerritory = new Hashtable<String,Integer>();
	
	public ReceiveAction() {
	}
	
	public void addSoldiersTerritory(int n, String territoryKey){
		Integer actualN = nSoldiersByTerritory.get(territoryKey);
		
		if(actualN == null )
			nSoldiersByTerritory.put(territoryKey, (Integer)n);
		else
			nSoldiersByTerritory.put(territoryKey, actualN + n);
	}
	
	public Hashtable<String, Integer> getSoldiersByTerritory(){
		return nSoldiersByTerritory;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -428826716024544518L;

}
