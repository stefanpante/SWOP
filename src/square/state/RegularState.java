package square.state;


/**
 * Class representing a regular state.
 * Implemented with a singleton
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class RegularState extends SquareState{

	// eager implementation of singleton
	private static final RegularState REGULAR = new RegularState();
	
	
	private RegularState(){
		super();
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
	public boolean hasEffect() {
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
