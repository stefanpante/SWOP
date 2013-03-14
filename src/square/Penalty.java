package square;

/**
 * 
 * @author Dieter
 *
 */
public interface Penalty {
	
	/**
	 * Returns number of action the player loses.
	 * 	Or zero if no actions should be lost.	
	 */
	public abstract int getPenalty();
	
	/**
	 * Returns whether there is an extra penalty involved.
	 */
	public abstract boolean hasPenalty();
}
