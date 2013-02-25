package grid.core;

import grid.obstacles.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Grid class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
public class Grid {
	
	/**
	 * the minimum vertical and horizontal size are 10 squares
	 */
	public static int MIN_VSIZE = 10;
	public static int MIN_HSIZE = 10;
	
	private HashMap<Coordinate2D,Square> squares;
	private ArrayList<Obstacle> obstacles;
	
	/**
	 * The vSize of this Grid object.
	 */
	private int vSize;
	
	/**
	 * The hSize of this Grid object.
	 */
	private int hSize;

	/**
	 * generates a grid with vSize * hSize squares
	 * 
	 * @param	vSize	Vertical dimension of the grid.
	 * @param	hSize	Horizontal dimension of the grid.
	 */
	public Grid(int vSize, int hSize) throws IllegalArgumentException {
		setVerticalSize(vSize);
		setHorizontalSize(hSize);
		this.initGrid();
	}
	//TODO: Write method
	private void initGrid(){
		//1. Creates squares
		// max 20% is covered by walls + the length of the walls is max 0.5 * hSize or vSize
		//2. 5 percent of squares have a lightgrenade in there inventory// Watch out,
		// squares covered by walls can't contain lightgrenades
		// Startposition cant be covered by wall
	}
	
	public Square getLowerLeft(){
		return null;
		
	}
	
	public Square getUpperRight(){
		return null;
		
	}
	/**
	 * Finds the Coordinate2D associate
	 * @param sq
	 * @return
	 * @throws IllegalStateException
	 */
	public Coordinate2D getCoordinate2D(Square sq) throws IllegalStateException{
		Coordinate2D result = null;
		for(Coordinate2D c: squares.keySet()){
			Square square = squares.get(c);
			if(square == sq){
				result = c;
				break;
			}
		}
		
		if(result == null) 
			throw new IllegalStateException("Square is not part of the grid");
		return result;
	}
	/**
	 * Returns the value of the vSize of this Grid as an int.
	 *
	 * @return 	An object of the int class.
	 * 			| int
	 */
	public int getVerticalSize() {
		return vSize;
	};

	/**
	 * Sets the value of the vSize of Grid if the given value is valid. 
	 * 
	 * @param 	vSize
	 *			The vSize to set.
	 * @post 	The given value is the current value of the vSize of this Grid.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid vSize.
	 *			| !isValidVerticalSize(vSize)
	 */
	public void setVerticalSize(int vSize) {
		if (!isValidVerticalSize(vSize)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ vSize
							+ ") is not a valid agrument of the field vSize from the class Grid");
		}
		this.vSize = vSize;
	};

	/**
	 * Check whether the given vSize is a valid vSize for all the objects of Grid.
	 * @param 	vSize
	 *			The vSize to check.
	 * @return	True if and only if the given value is larger than zero.
	 */
	public static boolean isValidVerticalSize(int vSize) {
		return vSize > 0;
	}

	/**
	 * Returns the value of the hSize of this Grid as an int.
	 *
	 * @return 	An object of the int class.
	 * 			| int
	 */
	public int getHorizontalSize() {
		return hSize;
	};

	/**
	 * Sets the value of the hSize of Grid if the given value is valid. 
	 * 
	 * @param 	hSize
	 *			The horizontal size to set.
	 * @post 	The given value is the current value of the horizontal size of this Grid.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid hSize.
	 *			| !isValidHorizontalSize(hSize)
	 */
	public void setHorizontalSize(int hSize) {
		if (!isValidHorizontalSize(hSize)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ hSize
							+ ") is not a valid agrument of the field hSize from the class Grid");
		}
		this.hSize = hSize;
	};

	/**
	 * Check whether the given hSize is a valid hSize for all the objects of Grid.
	 * @param 	hSize
	 *			The hSize to check.
	 * @return	True if and only if the given value is larger than 0.
	 */
	public static boolean isValidHorizontalSize(int hSize) {
		return hSize > 0;
	}
	
	



}
