package square.state;

import square.Square;

/**
 * This state indicates that a square is experiencing a power failure.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PowerFailureState extends State{

	public int getPenalty() {
		return -1;
	}

	public boolean hasPenalty() {
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Power Failed";
	}
	
}
