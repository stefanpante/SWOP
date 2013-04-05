package grid;

import item.Item;
import item.Teleport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import square.Direction;
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
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class Grid {
	
	/**
	 * The minimum vertical size is 10 squares.
	 */
	public static final int MIN_VSIZE = 10;
	
	/**
	 * The minimum horizontal size is 10 squares.
	 */
	public static final int MIN_HSIZE = 10;
	
	/**
	 * Minimal length of a wall
	 */
	public static final int SMALLEST_WALL_LENGTH = 2;
	
	/**
	 * Max percentage of squares covered by walls.
	 */
	public static float PERCENTAGE_WALLS = 0.2f;

	/**
	 * Percentage of square covered by grenades
	 */
	public static float PERCENTAGE_GRENADES = 0.05f;

	/**
	 * Percentage of square covered by identity disks
	 */
	public static float PERCENTAGE_IDENTITY_DISKS = 0.02f;
	
	/**
	 * Percentage of max length of a wall
	 */
	public static float LENGTH_PERCENTAGE_WALL = 0.5f;
	
	HashMap<Coordinate, Square> grid;
	
	/**
	 * The horizontal size of the grid.
	 */
	private int hSize;

	/**
	 * The vertical size of the grid.
	 */
	private int vSize;
	
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
	public Grid(int hSize, int vSize) throws IllegalArgumentException{
		grid = new HashMap<Coordinate, Square>();
		setHSize(hSize);
		setVSize(vSize);
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
		return size >= Grid.MIN_HSIZE;
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
		return size >= Grid.MIN_VSIZE;
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
	 * Sets the given square at the given coordinate.
	 * 
	 * @param 	coordinate
	 * 			The coordinate to place the square on.
	 * @param 	square
	 * 			
	 * @throw	IllegalArgumentException
	 * 			If the coordinate or square are not valid.
	 * 			| !isValidCoordinate(coordinate) || !canHaveAsCoordinate(coordinate)
	 * 			| 	|| 
	 * 			| !isValidSquare(square) || !canHaveAsSquare(square)
	 */
	public void setSquare(Coordinate coordinate, Square square) throws IllegalArgumentException{
		if(!isValidCoordinate(coordinate) || !canHaveAsCoordinate(coordinate)){
			throw new IllegalArgumentException("The given coordinate " + coordinate
												+ " is not valid for this " +grid );
		}
		if(!isValidSquare(square) || !canHaveAsSquare(square)){
			throw new IllegalArgumentException("The given square " + square
										+ " is not valid for this " + grid );
		}
		grid.put(coordinate,square);
	}

	
	/**
	 * Returns whether this coordinate is a valid coordinate for this grid.
	 * 
	 * @param 	coordinate
	 * 			The coordinate to check.
	 * @return	True 	if the coordinate x and y are smaller than 
	 * 					the horizontal resp. vertical size of this grid.
	 * 			False 	otherwise.
	 * 			| coordinate.getX() < this.getHSize()
	 * 			|	&&
	 * 			| coordinate.getY() < this.getVSize()
	 */
	private boolean canHaveAsCoordinate(Coordinate coordinate) {
		if(coordinate.getX() >= this.getHSize()){
			return false;
		}
		if(coordinate.getY() >= this.getVSize()){
			return false;
		}
		return true;
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
	public static boolean isValidSquare(Square square) {
		if(square == null){
			return false;
		}
		return true;
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
		if(this.contains(square)){
			return false;
		}
		return true;
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
		if(coordinate.getX() < 0){
			return false;
		}
		if(coordinate.getY() < 0){
			return false;
		}
		return true;
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
	public Square getNeighbor(Square square, Direction direction) throws NoSuchElementException {
		Coordinate coordinate = getCoordinate(square);
		Coordinate c = coordinate.getNeighbor(direction);
		try{
			return getSquare(c);
		}catch(NoSuchElementException ex){
			throw new NoSuchElementException("There is no neighbor in "+ direction 
					+" direction of the "+ square 
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
	public Square getSquare(Coordinate coordinate) throws NoSuchElementException{
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
	public ArrayList<Square> getSquares(ArrayList<Coordinate> coordinates){
		ArrayList<Square> squares = new ArrayList<Square>();
		for(Coordinate coordinate : coordinates){
			try {
				squares.add(getSquare(coordinate));
			} catch (NoSuchElementException e) {
				
			}
		}
		return squares;
	}
	
	/**
	 * Returns the coordinate for the given square.
	 * 
	 * @param 	square
	 * 			The square of which the coordinate will be returned.
	 * @return	The coordinate of the given square.
	 * @throws 	NoSuchElementException
	 * 			If the given square is not placed in this grid.
	 * 			| !contains(square)
	 */
	public Coordinate getCoordinate(Square square) throws NoSuchElementException{
		for(Coordinate coordinate : grid.keySet()){
			if(getSquare(coordinate).equals(square))
				return coordinate;
		}
		throw new NoSuchElementException("No coordinate found for " + square);
	}
	
	/**
	 * Returns whether the given square is placed in this grid.
	 * 
	 * @param 	square
	 * 			The square to check.
	 * @return	True	if the square is placed in this grid.
	 * 			False	otherwise.
	 */
	@Basic @Raw
	public boolean contains(Square square){
		return grid.containsValue(square);
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
	public ArrayList<Square> getAllSquares(){
		return new ArrayList<Square>(grid.values());
	}
	
	/**
	 * Return the square containing the given item.
	 * 
	 * @param 	item
	 * 			The item the square contians
	 * @return	The square containing the given item
	 */	
	public Square getSquareWith(Item item) {
		for(Square square : getAllSquares()){
			if(square.getInventory().hasItem(item))
				return square;
		}
		throw new IllegalArgumentException("No squares containing "+item);
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
	 * @param	square
	 * 			The square from which the check will be executed.
	 * @param 	direction 	
	 * 			The direction in which the check will be executed.
	 * @return 	True if this square has a neighbor in the given direction
	 * 			otherwise False.
	 */
	public boolean hasNeighbor(Square square, Direction direction){
		try {
			this.getNeighbor(square, direction);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if the given squares are neighbors in the given direction.
	 * 
	 * @param	fromSquare
	 * 			The square which will be the starting reference point.
	 * @param 	via 	
	 * 			The direction in which the square should be a neighbor.
	 * @param 	toSquare 
	 * 			The square that should be the neighbor in the given direction of fromSquare.
	 * @return 	true if the square is the neighbor in the given direction
	 * 			otherwise false
	 */
	public boolean hasNeighbor(Square fromSquare, Direction via, Square toSquare) {
		Square neighbor = getNeighbor(fromSquare, via);
		if(neighbor == null)
			return false;
		if(neighbor.equals(toSquare))
			return true;
		return false;
	}
	
	/**
	 * Checks whether from the given fromSquare it is possible to move to a
	 * neighboring square in the given direction. 
	 * 
	 * @param 	fromSquare
	 * 			The square from which the canMoveTo will be checked.
	 * @param 	direction
	 * 			The direction of the neighbor that will be checked.
	 * @return	True 	if it is possible to move in the given direction
	 * 			False	otherwise.
	 */
	public boolean canMoveTo(Square fromSquare, Direction direction){
		Square directionSquare;
		try {
			directionSquare = getNeighbor(fromSquare, direction);
		} catch (Exception e) {
			return false;
		}
		if(directionSquare.isObstructed())
			return false;
		if(direction.isDiagonal()){
			Square s1 = null;
			Square s2 = null;
			ArrayList<Direction> dirs = direction.neighborDirections();
			try{
				s1 = getNeighbor(fromSquare, dirs.get(0)); 
				s2 = getNeighbor(fromSquare, dirs.get(1));
			} catch (Exception exp){
				//This should never happen since directionSquare exists.
				assert(false);
			}
			if(s1 != null && s2 != null){
				if(s1.isObstructed() && s2.isObstructed()){
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
	public HashMap<Direction, Square> getNeighbors(Square square) throws NoSuchElementException {
		if(!contains(square)){
			throw new NoSuchElementException("The " + square + " is not a part of this " + this);
		}
		HashMap<Direction, Square> result = new HashMap<Direction, Square>();
		Square neighbor = null;
		for(Direction dir: Direction.values()){
			try{
				neighbor = getNeighbor(square, dir);
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
	public ArrayList<Square> getNeighborsAsList(Square square) {
		return new ArrayList<Square>(getNeighbors(square).values());
	}
	
	/**
	 * Finds the square related to the given destination Teleport.
	 * 
	 * @param 	destination
	 * 			The destination Teleport to find.
	 * @return	The square having the given destination as a Teleport.
	 * @throws 	IllegalArgumentException
	 * 			If the given destination is null or the square of the destination is obstructed.
	 * @throws	NoSuchElementException
	 * 			The given destination is no finable in this grid.
	 */
	public Square findSquare(Teleport destination) throws IllegalArgumentException, NoSuchElementException{
		if(destination == null){
			throw new IllegalArgumentException("The given destination is not a valid destination");
		}
		for(Square square : getAllSquares()){
			if(square.getInventory().getTeleport().equals(destination)){
				if(square.isObstructed()){
					throw new IllegalArgumentException("The destination is valid but the recieving teleport is obstructed. Try again later.");
				}
				return square;
			}
		}
		throw new NoSuchElementException("The given destination could not be found in this grid");
	}
	
	/**
	 * Returns a string representation of the grid.
	 */
	@Override
	public String toString() {
		return "Grid (hSize="+getHSize()+", vSize" + getVSize()+")";
	}
}
