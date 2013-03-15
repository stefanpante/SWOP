package square.state;

import square.Square;


/**
 * Class representing a regular state.
 * Implemented with a singleton
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class RegularState extends State{

	// eager implementation of singleton
	private static final RegularState REGULAR = new RegularState();
	
	
	private RegularState(){
		super();
	}
	
	/**
	 * returns the penalty caused by entering a square with this state.
	 */
	public int getPenalty() {
		return 0;
	}

	/**
	 * Returns the single instance of RegularState
	 * @return a singleton representing RegularState
	 */
	public static RegularState getInstance(){
		return REGULAR;
	}
	
	/**
	 * Returns whether this state has a penalty or not.
	 */
	public boolean hasPenalty() {
		return false;
	}

	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return super.toString() + "Regular";
	}
}
