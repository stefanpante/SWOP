package square.obstacles;

import java.util.ArrayList;

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
	 * @param	square First square.
	 * @param	otherSquare Second square.
	 * 
	 * @throws IllegalSquareException If a duplicate square is given an exception is thrown.
	 */
	public Wall(Square square, Square otherSquare) throws IllegalArgumentException {
		this.addSquare(square);
		this.addSquare(otherSquare);
	}
	
	/**
	 * Constructs a wall from an arrayList
	 * @param 	sequence the Sequence of which a wall should be created
	 */
	public Wall(ArrayList<Square> sequence) throws IllegalArgumentException {
		if(sequence.size() < 2) 
			throw new IllegalArgumentException("A wall should have at least two squares");
		for(Square sq: sequence){
			super.addSquare(sq);
		}
	}
}
