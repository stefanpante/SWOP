package square.state;

import penalty.Penalty;
import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public abstract class SquareState implements Penalty {

	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Square State: ";
	}
}
