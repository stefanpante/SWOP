/**
 * 
 */
package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import square.Direction;
import square.Square;
import utils.Coordinate2D;

/**
 * @author jonas
 *
 */
public class Grid {
	
	/**
	 * the minimum vertical and horizontal size are 10 squares.
	 */
	public static final int MIN_VSIZE = 10;
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
	 * Percentage of max length of a wall
	 */
	public static float LENGTH_PERCENTAGE_WALL = 0.5f;
	
	HashMap<Coordinate2D, Square> grid;
	
	/**
	 * the horizontal size and vertical size of the grid
	 */
	private int hSize, vSize;
	
	public Grid(int hSize, int vSize){
		grid = new HashMap<Coordinate2D, Square>();
		setHSize(hSize);
		setVSize(vSize);
	}
	
	public void setHSize(int size) throws IllegalArgumentException{
		if(!isValidHSize(size)) throw new IllegalArgumentException();
		this.hSize = size;
	}
	
	public void setVSize(int size) throws IllegalArgumentException{
		if(!isValidVSize(size)) throw new IllegalArgumentException();
		this.vSize = size;
	}
	
	public boolean isValidHSize(int size){
		return size >= this.MIN_HSIZE;
	} 
	
	public boolean isValidVSize(int size){
		return size >= this.MIN_VSIZE;
	}
	
	public int getHSize(){
		return hSize;
	}
	
	public int getVSize(){
		return vSize;
	}
	
	public void setSquare(Coordinate2D coordinate, Square square){
		grid.put(coordinate,square);
	}

	
	/**
	 * Returns the neighbor of the given square.
	 * 
	 * @param square
	 * @param direction
	 * @return
	 * @throws NoSuchElementException
	 */
	public Square getNeighbor(Square square, Direction direction) throws NoSuchElementException {
		int x = getCoordinate(square).getX(); 
		int y = getCoordinate(square).getY(); 
		Coordinate2D coordinate = new Coordinate2D(x, y);
		if(contains(coordinate.getNeighbor(direction)))
			return getSquare(coordinate);
		throw new NoSuchElementException();
	}
	
	
	public Square getSquare(Coordinate2D coordinate){
		return grid.get(coordinate);
	}
	
	public ArrayList<Square> getSquares(ArrayList<Coordinate2D> coordinates){
		ArrayList<Square> squares = new ArrayList<Square>();
		for(Coordinate2D coordinate : coordinates){
			squares.add(getSquare(coordinate));
		}
		return squares;
	}
	
	public Coordinate2D getCoordinate(Square square) throws IndexOutOfBoundsException{
		for(Coordinate2D coordinate : grid.keySet()){
			if(getSquare(coordinate).equals(square))
				return coordinate;
		}
		throw new IndexOutOfBoundsException("No coordinate found for square " + square);
	}
	
	public boolean contains(Square square){
		return grid.containsValue(square);
	}
	
	public boolean contains(Coordinate2D coordinate){
		return grid.containsKey(coordinate);
	}
	
	public ArrayList<Square> getAllSquares(){
		return new ArrayList<Square>(grid.values());
	}
	
	public ArrayList<Coordinate2D> getAllCoordinates(){
		return new ArrayList<Coordinate2D>(grid.keySet());
	}
	
	//TODO: canMoveTo Square, Square 

	//TODO: canMoveTo Square, Direction
	
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
	 * @return	true if it is possible to move in the given direction
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
				//This should never happen.
				assert(false);
			}
			if(s1 != null && s2 != null){
				if(s1.isObstructed() && s2.isObstructed()){
					if(s1.getObstacle().equals(s2.getObstacle())){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return A HashMap with neighbors with there direction as the key
	 * 			and the Square as value.
	 */
	public HashMap<Direction, Square> getNeighbors(Square square) {
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
	
}
