package board.obstacles;

import board.Square;

/**
 * 
 * Represents the building block of a wall
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class Brick extends Square {
	
	public Brick(){
		super();
	}
	
	/**
	 * Returns if this Square is obstructed
	 * @return
	 */
	@Override
	public boolean isObstructed(){
		return true;
	}

}
