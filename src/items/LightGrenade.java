package items;

import square.Penalty;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LightGrenade extends Item implements Penalty{
	
	public int getPenalty() {
		return -3;
	}

	public boolean hasPenalty() {
		return true;
	}
	
	@Override
	public String toString() {
		return "LightGrenade";
	}
}
