/**
 * 
 */
package grid;

import java.util.ArrayList;

/**
 * @author jonas
 *
 */
public class LightTrail {
	
	public static final int LENGTH = 2;
	
	ArrayList<Square> squares;
	
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
