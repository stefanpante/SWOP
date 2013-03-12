package utils;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

import square.Direction;

/**
 * Coordinate2D represents a point in 2D space, 
 * has an x-coordinate and y-coordinate
 * 
 * (0,0) by convention is the most left upper square.
 * 
 * @author Jonas Devlieghere, Dieter Castel, Vincent Reniers and Stefan Pante
 * @version 0.9
 *
 * @Invar 	The x-coordinate is a valid x-coordinate.
 * 			| isValidX(getX())
 * @Invar 	The y-coordinate is a valid y-coordinate.
 * 			| isValidY(getY())
 */
public class Coordinate {

	/**
	 * The x-coordinate.
	 */
	private int x;
	/**
	 * The y-coordinate.
	 */
	private int y;

	/**
	 * Initialize a new coordinate with a given x- and y-coordinates
	 * 
	 * @param	x
	 * 			the x-coordinate of this Coordinate2D
	 * @param	y
	 * 			the y-coordinate of this Coordinate2D
	 * @post	The new x-coordinate is equal to the given x
	 * 			|	new.getX() == x
	 * @post	the new y-coordinate is equal to the given y
	 * 			|	new.getY() == y
	 * @throws 	IllegalArgumentException
	 * 			| the given x and/or y cannot be used as
	 * 			| x or y- coordinates of this Coordinate2d
	 * 			| !isValidX(x) || !isValidY(y)
	 */
	public Coordinate(int x, int y) throws IllegalArgumentException {
		setX(x);
		setY(y);
	}

	/**
	 * Returns the x-coordinate of this Coordinate2D
	 */
	@Basic @Raw
	public int getX(){
		return x;
	}

	/**
	 *  Returns the y-coordinate of this Coordinate2D
	 */
	@Basic @Raw
	public int getY(){
		return y;
	}
	/**
	 * Sets the x-coordinate of this coordinate if the given x is valid.
	 * 
	 * @param x
	 * 		
	 */
	public void setX(int x) throws IllegalArgumentException{
		if(!isValidX(x)) throw new IllegalArgumentException();
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of this coordinate if the given y is valid.
	 * 
	 * @param y
	 */
	public void setY(int y) throws IllegalArgumentException{
		if(!isValidY(y)) throw new IllegalArgumentException();
		this.y = y;
	}

	/**
	 * Returns whether the given number is a valid x coordinate.
	 * 
	 * @param x
	 * @return 	Always true since the coordinates have no constraints.
	 * 			| result == true
	 */
	public static boolean isValidX(int x){
		return true;
	}

	/**
	 * Returns whether the given number is a valid y coordinate.
	 * 
	 * @param y
	 * @return Always true since the coordinates have no constraints.
	 * 			| result == true
	 */
	public static boolean isValidY(int y){
		return true;	
	}

	/**
	 * The string representation of this coordinate.
	 */
	@Override
	public String toString() {
		String result = "(" + getX()+","+ getY() +")";
		return result;
	}

	/**
	* Check whether this Coordinate is equal to the given object.
	* Compares the x and y coordinates if the given object is also
	* a Coordinate2D
	*
	* @return	True if <obj> is an effective Coordinate2D with the same
	* 			x and y coordinate.
	* 			| (obj != null) && (obj instance of Coordinate2D) &&
	* 			| (((Coordinate2D) obj).getX() == getX())
	* 			| (((Coordinate2D) obj).getY() == getY())
	*/
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Coordinate)){
			return false;
		}
		if(this.getX() != ((Coordinate) obj).getX()){
			return false;
		}
		if(this.getY() != ((Coordinate) obj).getY()){
			return false;
		}
		return true;
	}

	/*TODO: currently we state that the coordinates can have negative values 
	 * 		if however we use negative numbers the hashCode won't be correct.
	 */
	@Override
	public int hashCode() {
		return (int) (Math.pow(2, this.getX()) * Math.pow(3, this.getY()));
	}

	/**
	 * Returns the closest neighbouring coordinate in the given direction
	 * 
	 * @param 	direction the direction in which the neighbour is wanted
	 * @return 	the closest neighbour in the given direction
	 */
	public Coordinate getNeighbor(Direction direction){
		int x = getX();
		int y = getY();
		switch (direction) {
		case NORTH:
			return new Coordinate(x, y-1);
		case NORTHEAST:
			return new Coordinate(x+1, y-1);
		case EAST:
			return new Coordinate(x+1, y);
		case SOUTHEAST:
			return new Coordinate(x+1, y+1);
		case SOUTH:
			return new Coordinate(x, y+1);
		case SOUTHWEST:
			return new Coordinate(x-1, y+1);
		case WEST:
			return new Coordinate(x-1, y);
		case NORTHWEST:
			return new Coordinate(x-1, y-1);
		default:
			throw new IllegalArgumentException("The given direction is not valid" + direction ); 
		}
	}
	
	public ArrayList<Coordinate> getAllNeighbors(){
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(new Coordinate(x+1, y));
		coordinates.add(new Coordinate(x+1, y+1));
		coordinates.add(new Coordinate(x+1, y-1));
		coordinates.add(new Coordinate(x-1, y));
		coordinates.add(new Coordinate(x-1, y+1));
		coordinates.add(new Coordinate(x-1, y-1));
		coordinates.add(new Coordinate(x	, y-1));
		coordinates.add(new Coordinate(x	, y+1));
		return coordinates;
	}
}