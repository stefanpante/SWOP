package square;

/**
 * This is used for scenarios where the player wants to move into a certain direction
 * or for general situations where a direction is needed.
 *
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 * */
public enum Direction {
	NORTH {
		@Override
		public Direction opposite() {
			return Direction.SOUTH;
		}
	},	NORTHEAST{
		@Override
		public Direction opposite() {
			return Direction.SOUTHWEST;
		}
	},	EAST {
		@Override
		public Direction opposite() {
			return Direction.WEST;
		}
	},	SOUTHEAST {
		@Override
		public Direction opposite() {
			return Direction.NORTHWEST;
		}
	},	SOUTH {
		@Override
		public Direction opposite() {
			return Direction.NORTH;
		}
	},	SOUTHWEST {
		@Override
		public Direction opposite() {
			return Direction.NORTHEAST;
		}
	},	WEST {
		@Override
		public Direction opposite() {
			return Direction.EAST;
		}
	},	NORTHWEST {
		@Override
		public Direction opposite() {
			return Direction.SOUTHEAST;
		}
	};
	
	/**
	 * Returns the opposite direction of the direction on which it is called.
	 * @return
	 */
	abstract public Direction opposite();
}
