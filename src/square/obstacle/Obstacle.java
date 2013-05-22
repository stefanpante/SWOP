package square.obstacle;

import square.Square;

/**
 * Interface that is used for implementing an obstacle.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public interface Obstacle{
	
	/**
	 * Checks if a square is contained in the obstacle.
	 * 
	 * @param	square	
	 * 			Square to check if it is contained.
	 * @return	True	If square is part of this obstacle.
	 * 			False	If square is not part of this obstacle.
	 */
	public boolean contains(Square square);
	
	
	/**
	 * Adds a square to the obstacle.
	 * 
	 * @param 	square
	 * 			The square to add.
	 * @throws 	IllegalArgumentException
	 * 			If the given square cannot be added to the obstacle.
	 */
	public void addSquare(Square square) throws IllegalArgumentException;
	
	
	/**
	 * Removes a square from the obstacle if possible. 
	 * 
	 * @param 	square
	 * 			The square to remove. 
	 * @throws 	IllegalArgumentException
	 * 			When the square cannot be removed.
	 */
	public void removeSquare(Square square) throws IllegalArgumentException;
	
	/**
	 * Returns whether the given square is a valid square for this object.
	 * 
	 * @param 	square
	 * 			The square to check.
	 * @return	True if the square can be added to this obstacle.
	 */
	public boolean isValidSquare(Square square);
		
	/**
	 * Returns whether the obstacle bounces a launchable item 
	 * 	back onto the previous square it passed or not. 
	 * 
	 * @return	True if the obstacle bounces the item back.
	 */
	public boolean bouncesBack();

    public boolean preventsField();
}
