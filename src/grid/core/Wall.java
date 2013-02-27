package grid.core;

import grid.obstacles.Obstacle;

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
	
	/**
	 * Adds a square to the wall.
	 * 
	 * @param square
	 * 
	 * @throws IllegalSquareException If a duplicate square is given an exception is thrown.
	 */
	public void addSquare(Square square) throws IllegalArgumentException {
		if(isValidSquare(square))
			squares.add(square);
		else
			throw new IllegalArgumentException("The square is already contained in the wall.");
	}
	
	/**
	 * A given square is invalid if the square is already contained in the wall.
	 * The wall may not contain duplicates.
	 * 
	 * @param square	The square that needs to be checked if it is valid.
	 * @return	True	If the square is not yet contained.
	 * 			False	If the square is already contained.
	 */
	public boolean isValidSquare(Square square) {
		if(squares.contains(square))
			return false;
		else
			return true;		
	}
	
}
