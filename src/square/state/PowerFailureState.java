package square.state;

import square.Square;

/**
 * This state indicates that a square is experiencing a power failure.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PowerFailureState extends State{

	// eager singleton implementation
	public static int TURNS_ACTIVE = 3;
	private static final PowerFailureState POWERFAILURE = new PowerFailureState();
	private PowerFailureState(){
		
	}
	public int getPenalty() {
		return -1;
	}

	public boolean hasPenalty() {
		return true;
	}
	/**
	 * Returns the single instance of this PowerFailure
	 * @return
	 */
	public static PowerFailureState getInstance(){
		return POWERFAILURE;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Power Failed";
	}
	
}
