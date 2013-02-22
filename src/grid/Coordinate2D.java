package grid;

/**
 * Coordinate2D represents a point in 2D space, 
 * has an x-coordinate and y-coordinate
 * 
 * @author Jonas Devlieghere, Dieter Castel, Vincent Reniers and Stefan Pante
 * @version 0.0.1RC-alpha
 *
 */

public class Coordinate2D {

	private int x;
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
	public Coordinate2D(int x, int y) throws IllegalArgumentException {
		setX(x);
		setY(y);
	}
	
	/**
	 * Returns the x-coordinate of this Coordinate2D
	 */
	public int getX(){
		return x;
	}
	
	/**
	 *  Returns the y-coordinate of this Coordinate2D
	 */
	public int getY(){
		return y;
	}
	/**
	 * Checks whether the given x is a valid value for this
	 * Coordinate2D
	 * 
	 * @param 	x1
	 * 			
	 */
	public void setX(int x) throws IllegalArgumentException{
		if(!isValidX(x)) throw new IllegalArgumentException();
		this.x = x;
	}
	
	/**
	 * Checks whether the given y is a valid value for this
	 * Coordinate2D
	 * 
	 * @param y
	 */
	public void setY(int y) throws IllegalArgumentException{
		if(!isValidY(y)) throw new IllegalArgumentException();
		this.y = y;
	}
	
	public boolean isValidX(int x){
		return true;
	}
	
	public boolean isValidY(int y){
		return true;	
	}


}
