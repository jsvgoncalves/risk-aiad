package userbehaviours;

public class User {

	private int atacksBack, atacksAlone, value_back, value_alone;
	private String name;
	
	public User(String name){
		this.name=name;
		atacksBack=0;
		atacksAlone=0;
		value_back=UserBehaviours.MED;
		value_alone=UserBehaviours.MED;
	}
		
	public String getName(){
		return name;
	}
	
	public void addAtackBack(){
		atacksBack++;
	}
	
	public void addAtackAlone(){
		atacksAlone++;
	}
	
	public int getAtackBack(){
		return atacksBack;
	}
	
	public int getAtackAlone(){
		return atacksAlone;
	}

	public int getValue_back() {
		return value_back;
	}

	public void setValue_back(int value) {
		this.value_back = value;
	}

	public int getValue_alone() {
		return value_alone;
	}

	public void setValue_alone(int value_alone) {
		this.value_alone = value_alone;
	}
}
