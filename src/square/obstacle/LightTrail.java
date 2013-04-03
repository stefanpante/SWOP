package square.obstacle;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import square.Square;

/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class LightTrail extends MultiObstacle implements Observer{

	private Queue<Square> trail;
	
	/**
	 * Maximum length of a LightTrail.
	 */
	public static final int MAX_LENGTH = 3;
	
	/**
	 * Initialises a new linkedList for the LightTrail.
	 */
	public LightTrail() {
		trail  = new LinkedList<Square>();
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
	public void addSquare(Square square) throws IllegalArgumentException {
		if(!isValidSquare(square))
			throw new IllegalArgumentException("This square is not valid for this lighttrails");
		
		if(getLength() >= MAX_LENGTH) 
			this.removeSquare(getLastSquare());
		
		super.addSquare(square);
		this.trail.add(square);
	}
	
	/**
	 * Removes a square of the obstacle.
	 * 
	 * @param 	square
	 * 
	 * @throws 	IllegalArgumentException 
	 * 			If the square is not valid.
	 */
	public void removeSquare(Square square) throws IllegalArgumentException {
		super.removeSquare(square);
		trail.remove(square);
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
	
	/**
	 * Returns the newest square which was recently added.
	 * 
	 * @return	Square	If the trail is not empty the newest one is returned.
	 * 			Null	If the trail is empty.
	 */
	public Square getNewestSquare() {
		if(getLength() >= 1)
			return getSquares().get(getLength() - 1);
		else
			return null;
	}

	/**
	 * Method that gets called when the observable object changes.
	 * 
	 * If the square given is the same as the last square we know
	 * this does not need to be added. But because of an action that
	 * we are notified of, the last square must be removed.
	 */
	public void update(Observable o, Object arg) {
		if(arg !=null){
			Square square = (Square) arg;
			
			if(getNewestSquare() == square)
				this.removeSquare(getLastSquare());
			else
				this.addSquare(square);
		}
	}

	/**
	 * The LightTrail bounces a launchable item back onto the previous square it passed.
	 * 
	 * @return	True always.
	 */
	@Override
	public boolean bouncesBack() {
		return true;
	}
	
}
