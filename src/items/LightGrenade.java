package items;

import square.Penalty;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LightGrenade extends Item implements Penalty{
	
	/**
	 * returns the number of actions the player loses
	 *  when he comes in contact with a lightgrenade.
	 */
	public int getPenalty() {
		return -3;
	}

	/**
	 * returns if this object has a penalty.
	 */
	public boolean hasPenalty() {
		return true;
	}
	
	/**
	 * Returns the string representation of this LightGrenade
	 */
	@Override
	public String toString() {
		return "LightGrenade";
	}
}
