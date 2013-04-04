package square.state;

import effect.Effect;
import square.Square;

/**
 * Describes the state of a square.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public abstract class SquareState implements Effect {

	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Square State: ";
	}
	
}
