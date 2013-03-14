package square.state;

import square.Penalty;
import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public abstract class State implements Penalty {

	@Override
	public String toString() {
		return "State: ";
	}
}
