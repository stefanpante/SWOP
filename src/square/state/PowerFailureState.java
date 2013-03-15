package square.state;

import square.Square;

/**
 * This state indicates that a square is experiencing a power failure.
 * Implemented as a singleton.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PowerFailureState extends State{

	// eager singleton implementation
	public static int TURNS_ACTIVE = 3;
	private static final PowerFailureState POWERFAILURE = new PowerFailureState();
	private PowerFailureState(){
		
	}
	
	/**
	 * Returns the number of actions a player will use if he enters the square with a
	 * PowerFailureState.
	 */
	public int getPenalty() {
		return -1;
	}

	/**
	 * Returns whether the current state causes a penalty
	 */
	public boolean hasPenalty() {
		return true;
	}
	/**
	 * Returns the single instance of this PowerFailure
	 * @return a singleton representing a powerfailure
	 */
	public static PowerFailureState getInstance(){
		return POWERFAILURE;
	}
	
	
	/**
	 * returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return super.toString() + "Power Failed";
	}
	
}
