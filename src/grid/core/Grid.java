package grid.core;

import grid.obstacles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

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

	/**
	 * Percentage of square covered by grenades
	 */
	public static float PERCENTAGEGRENADES = 0.05f;

	/**
	 * Max percentage of squares covered by walls
	 */
	public static float PERCENTAGEWALLS = 0.2f;

	/**
	 * Percentage of max length of a wall
	 */
	public static float LENGTHPERCENTAGEWALL = 0.5f;

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
	 * 
	 * @param	hSize	Horizontal dimension of the grid.
	 */
	public Grid(int vSize, int hSize) throws IllegalArgumentException {
		setVerticalSize(vSize);
		setHorizontalSize(hSize);
		this.squares = new HashMap<Coordinate2D,Square>(this.getSize());
		this.obstacles = new ArrayList<Obstacle>();
		this.initGrid();
	}
	//TODO: Write method, creates all the squares,
	public void initGrid(){
		createSquares();
		createWalls();
		placeGrenades();

		// max 20% is covered by walls + the length of the walls is max 0.5 * hSize or vSize
		//2. 5 percent of squares have a lightgrenade in there inventory// Watch out,
		// squares covered by walls can't contain lightgrenades
		// Startposition cant be covered by wall
	}
	/**
	 * Creates the grid with the squares
	 */
	public void createSquares() throws IllegalStateException{

		for(int i = 0; i< hSize; i++){
			for(int j = 0; j < vSize; j++){
				Coordinate2D key = new Coordinate2D(i,j);
				Square value = new Square();
				squares.put(key, value);
			}
		}
	}

	@Basic 
	public HashMap<Coordinate2D, Square> getSquares(){
		return squares;
	}
	//TODO
	public void createWalls(){

		// max 20 percent of squares is covered
		int coverage = (int) ((vSize*hSize) * Grid.PERCENTAGEWALLS);
		// random selection between 0 and 20 percent
		coverage = (int) Math.floor(coverage * Math.random());

		// All possible orientations for a wall.
		Orientation[] orientations = new Orientation[]{Orientation.HORIZONTAL, Orientation.VERTICAL};
		// is this a correct method? Clone vereist cast jammer genoeg...

		HashMap<Coordinate2D, Square> candidates = new HashMap<Coordinate2D, Square>(squares);//(HashMap<Coordinate2D, Square>) squares.clone();
		// remove startpositions from possible candidates
		candidates.remove(new Coordinate2D(0, vSize -1));
		candidates.remove(new Coordinate2D(hSize -1, 0));

		// kies een random richting
		// Random waarde tussen 0 en 0.50 ( minimum lengte van wall is 2
		// lengte van de muur
		// aftrekken van nodige squares
		// stoppen als het aantal resterende square < 2
	}

	//TODO: me thinkey this method is finished
	private void placeGrenades(){

		int grenades = (int) (this.getSize() * Math.ceil(this.getSize() * Grid.PERCENTAGEGRENADES));

		ArrayList<Square> candidateSquares = new ArrayList<Square>(squares.values());

		// Remove startpositions
		candidateSquares.remove(getLowerLeft());
		candidateSquares.remove(getUpperRight());

		//TODO: remove all obstacles
		for(Obstacle o: obstacles)
			candidateSquares.removeAll(o.getSquares());

		// Add the grenades to the squares, random distribution
		Random generator = new Random();
		for(int i = 0; i < grenades; i++){
			int l = candidateSquares.size();
			Square s = candidateSquares.get(generator.nextInt(l));
			candidateSquares.remove(s);
		}

	}
	//TODO
	/**
	 * gets the lowerleft square of the grid
	 * @return
	 */
	@Raw @Basic
	public Square getLowerLeft() {
		return squares.get(new Coordinate2D(0, vSize -1));

	}

	/**
	 * gets the upper right square of the grid
	 * @return
	 */
	@Raw @Basic
	public Square getUpperRight(){
		return squares.get(new Coordinate2D(hSize -1, 0));

	}
	/**
	 * Finds the Coordinate2D associated with a square
	 * @param sq the square of which the coordinate needs to be looked up
	 * @return the coordinate2D associated with the given square
	 * @throws IllegalStateException
	 * 			thrown if the square is not a part of the grid, the 
	 * 			grid is in a inconsistent state
	 */
	public Coordinate2D getCoordinate2D(Square sq) throws IllegalStateException{
		Coordinate2D result = null;
		for(Coordinate2D c: squares.keySet()){
			Square square = squares.get(c);
			if(square.equals(sq)){
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
		return vSize > Grid.MIN_VSIZE;
	}

	/**
	 * Checks if the given position is a valid start position for the player.
	 * The starting position of player cannot contain a light grenade.
	 * For each player, at least one light grenade is placed in the area of 3x3 squares 
	 * that covers the starting position of that player.
	 * 
	 * @param	pos
	 * @return  true if the position is a valid startposition
	 * 			false otherwise
	 */
	public boolean isValidStartPosition(Square pos){
		return false;
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
		return hSize > Grid.MIN_HSIZE;
	}

	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}

	public int getSize(){
		return hSize * vSize;
	}
	
	@Override
	//TODO should we also describe the obstacles?
	public String toString(){
		return "horizontal size: " + hSize + " Vertical size: " +  vSize;
	}

}
