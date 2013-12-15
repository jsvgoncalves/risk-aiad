package userbehaviours;

public class Range {
	
	private double few;
	private double lot;

	/**
	 * Calculates the mean point between min-med and med-max
	 * @param min
	 * @param med
	 * @param max
	 */
	public Range(int min, double med, int max){
		this.few = med - (med - min) / 2;
		this.lot = med + (max - min) / 2;
	}
	
	/**
	 * Returns the value given the actual range
	 * @param n
	 * 			The number from which it is needed to evaluate it's relative value. (Ex: User.atacksBack)
	 * @return
	 * 			Relative value given the range
	 */
	public int getRange(int n){
		if( n < few )
			return UserBehaviours.FEW;
		if( n > lot )
			return UserBehaviours.LOT;
		
		return UserBehaviours.MEAN;
	}
}
