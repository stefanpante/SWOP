package grid.obstacles;

import grid.core.Square;

import java.util.ArrayList;

/**
 * Super class where any obstacle grid inherits its general properties from.
 * Such as a wall or a light trail
 * A player cannot move through an obstacle.
 * 
 * @author vincentreniers
 */
public abstract class Obstacle {
	
	/**
	 * An obstacle may cover a set of squares.
	 */
	protected ArrayList<Square> squares = new ArrayList<Square>();
	
	/**
	 * Returns a list of squares which the obstacle covers.
	 */
	public ArrayList<Square> getSquares() {
		return this.squares;
	}
	
	/**
	 * Checks if a square is contained in the obstacle.
	 * 
	 * @param	square	Square to check if it is contained.
	 * @return	True	If square is contained.
	 * 			False	If square is not contained.
	 */
	public boolean contains(Square square){
		return this.squares.contains(square);
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
			getSquares().add(square);
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
