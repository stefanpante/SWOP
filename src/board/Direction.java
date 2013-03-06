package board;

import java.util.ArrayList;

import utils.Coordinate2D;

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
		
		@Override
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return  new Coordinate2D(c.getX(), c.getY() - 1);
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
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX() -1, c.getY() - 1);
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
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX() -1, c.getY());
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
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX()  -1, c.getY() + 1);
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
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX(), c.getY() + 1);
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
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX() + 1, c.getY() + 1);
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
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX() + 1, c.getY());
		}
	},	NORTHWEST {
		@Override
		public Direction opposite() {
			return Direction.SOUTHEAST;
		}

		@Override
		public ArrayList<Direction> neighborDirections() {
			ArrayList<Direction> neighborDirections = new ArrayList<Direction>();
			neighborDirections.add(Direction.NORTH);
			neighborDirections.add(Direction.WEST);
			return neighborDirections;
		}

		@Override
		public boolean isDiagonal() {
			return true;
		}
		
		public Coordinate2D getNeighborCoordinate(Coordinate2D c){
			return new Coordinate2D(c.getX()  + 1, c.getY() - 1);
		}
	};
	
	/**
	 * Returns the opposite direction of the direction on which it is called.
	 * @return returns the direction opposite of this direction.
	 */
	abstract public Direction opposite();
	
	abstract public ArrayList<Direction> neighborDirections();
	
	abstract public Coordinate2D getNeighborCoordinate(Coordinate2D c);
	
	abstract public boolean isDiagonal();
}
