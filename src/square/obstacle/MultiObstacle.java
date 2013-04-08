package square.obstacle;


import java.util.ArrayList;

import square.Square;

/**
 * Super class where any obstacle grid inherits its general properties from.
 * Such as a wall or a light trail
 * A player cannot move through an obstacle.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public abstract class MultiObstacle implements Obstacle {
	
	/**
	 * An obstacle may cover a set of squares.
	 */
	private ArrayList<Square> squares; 
	
	public MultiObstacle(){
		squares = new ArrayList<Square>();
	}
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
	@Override
	public boolean contains(Square square){
		return this.squares.contains(square);
	}
	
	/**
	 * Adds a square to the obstacle.
	 * 
	 * @param square
	 * 
	 * @throws IllegalArgumentException If a duplicate square is given an exception is thrown.
	 */
	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
		if(isValidSquare(square)){
			getSquares().add(square);
			square.setObstacle(this);
		}else{
			throw new IllegalArgumentException("The square is invalid.");
		}
	}
	
	/**
	 * Removes a square of the obstacle.
	 * 
	 * @param 	square
	 * 
	 * @throws 	IllegalArgumentException 
	 * 			If the square is not 
	 */
	@Override
	public void removeSquare(Square square) throws IllegalArgumentException {
		if(!getSquares().contains(square)){
			throw new IllegalArgumentException("A square that is not added can not part of the obstacle cannot be removed.");
		}else{
			square.setObstacle(null);
			getSquares().remove(square);
		}
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
	@Override
	public boolean isValidSquare(Square square){
		if(square == null)
			return false; 
		if(getSquares().contains(square))
			return false;
		return true;
	}
	
	/**
	 * Get the current length of the obstacle.
	 */
	public int getLength() {
		return getSquares().size();
	}
	
	
}