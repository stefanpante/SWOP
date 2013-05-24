package util;

import org.jetbrains.annotations.NotNull;

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
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.SOUTH;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.NORTHWEST);
			neighborDirections.add(Direction.NORTHEAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	NORTHEAST{
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.SOUTHWEST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.NORTH);
			neighborDirections.add(Direction.EAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	EAST {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.WEST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.NORTHEAST);
			neighborDirections.add(Direction.SOUTHEAST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	SOUTHEAST {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.NORTHWEST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.EAST);
			neighborDirections.add(Direction.SOUTH);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	SOUTH {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.NORTH;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.SOUTHEAST);
			neighborDirections.add(Direction.SOUTHWEST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	SOUTHWEST {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.NORTHEAST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.WEST);
			neighborDirections.add(Direction.SOUTH);
			return neighborDirections;
		}
		
		@Override
		public boolean isDiagonal() {
			return true;
		}
	},	WEST {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.EAST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
			neighborDirections.add(Direction.SOUTHWEST);
			neighborDirections.add(Direction.NORTHWEST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return false;
		}
	},	NORTHWEST {
		@NotNull
        @Override
		public Direction opposite() {
			return Direction.SOUTHEAST;
		}

		@NotNull
        @Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<>();
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
	@NotNull
    abstract public Direction opposite();

    /**
     * Returns the neighboring directions of the direction on which the method is called.
     *  At index 0 the direction going counterclockwise (turning left).
     *  At index 1 the direction going clockwise (turning right).
     *
     * @return  A list of neighbors
     */
	@NotNull
    abstract public ArrayList<Direction> neighborDirections();
	
	abstract public boolean isDiagonal();

	/**
	 * Either North or East representing resp. vertical and horizontal orientation.
	 * 
	 * @return	North or East with a 50%.
	 */
	@NotNull
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