package square.state;

import square.Square;


/**
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class RegularState extends State{

	// eager implementation of singleton
	private static final RegularState REGULAR = new RegularState();
	
	private RegularState(){
		
	}
	public int getPenalty() {
		return 0;
	}

	/**
	 * Returns the single instance of RegularState
	 * @return
	 */
	public static RegularState getInstance(){
		return REGULAR;
	}
	public boolean hasPenalty() {
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "Regular";
	}
}
