package player;


import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import board.Square;
import board.obstacles.Obstacle;


/**
 * LightTrail is a trail that is left behind the player while he
 * executes actions on the grid. The LightTrail is an obstacle.
 * 
 * @author jonas, vincent
 */
public class LightTrail extends Obstacle implements Observer{
	public static final int LENGTH = 2;

	private HashMap<Integer,Square> trail;
	public static final int MAX_LENGTH = 3;
	
	/**
	 * The counter of this LightTrail object.
	 */
	private int counter;

	
	public LightTrail(){
		trail = new HashMap<Integer,Square>();
		counter = 0;
	}
	
	/**
	 * Returns the value of the counter of this LightTrail as an int.
	 * 
	 * @return An object of the int class. | int
	 */
	public int getCounter() {
		return counter;
	};
	
	/**
	 * Sets the value of the counter of LightTrail if the given value is valid.
	 * 
	 * @param counter
	 *            The counter to set.
	 * @post The given value is the current value of the counter of this
	 *       LightTrail.
	 * @throws IllegalArgumentException
	 *             If the given argument is not a valid counter. |
	 *             !isValidCounter(counter)
	 */
	private void setCounter(int counter) {
		if (!isValidCounter(counter)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ counter
							+ ") is not a valid agrument of the field counter from the class LightTrail");
		}
		this.counter = counter;
	};

	/**
	 * Check whether the given counter is a valid counter for all the objects of
	 * LightTrail.
	 * 
	 * @param counter
	 *            The counter to check.
	 * @return True if and only if the given value is not null, has the correct
	 *         type, ...
	 */
	public static boolean isValidCounter(int counter) {
		return counter >= 0;
	}

	private void increaseCounter() {
		int newCounter = getCounter() + 1;
		setCounter(newCounter);
	}

	private void addToTrail(int id, Square square) {
		if(isValidSquare(square)){
			trail.put(id, square);
			addSquare(square);
		}
	}

	private void removeFromTrail(int id) {
		removeSquare(trail.get(id));
		trail.remove(id);
	}

	/**
	 * Remove the squares which are no longer covered by a light trail
	 */
	private void clearTrail() {
		int smallestId = getCounter() - LENGTH;
		for (Integer id : trail.keySet()) {
			if (id <= smallestId) {
				removeFromTrail(id);
			}
		}
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
			throw new IllegalArgumentException();
		
		if(getLength() >= MAX_LENGTH)
			getSquares().remove(MAX_LENGTH-1);
		
		super.addSquare(square);
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
		
		if(getLastSquare().isConnectedTo(square))
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

	/**
	 * Method that gets called when the observable object changes.
	 */
	public void update(Observable o, Object arg) {
		if(arg !=null){
			Square square = (Square) arg;
			increaseCounter();
			addToTrail(getCounter(), square);
			clearTrail();	
		}
	}
	
}
