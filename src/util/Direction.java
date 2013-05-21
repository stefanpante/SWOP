package util;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is used for scenarios where the player wants to move into a certain direction
 * or for general situations where a direction is needed.
 *
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 * */
public enum Direction {
	NORTH {
		@Override
		public Direction opposite() {
			return Direction.SOUTH;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.NORTHWEST);
			neighborDirections.add(Direction.NORTHEAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	NORTHEAST{
		@Override
		public Direction opposite() {
			return Direction.SOUTHWEST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.NORTH);
			neighborDirections.add(Direction.EAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	EAST {
		@Override
		public Direction opposite() {
			return Direction.WEST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.NORTHEAST);
			neighborDirections.add(Direction.SOUTHEAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	SOUTHEAST {
		@Override
		public Direction opposite() {
			return Direction.NORTHWEST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.EAST);
			neighborDirections.add(Direction.SOUTH);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	SOUTH {
		@Override
		public Direction opposite() {
			return Direction.NORTH;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.SOUTHEAST);
			neighborDirections.add(Direction.SOUTHWEST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	SOUTHWEST {
		@Override
		public Direction opposite() {
			return Direction.NORTHEAST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.WEST);
			neighborDirections.add(Direction.SOUTH);
			return neighborDirections;
		}
		
		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	WEST {
		@Override
		public Direction opposite() {
			return Direction.EAST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.SOUTHWEST);
			neighborDirections.add(Direction.NORTHWEST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	NORTHWEST {
		@Override
		public Direction opposite() {
			return Direction.SOUTHEAST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
            neighborDirections.add(Direction.WEST);
            neighborDirections.add(Direction.NORTH);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
	};
	
	/**
	 * Returns the opposite direction of the direction on which it is called.
	 * @return returns the direction opposite of this direction.
	 */
	abstract public Direction opposite();

    /**
     * Returns the neighboring directions of the direction on which the method is called.
     *  At index 0 the direction going counterclockwise (turning left).
     *  At index 1 the direction going clockwise (turning right).
     *
     * @return
     */
	abstract public ArrayList<Direction> neighborDirections();
	
	abstract public boolean isDiagonal();

	/**
	 * Either North or East representing resp. vertical and horizontal orientation.
	 * 
	 * @return	North or East with a 50%.
	 */
	public static Direction getRandomOrientation() {
		Random random = new Random();
		if(random.nextBoolean())
			return Direction.NORTH;
		return Direction.EAST;
	}
	
	
	/**
	 * Returns a random direction with an even chance.
	 */
	public static Direction getRandomDirection() {
		Random random = new Random();
		Direction[] dir = values();
		return dir[random.nextInt(dir.length)];
	}
	

}