package userbehaviours;

import java.util.ArrayList;

public class UserBehaviours {

	public static final int MIN = 0;
	public static final int MED = 1;
	public static final int MAX = 2;

	public static final int FEW = 3;
	public static final int MEAN = 4;
	public static final int LOT = 5;

	public static final int BACK = 6;
	public static final int ALONE = 7;

	public int min_back, max_back;
	public int min_alone, max_alone;
	public double med_alone, med_back;

	public Range range_alone, range_back;

	public UserBehaviours(ArrayList<User> users) {
		min_back = Integer.MAX_VALUE;
		med_back = 0;
		max_back = Integer.MIN_VALUE;

		min_alone = Integer.MAX_VALUE;
		med_alone = 0;
		max_alone = Integer.MIN_VALUE;
		initialize(users);
	}

	/**
	 * Goes through all users and calculates, min, med and max values
	 * 
	 * @param users
	 */
	private void initialize(ArrayList<User> users) {

		if (users.size() == 0) {
			min_alone=-1;
			min_back=-1;
			med_alone = 0;
			med_back=0;
			max_alone=1;
			max_back=1;
			
			range_alone = new Range(min_alone, med_alone, max_alone);
			range_back = new Range(min_back, med_back, max_back);
			
			return;
		}
		
		int sum_alone = 0;
		int sum_back = 0;

		for (User user : users) {
			if (user.getAtackAlone() < min_alone)
				min_alone = user.getAtackAlone();
			if (user.getAtackBack() < min_back)
				min_back = user.getAtackBack();

			if (user.getAtackAlone() > max_alone)
				max_alone = user.getAtackAlone();
			if (user.getAtackBack() > max_back)
				max_back = user.getAtackBack();

			sum_alone += user.getAtackAlone();
			sum_back += user.getAtackBack();
		}

		med_alone = sum_alone / users.size();
		med_back = sum_back / users.size();

		range_alone = new Range(min_alone, med_alone, max_alone);
		range_back = new Range(min_back, med_back, max_back);
		
		updateUsers(users);
	}

	/**
	 * Updates the value in user given the new stats
	 * @param users
	 */
	private void updateUsers(ArrayList<User> users) {
		for(User user: users){
			user.setValue_alone(range_alone.getRange(user.getAtackAlone()));
			user.setValue_back(range_back.getRange(user.getAtackBack()));
		}
		
	}

	public boolean isAgressive(User user) {
		if(range_alone.getRange(user.getValue_alone()) == LOT)
			return true;
		return false;
	}
	
	public boolean isReactive(User user){
		if(range_alone.getRange(user.getValue_alone()) == FEW && 
				range_back.getRange(user.getValue_back()) == LOT)
			return true;
		return false;
	}
	
	public boolean isRandom(User user){
		if( !isAgressive(user) && !isReactive(user))
			return true;
		
		return false;
	}
}
