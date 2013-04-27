package square.power;

/**
 * This power lasts infinite amount of turns and actions.
 * Untill it may experience a power failure.
 * 
 * @author vincentreniers
 */
public class RegularPower extends Power{
	
	public static int TURNS = Integer.MAX_VALUE;
	
	public static int ACTIONS = Integer.MAX_VALUE;

	public RegularPower() {
		super(TURNS, ACTIONS);
	}
	
	public boolean isFailing() {
		return false;
	}
	
	public void resetCount() {
		
	}

}
