package grid.obstacles;

import grid.core.Square;

import java.util.ArrayList;

/**
 * Super class where any obstacle grid inherits its general properties from.
 * Such as a wall or a light trail.
 * A player cannot move through an obstacle.
 * 
 * @author vincentreniers
 */
public class Obstacle {
	
	/**
	 * An obstacle may cover a set of squares.
	 */
	protected ArrayList<Square> squares;
	
	/**
	 * Returns a list of squares which the obstacle covers.
	 */
	public ArrayList<Square> getSquares() {
		return this.squares;
	}
	
}
