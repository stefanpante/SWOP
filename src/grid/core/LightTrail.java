/**
 * 
 */
package grid.core;


import grid.obstacles.Obstacle;

import java.util.ArrayList;


/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author jonas
 */
public class LightTrail extends Obstacle{
	
	public static final int LENGTH = 2;
		
	/**
	 * Extend the light trail by adding a new square to the trail.
	 * 
	 * @param 	square 
	 * 			The square to add to the trail
	 * @throws 	IllegalArgumentException
	 * 			
	 */
	public void addToLightTrail(Square square) throws IllegalArgumentException {
		if(!canAddToLightTrail(square))
			throw new IllegalArgumentException();
		
		squares.remove(LENGTH);
		squares.add(0,square);
	}
	
	/**
	 * Check whether the given square can be added to this light trail.
	 * @param 	square
	 * 			The square to be checked
	 * @return	True
	 * 			True if and only if the square is connected to the last square
	 * 			in this light trail.
	 */
	public boolean canAddToLightTrail(Square square){
		return true;
	}
	
	
	
}
