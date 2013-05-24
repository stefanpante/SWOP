package grid;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import square.GridElement;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This class describes a grid object.
 * 
 * It contains GridElements on coordinates that range from (0,0) (upper leftmost square) to
 * 	(<code>getHSize()</code>-1, <code>getVSize()</code>-1) (lower rightmost square).
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class Grid {
	
	/**
	 * Minimal length of a wall
	 */
	public static final int SMALLEST_WALL_LENGTH = 2;
	
	/**
	 * Max percentage of walls in this grid.
	 */
	public static float PERCENTAGE_WALLS = 0.2f;

	/**
	 * Percentage of max length of a wall
	 */
	public static float LENGTH_PERCENTAGE_WALL = 0.5f;
	
	/**
	 * HashMap containing all the gridElements of the grid. the coordinate
	 * of the gridElement is the key.
	 */
	private HashMap<Coordinate, GridElement> grid;
	
	/**
	 * The horizontal size of the grid.
	 */
	private int hSize;

	/**
	 * The vertical size of the grid.
	 */
	private int vSize;
	
	/**
	 * the start position of the players
	 */
	
	private ArrayList<Square> startPositions;
	
	/**
	 * Creates a new grid with given height and length.
	 * 
	 * @param 	hSize
	 * 			The horizontal size of the new grid.
	 * @param	vSize
	 * 			The vertical size of the new grid.
	 * @effect	setHSize(hSize)
	 * @effect	setVSize(vSize)
	 */
	public Grid(int hSize, int vSize, HashMap<Coordinate, GridElement> grid){
		this.grid = grid;
		setHSize(hSize);
		setVSize(vSize);
		this.startPositions = new ArrayList<Square>();
	}
	
	/**
	 * Sets the horizontal size to the given size if it is valid.
	 * 
	 * @param 	size
	 * 			The size to which the horizontal size will be set.
	 * @throws 	IllegalArgumentException
	 * 			If the given size is not a valid horizontal size.
	 * 			| !isisValidHSize(size)
	 */
	private void setHSize(int size) throws IllegalArgumentException{
		if(!isValidHSize(size)) throw new IllegalArgumentException("The given size ("+ size+") is not a valid horizontal size");
		this.hSize = size;
	}
	
	/**
	 * Sets the vertical size to the given size if it is valid.
	 * 
	 * @param 	size
	 * 			The size to which the vertical size will be set.
	 * @throws 	IllegalArgumentException
	 * 			If the given size is not a valid vertical size.
	 * 			| !isisValidVSize(size)
	 */
	private void setVSize(int size) throws IllegalArgumentException{
		if(!isValidVSize(size)) throw new IllegalArgumentException("The given size ("+ size+") is not a valid vertical size");
		this.vSize = size;
	}
	
	/**
	 * Returns whether the given size is a valid horizontal size.
	 * 
	 * @param 	size
	 * 			The size to check.
	 * @return	True 	if and only if the given size is bigger than 
	 * 					the minimum horizontal size.
	 * 			False	otherwise
	 * 			| size >= Grid.MIN_HSIZE
	 */
	public boolean isValidHSize(int size){
		return size >= 0;
	} 
	
	/**
	 * Returns whether the given size is a valid vertical size.
	 * 
	 * @param 	size
	 * 			The size to check.
	 * @return	True 	if and only if the given size is bigger than 
	 * 					the minimum vertical size.
	 * 			False	otherwise.
	 * 			| size >= Grid.MIN_VSIZE
	 */
	public boolean isValidVSize(int size){
		return size >= 0;
	}
	
	/**
	 * Returns the horizontal size of this gird.
	 */
	@Basic @Raw
	public int getHSize(){
		return hSize;
	}
	
	/**
	 * Returns the vertical size of this gird.
	 */
	@Basic @Raw
	public int getVSize(){
		return vSize;
	}

	/**
	 * Checks whether the given coordinate is a valid coordinate for all the grids.
	 *  
	 * @param 	coordinate
	 * 			The coordinate to check.
	 * @return	True	if the x and y coordinate are not lower than zero.
	 * 			False	otherwise.
	 * 			|	coordinate.getX() >= 0
	 * 			|	&&
	 * 			|	coordinate.getY() >= 0
	 */
	public boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate != null && coordinate.getX() >= 0 && coordinate.getY() >= 0;
    }
	
	/**
	 * Returns the square at the given coordinate.
	 * 
	 * @param 	coordinate
	 * 			The coordinate of the square.
	 * @return	The square at the given coordinate.
	 * @throws	NoSuchElementException
	 * 			If there is no such coordinate in the given grid.
	 */
	@Basic @Raw
	public GridElement getGridElement(Coordinate coordinate) throws NoSuchElementException{
		if(!contains(coordinate)){
			throw new NoSuchElementException("There is no coordinate " + coordinate + "in this " + this);
		}
		return grid.get(coordinate);
	}
	
	/**
	 * Returns a list with the squares at the given coordinates.
	 * 	Only the valid coordinates of the grid that return a square are added to the resulting list.
	 * 	Non valid coordinates are ignored.
	 * 	There is no guarantee about the order of the squares.
	 * 
	 * @param 	coordinates
	 * 			The list of coordinates of which you want the squares.
	 * @return	A list of squares corresponding to all the valid given coordinates.
	 * 			Not in a specific order.
	 */
	public ArrayList<GridElement> getGridElements(ArrayList<Coordinate> coordinates){
		ArrayList<GridElement> gridElements = new ArrayList<GridElement>();
		for(Coordinate coordinate : coordinates){
			try {
				gridElements.add(getGridElement(coordinate));
			} catch (NoSuchElementException ignored) {
				
			}
		}
		return gridElements;
	}
	
	/**
	 * Returns the coordinate for the given square.
	 * 
	 * @param 	gridElement
	 * 			The square of which the coordinate will be returned.
	 * @return	The coordinate of the given square.
	 * @throws 	NoSuchElementException
	 * 			If the given square is not placed in this grid.
	 * 			| !contains(square)
	 */
	public Coordinate getCoordinate(GridElement gridElement) throws NoSuchElementException{
		for(Coordinate coordinate : grid.keySet()){
			if(getGridElement(coordinate).equals(gridElement))
				return coordinate;
		}
		throw new NoSuchElementException("No coordinate found for " + gridElement);
	}
	
	/**
	 * Returns the coordinates corresponding to the given gridElements.
	 * @param gridElements		the gridElements of which the coordinates are returned
	 * @return				the coordinates of the gridElements
	 */
	public ArrayList<Coordinate> getCoordinates(ArrayList<? extends GridElement> gridElements){
		ArrayList<Coordinate> coors = new ArrayList<Coordinate>();
		for(GridElement gridElement: gridElements)
			coors.add(getCoordinate(gridElement));
		return coors;
	}
	
	/**
	 * Returns whether the given gridElement is placed in this grid.
	 * 
	 * @param 	gridElement
	 * 			The gridElement to check.
	 * @return	True	if the gridElement is placed in this grid.
	 * 			False	otherwise.
	 */
	@Basic @Raw
	public boolean contains(GridElement gridElement){
		return grid.containsValue(gridElement);
	}
	
	/**
	 * Returns whether the given coordinate is placed in this grid.
	 * 
	 * @param 	coordinate
	 * 			The coordinate to check.
	 * @return	True	if the coordinate is a part of this grid.
	 * 			False	otherwise.
	 */
	@Basic @Raw
	public boolean contains(Coordinate coordinate){
		return grid.containsKey(coordinate);
	}
	
	/**
	 * Returns a list with all the squares of this grid.
	 * 
	 * @return	A list with all the squares positioned 
	 * 			at a coordinate in this grid.
	 */
	@Basic @Raw
	public ArrayList<GridElement> getAllGridElements(){
		return new ArrayList<GridElement>(grid.values());
	}
	
	/**
	 * Returns a list with all the coordinates of this grid.
	 * 
	 * @return	A list with all the coordinates of this grid.
	 */
	@Basic @Raw
	public ArrayList<Coordinate> getAllCoordinates(){
		return new ArrayList<Coordinate>(grid.keySet());
	}

	/**
	 * Checks if the given square is a valid start position for the player
	 * @param player   The square which needs to be checked
	 */
	public boolean isValidStartPosition(GridElement player){
        return true;
//		return (grid.containsValue(player) && !player.isObstacle());
	}

	
    /**
     * Returns the start positions of all the players on the grid.
     * @return
     */
    public ArrayList<Square> getStartPositions(){
    	return new ArrayList<Square>(startPositions);
    }
    
    /**
     * Adds a start position for the player on the grid.
     * @param square
     */
    public void addStartPosition(Square square){
    	if(!isValidStartPosition(square)){
    		throw new IllegalArgumentException("a startsquare for a player should be valid! (not obstructed and not null");
    	}
    	this.startPositions.add(square);
    }
    
    
	/**
	 * Returns a string representation of the grid.
	 */
	@Override
	public String toString() {
		return "Grid (hSize="+getHSize()+", vSize" + getVSize()+")";
	}
}
