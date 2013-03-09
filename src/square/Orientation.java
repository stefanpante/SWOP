package square;

import java.util.Random;

/**
 * Orientation is used to indicate the orientation of an object.
 * For example a wall may be oriented horizontally or vertically.
 * 
 * @author Vincent Reniers
 */

//TODO I suppose this enum is only used for walls add the moment, why should there be a diagonal?
public enum Orientation {
	HORIZONTAL,
	VERTICAL;
	

	/**
	 * Returns a random orientation
	 * @return random orientation 
	 */
	public static Orientation getRandomOrientation(){
		Random ran = new Random();
		Orientation[] or = Orientation.values();
		return or[ran.nextInt(or.length)];
	}
}
