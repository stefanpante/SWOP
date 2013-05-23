package square.multi;

import java.util.LinkedList;
import square.Square;
import square.field.Field;

/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class LightTrail extends Field {

	private LinkedList<Square> trailqueue;
	
	/**
	 * Maximum length of a LightTrail.
	 */
	public static final int MAX_LENGTH = 3;
	
	/**
	 * Initialises a new linkedList for the LightTrail.
	 */
	public LightTrail() {
		super();
		trailqueue  = new LinkedList<Square>();
	}
	
	/**
	 * Extend the light trail by adding a new square to the trail.
	 * 
	 * @param 	square 	
	 * 			The square to add to the trail
	 * @throws 	IllegalArgumentException
	 * 			If the square is not valid.
	 */
	@Override
	public void addGridElement(Square square) throws IllegalArgumentException {
		if(!isValidGridElement(square))
			throw new IllegalArgumentException("This square is not valid for this lighttrails");
		
		if(getLength() >= MAX_LENGTH) 
			this.removeGridElement(getLastSquare());
		
		super.addGridElement(square);
		this.trailqueue.add(square);
	}
	
	/**
	 * Removes a square of the obstacle.
	 * 
	 * @param 	square
	 * 
	 * @throws 	IllegalArgumentException 
	 * 			If the square is not valid.
	 */
	@Override
	public void removeGridElement(Square square) throws IllegalArgumentException {
		super.removeGridElement(square);
		trailqueue.remove(square);
	}
	
	/**
	 * Returns the last square that was added in the LightTrail.
	 * 
	 * @return	Square	If the trail is not empty the last one is returned.
	 * 			Null	If the trail is empty.
	 */
	public Square getLastSquare() {
		if(getLength() >= 1)
			return getGridElements().get(0);
		else
			return null;
	}
	
	/**
	 * Returns the newest square which was recently added.
	 * 
	 * @return	Square	If the trail is not empty the newest one is returned.
	 * 			Null	If the trail is empty.
	 */
	public Square getNewestSquare() {
		if(getLength() >= 1)
			return getGridElements().get(getLength() - 1);
		else
			return null;
	}

	public void setHead(Square square) {
		
		if(getNewestSquare() == square)
			this.removeGridElement(getLastSquare());
		else
			this.addGridElement(square);
	}



}