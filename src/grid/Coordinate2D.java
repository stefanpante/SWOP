package grid;

/**
 * Coordinate2D represents a point in 2D space, has an x-coordinate and y coordinate
 * @author Jonas Devlieghere, Dieter Castel, Vincent Reniers and Stefan Pante
 *
 */

public class Coordinate2D {

	private int x;
	private int y;
	
	/**
	 * 
	 */
	public Coordinate2D(int x, int y) {
		this.x = x;
		this.y = y;
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
	public void setX(int x1){
		this.x = x1;
	}
	
	/**
	 * 
	 * @param y1
	 */
	//TODO check if valid param, do we need lower and upper bound?
	public void setY(int y1){
		this.y = y1;
	}
	
	public boolean isValidX(int x1){
		return false;
	}
	
	public boolean isValidY(int y1){
		return false;
	}


}
