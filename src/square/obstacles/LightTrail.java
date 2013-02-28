package square.obstacles;


import java.util.Observable;
import java.util.Observer;

import square.Square;

/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author jonas, vincent
 */
public class LightTrail extends Obstacle implements Observer{
	
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
	 * Checks if the square is valid. The square must not be a duplicate
	 * and it must be connected to the last square in the LightTrail.
	 * 
	 * @param	Square	The square.
	 * @return	True	If the square is no duplicate and connected to the last
	 * 					square in the lightTrail. If there is at least one square.
	 * 			False	If it is a duplicate or not connected to the last square
	 * 					in the trail. If there is at least one square.
	 */
	@Override
	public boolean isValidSquare(Square square) {
		if(!super.isValidSquare(square))
			return false;
		
		if(getLength() == 0)
			return true;
		
		if(getLastSquare().connectedTo(square))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns the last square that was added in the LightTrail.
	 * 
	 * @return	Square	If the trail is not empty the last one is returned.
	 * 			Null	If the trail is empty.
	 */
	public Square getLastSquare() {
		if(getLength() >= 1)
			return getSquares().get(0);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
