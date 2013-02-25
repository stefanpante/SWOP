/**
 * 
 */
package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author jonas
 *
 */
public class LightTrail implements Observer {
	
	public static final int LENGTH = 2;
	
	private HashMap<Integer,Square> trail;
	
	/**
	 * Create a new Light Trail Object
	 * 
	 * @param 	o
	 */
	public LightTrail(){
		trail = new HashMap<Integer, Square>();
	}
		
	/**
	 * The counter of this LightTrail object.
	 */
	//TODO: Move this line to the begin of the class.
	private int counter;

	/**
	 * Returns the value of the counter of this LightTrail as an int.
	 *
	 * @return 	An object of the int class.
	 * 			| int
	 */
	public int getCounter() {
		return counter;
	};

	/**
	 * Sets the value of the counter of LightTrail if the given value is valid. 
	 * 
	 * @param 	counter
	 *			The counter to set.
	 * @post 	The given value is the current value of the counter of this LightTrail.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid counter.
	 *			| !isValidCounter(counter)
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
	 * Check whether the given counter is a valid counter for all the objects of LightTrail.
	 * @param 	counter
	 *			The counter to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidCounter(int counter) {
		return counter >= 0;
	}
	
	private void increaseCounter(){
		int newCounter = getCounter() + 1;
		setCounter(newCounter);
	}
	
	private void addToTrail(int id, Square square){
		trail.put(id, square);
	}
	
	private void removeFromTrail(int id){
		trail.remove(id);
	}
	
	/**
	 * Remove the squares which are no longer covered by a light trail 
	 */
	private void clearTrail(){
		int smallestId = getCounter() - LENGTH;
		for(Integer id: trail.keySet()){
			if(id <= smallestId){
				removeFromTrail(id);
			}
		}
	}

	/**
	 * TODO: 
	 */
	@Override
	public void update(Observable o, Object arg) {
		Square square = (Square) arg;
		increaseCounter();
		addToTrail(getCounter(), square);
		clearTrail();
	}
	
	
	
}
