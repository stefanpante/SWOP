package grid.obstacles;


import grid.core.Square;

/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author jonas, vincent
 */
public class LightTrail extends Obstacle{
	
	public static final int MAX_LENGTH = 3;
		
	/**
	 * Extend the light trail by adding a new square to the trail.
	 * 
	 * @param 	square 
	 * 			The square to add to the trail
	 * @throws 	IllegalArgumentException
	 * 			If the square is not valid.
	 */
	@Override
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException();
		
		if(getLength() >= MAX_LENGTH)
			getSquares().remove(MAX_LENGTH-1);
		
		getSquares().add(0,square);
	}
	
	/**
	 * Check whether the given square can be added to this light trail.
	 * The added square may not already be contained in the existing light trail.
	 * No duplicates may be added.
	 * 
	 * @param 	square
	 * 			The square to be checked
	 * @return	True	if and only if the square is connected to the last square
	 * 					in this light trail.
	 * 			False	If the square is a duplicate or it is not connected to any other
	 * 					square.
	 */
	@Override
	public boolean isValidSquare(Square square){
		if(!super.isValidSquare(square))
			return false;
		
		return true;
	}
	
	/**
	 * Get the current length of the LightTrail.
	 */
	public int getLength() {
		return getSquares().size();
	}
	
}
