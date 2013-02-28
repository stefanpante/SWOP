package square.obstacles;


import java.util.ArrayList;
import java.util.Observer;

import square.Square;

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
	private ArrayList<Square> squares = new ArrayList<Square>();
	
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
	 * Check whether the given square can be added to this obstacle.
	 * The added square may not already be contained in the existing obstacle.
	 * No duplicates may be added.
	 * 
	 * @param 	square
	 * 			The square to be checked
	 * @return	True	if and only if the square is connected to one of the squares.
	 * 			False	If the square is a duplicate or it is not connected to any other
	 * 					square.
	 */
	public boolean isValidSquare(Square square){
		if(getSquares().contains(square))
			return false;
		
		if(getLength() == 0)
			return true;
		
		for(Square trailSquare : getSquares())
			if(trailSquare.connectedTo(square))
				return true;
		
		return false;
	}
	
	/**
	 * Get the current length of the obstacle.
	 */
	public int getLength() {
		return getSquares().size();
	}
	
	
}
