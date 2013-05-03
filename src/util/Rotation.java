package util;

import java.util.Random;

import square.Direction;

/**
 * Rotation of a power failure.
 * 
 * @author vincent
 */
public enum Rotation {
	CLOCKWISE {
		@Override
		public Direction rotate(Direction direction) {
			switch(direction) {
				case EAST:
					return Direction.SOUTHEAST;
				case SOUTHEAST:
					return Direction.SOUTH;
				case SOUTH:
					return Direction.SOUTHWEST;
				case SOUTHWEST:
					return Direction.WEST;
				case WEST:
					return Direction.NORTHWEST;
				case NORTHWEST:
					return Direction.NORTH;
				case NORTH:
					return Direction.NORTHEAST;
				case NORTHEAST:
					return Direction.EAST;
			}
			
			return null;
		}
	},
	COUNTERCLOCKWISE {
		@Override
		public Direction rotate(Direction direction) {
			switch(direction) {
			case EAST:
				return Direction.NORTHEAST;
			case NORTHEAST:
				return Direction.NORTH;
			case NORTH:
				return Direction.NORTHWEST;
			case NORTHWEST:
				return Direction.WEST;
			case WEST:
				return Direction.SOUTHWEST;
			case SOUTHWEST:
				return Direction.SOUTH;
			case SOUTH:
				return Direction.SOUTHEAST;
			case SOUTHEAST:
				return Direction.EAST;
		}
		
		return null;
		}
	};
	
	/**
	 * Given a direction it will rotate into a new direction.
	 * 
	 * @param direction   the direction
	 */
	public abstract Direction rotate(Direction direction);
	
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
