package util;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Coordinate2D represents a point in 2D space, 
 * has an x-coordinate and y-coordinate
 * 
 * (0,0) by convention is the most left upper square.
 * 
 * @author Jonas Devlieghere, Dieter Castel   and Stefan Pante
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
	 * @param x  the x coordinate
	 * 		
	 */
    void setX(int x) throws IllegalArgumentException{
		if(isNotValidX(x)) throw new IllegalArgumentException("The given x("+x+") is not a valid x coordinate.");
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of this coordinate if the given y is valid.
	 * 
	 * @param y
	 */
    void setY(int y) throws IllegalArgumentException{
		if(isNotValidY(y)) throw new IllegalArgumentException("The given y("+y+") is not a valid y coordinate.");
		this.y = y;
	}

	/**
	 * Returns whether the given number is a valid x coordinate.
	 * 
	 * @param x
	 * @return 	Always true since the coordinates have no constraints.
	 * 			| result == true
	 */
	private static boolean isNotValidX(int x){
		return false;
	}

	/**
	 * Returns whether the given number is a valid y coordinate.
	 * 
	 * @param y
	 * @return 	Always true since the coordinates have no constraints.
	 * 			| result == true
	 */
	private static boolean isNotValidY(int y){
		return false;
	}

	/**
	 * The string representation of this coordinate.
	 */
	@Override
	public String toString() {
        return "(" + getX()+","+ getY() +")";
	}

	/**
	 * Returns the closest neighboring coordinate in the given direction
	 * 
	 * @param 	direction the direction in which the neighbor is wanted
	 * @return 	the closest neighbor in the given direction
	 */
	public Coordinate getNeighbor(Direction direction){
		return getCoordinate(direction,1);
	}
	
	/**
	 * Returns a list of all eight neighbors of this coordinate in respect to the direction.
	 */
	public HashMap<Direction,Coordinate> getAllNeighbors(){
		HashMap<Direction,Coordinate> coordinates = new HashMap<>();
		
		coordinates.put(Direction.EAST,new Coordinate(x+1, y));
		coordinates.put(Direction.SOUTHEAST,new Coordinate(x+1, y+1));
		coordinates.put(Direction.NORTHEAST,new Coordinate(x+1, y-1));
		coordinates.put(Direction.WEST,new Coordinate(x-1, y));
		coordinates.put(Direction.SOUTHWEST,new Coordinate(x-1, y+1));
		coordinates.put(Direction.NORTHWEST,new Coordinate(x-1, y-1));
		coordinates.put(Direction.NORTH,new Coordinate(x	, y-1));
		coordinates.put(Direction.SOUTH,new Coordinate(x	, y+1));
		
		return coordinates;
	}

	/**
	 * Returns the hashcode of this coordinate.
	 * 	Hashcodes are the same if the x and y coordinate are the same. 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	* Check whether this Coordinate is equal to the given object.
	* Compares the x and y coordinates if the given object is also
	* a Coordinate.
	*
	* @return	True if <obj> is an effective Coordinate with the same
	* 			x and y coordinate.
	* 			| (obj != null) && (obj instance of Coordinate2D) &&
	* 			| (((Coordinate2D) obj).getX() == getX())
	* 			| (((Coordinate2D) obj).getY() == getY())
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Coordinate)) {
			return false;
		}
		Coordinate other = (Coordinate) obj;
        return x == other.x && y == other.y;
    }

    /**
     * Returns a new Coordinate in the given Direction at the given Distance.
     *
     * @param   direction
     *          The direction in which the coordinate is returned
     * @param   distance
     *          The distance at which the coordinate is returned
     * @return  The coordinate in the given directin at the given distance
     */
    public Coordinate getCoordinate(Direction direction, int distance){
        switch (direction){
            case NORTH:
                return new Coordinate(getX(), getY() - distance);
            case SOUTH:
                return new Coordinate(getX(), getY() + distance);
            case EAST:
                return new Coordinate(getX() + distance, getY());
            case WEST:
                return new Coordinate(getX() - distance, getY());
            case NORTHEAST:
                return new Coordinate(getX() + distance, getY() - distance);
            case NORTHWEST:
                return new Coordinate(getX() - distance, getY() - distance);
            case SOUTHEAST:
                return new Coordinate(getX() + distance, getY() + distance);
            case SOUTHWEST:
                return new Coordinate(getX() - distance, getY() + distance);
        }
        return null;
    }

    /**
     * Returns a list with coordinates between this coordinate and the given coordinate.
     *
     * @param   coordinate
     *          The last coordinate of the set.
     * @return  A list with coordinates between this coordinate and the given coordinate
     */
    public ArrayList<Coordinate> getCoordinatesTo(Coordinate coordinate){
        Direction direction = directionTo(coordinate);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(this);
        Coordinate neighbor = getNeighbor(direction);
        while(!neighbor.equals(coordinate)){
            coordinates.add(neighbor);
            neighbor = neighbor.getNeighbor(direction);
        }
        coordinates.add(coordinate);
        return coordinates;
    }


    /**
     * Determine the main direction in which the given coordinate lies seen from
     * this coordinate.
     *
     * @param   coordinate
     *          The coordinate for which the direction is returned
     * @return  The main direction in which the given coordinate lies
     */
    public Direction directionTo(Coordinate coordinate){
        if(equals(coordinate))
            throw  new IllegalArgumentException("The given squares are the same.");

        int xDiff = coordinate.getX() - getX();
        int yDiff = coordinate.getY() - getY();
        boolean straight = (xDiff == 0 || yDiff == 0);
        boolean diagonal = (xDiff == yDiff || xDiff == -yDiff);

        if(straight){
            if(xDiff > 0){
                return Direction.EAST;
            }else if(xDiff < 0){
                return Direction.WEST;
            }

            if(yDiff > 0){
                return  Direction.SOUTH;
            }else if(yDiff < 0){
                return Direction.NORTH;
            }
        }else if(diagonal){
            if(xDiff > 0 && yDiff > 0){
                return  Direction.SOUTHEAST;
            }else if(xDiff > 0 && yDiff < 0){
                return Direction.NORTHEAST;
            }else if(xDiff < 0 && yDiff > 0){
                return Direction.SOUTHWEST;
            }else if(xDiff < 0 && yDiff < 0){
                return Direction.NORTHWEST;
            }
        }
        throw new IllegalArgumentException("The given squares do not lie on a main direction");
    }

}