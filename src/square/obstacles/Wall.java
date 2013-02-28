package square.obstacles;

import square.Square;


/**
 * Wall class
 * 
 * @pre	The squares added to the wall must be different.
 * 
 * @invariant The minimum amount of squares the wall covers is 2.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
public class Wall extends Obstacle{
	
	public static int MIN_SIZE = 2;
	/**
	 * Initialises the wall. Two squares must at least be given for a wall to be possible.
	 * 
	 * @param	Square First square.
	 * @param	Square Second square.
	 * 
	 * @throws IllegalSquareException If a duplicate square is given an exception is thrown.
	 */
	public Wall(Square square, Square otherSquare) throws IllegalArgumentException {
		this.addSquare(square);
		this.addSquare(otherSquare);
	}
}
