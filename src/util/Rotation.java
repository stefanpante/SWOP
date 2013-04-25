package util;

import java.util.Random;

/**
 * Rotation of a power failure.
 * 
 * @author vincent
 */
public enum Rotation {
	CLOCKWISE,
	COUNTERCLOCKWISE;
	
	/**
	 * Returns a random rotation.
	 */
	public static Rotation random() {
		Random random = new Random();
		
		if(random.nextInt(2) == 0)
			return Rotation.CLOCKWISE;
		else
			return Rotation.COUNTERCLOCKWISE;
	}
}
