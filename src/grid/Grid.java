package grid;

import item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import util.Direction;
import square.GridElement;
import square.Square;
import util.Coordinate;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * This class describes a grid object.
 * 
 * It contains squares on coordinates that range from (0,0) (upper leftmost square) to
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
	 * Returns whether the given square is a valid square for all grids.
	 * 
	 * @param 	square
	 * 			The square to check.
	 * @return	True 	if the given square is an actual square.
	 * 			False	otherwise.
	 * 			| square != null
	 */
	public static boolean isValidGridElement(GridElement gridElement) {
        return gridElement != null;
    }
	
	/**
	 * Returns whether the given square is a valid square for this grid.
	 * 
	 * @param 	square
	 * 			The square to check.
	 * @return	True	if the square is not already located in this grid.
	 * 			False	otherwise.
	 * 			| ! this.contains(square)
	 */
	public boolean canHaveAsSquare(Square square){
        return !this.contains(square);
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
	 * Returns the neighbor of the given square.
	 * 
	 * @param 	square
	 * 			The square from which the neighbor will be returned.
	 * @param 	direction
	 * 			The direction in which the neighbor will be calculated.
	 * @return	The neighbor of the given square in the given direction.	
	 * @throws 	NoSuchElementException
	 * 			If there is no square at the neighboring coordinate.
	 */
	public GridElement getNeighbor(GridElement gridElement, Direction direction) throws NoSuchElementException {
		Coordinate coordinate = getCoordinate(gridElement);
		Coordinate c = coordinate.getNeighbor(direction);
		try{
			return getGridElement(c);
		}catch(NoSuchElementException ex){
			throw new NoSuchElementException("There is no neighbor in "+ direction 
					+" direction of the "+ gridElement 
					+" at coordinate " + coordinate+".");
		}
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
	
	public ArrayList<Coordinate> getCoordinates(ArrayList<GridElement> gridElements){
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
	 * Checks if there is a neighbor in the given direction for the given square.
	 * 
	 * @param	gridElement	
	 * 			The square from which the check will be executed.
	 * @param 	direction 	
	 * 			The direction in which the check will be executed.
	 * @return 	True if this square has a neighbor in the given direction
	 * 			otherwise False.
	 */
	public boolean hasNeighbor(GridElement gridElement, Direction direction){
		try {
			this.getNeighbor(gridElement, direction);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the given squares are neighbors in the given direction.
	 * 
	 * @param	fromGridElement
	 * 			The square which will be the starting reference point.
	 * @param 	via 	
	 * 			The direction in which the square should be a neighbor.
	 * @param 	toGridElement 
	 * 			The square that should be the neighbor in the given direction of fromSquare.
	 * @return 	true if the square is the neighbor in the given direction
	 * 			otherwise false
	 */
	public boolean hasNeighbor(GridElement fromGridElement, Direction via, GridElement toGridElement) {
		GridElement neighbor = getNeighbor(fromGridElement, via);
        return neighbor != null && neighbor.equals(toGridElement);
    }
	
	/**
	 * Checks whether from the given fromSquare it is possible to move to a
	 * neighboring square in the given direction. 
	 * 
	 * @param 	fromGridElement
	 * 			The square from which the prohibitsPlayer will be checked.
	 * @param 	direction
	 * 			The direction of the neighbor that will be checked.
	 * @return	True 	if it is possible to move in the given direction
	 * 			False	otherwise.
	 */
	// FIXME this method isnt correct anymore
	@Deprecated
	public boolean canMoveTo(GridElement fromGridElement, Direction direction){
		GridElement directionGridElement;
		try {
			directionGridElement = getNeighbor(fromGridElement, direction);
		} catch (Exception e) {
			return false;
		}
		if(directionGridElement.isObstacle())
			return false;
		if(direction.isDiagonal()){
			GridElement g1 = null;
			GridElement g2 = null;
			ArrayList<Direction> dirs = direction.neighborDirections();
			try{
				g1 = getNeighbor(fromGridElement, dirs.get(0)); 
				g2 = getNeighbor(fromGridElement, dirs.get(1));
			} catch (Exception exp){
				//This should never happen since directionG exists.
				assert(false);
			}
			if(g1 != null && g2 != null){
				if(g1.isObstacle() && g2.isObstacle()){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns an HashMap with the neighbors of the given square by direction.
	 * 
	 * @return 	An HashMap with neighbors with there direction as the key
	 * 			and the Square as value.
	 * @throws	NoSuchElementException
	 * 			If the given square is not a part of this grid.
	 * 			| !contains(square)
	 */
	@Deprecated
	public HashMap<Direction, GridElement> getNeighbors(GridElement gridElement) throws NoSuchElementException {
		if(!contains(gridElement)){
			throw new NoSuchElementException("The " + gridElement + " is not a part of this " + this);
		}
		HashMap<Direction, GridElement> result = new HashMap<Direction, GridElement>();
		GridElement neighbor;
		for(Direction dir: Direction.values()){
			try{
				neighbor = getNeighbor(gridElement, dir);
				result.put(dir, neighbor);
			} catch(NoSuchElementException ex){
				//Happens when there is no other neighbor.
			}
		}
		return result;
	}
	
	/**
	 * Returns the neighbors of this square as a list.
	 * 
	 * @return Returns the neighbors of this square as a list.
	 */
	public ArrayList<GridElement> getNeighborsAsList(GridElement gridElement) {
		return new ArrayList<GridElement>(getNeighbors(gridElement).values());
	}
		
	/**
	 * Checks if the given square is a valid start position for the player
	 * @param player   The square which needs to be checked
	 */
	public boolean isValidStartPosition(GridElement player){
		return (grid.containsValue(player) && !player.isObstacle());
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
