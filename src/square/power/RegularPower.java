package square.power;

public class RegularPower extends Power{
	
	public static int TURNS = Integer.MAX_VALUE;
	
	public static int ACTIONS = Integer.MAX_VALUE;

	public RegularPower() {
		super(TURNS, ACTIONS);
	}
	
	public boolean isFailing() {
		return false;
	}

}
