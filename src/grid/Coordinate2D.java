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
	 * 
	 * @return
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY(){
		return y;
	}
	/**
	 * 
	 * @param x1
	 */
	//TODO Check if valid param, do we need lower and upper bound?
	public void setX(int x1) throws IllegalArgumentException{
		this.x = x1;
	}
	
	/**
	 * 
	 * @param y1
	 */
	//TODO check if valid param, do we need lower and upper bound?
	public void setY(int y1) throws IllegalArgumentException{
		this.y = y1;
	}
	
	public boolean isValidX(int x1){
		return false;
	}
	
	public boolean isValidY(int y1){
		return false;
	}


}
