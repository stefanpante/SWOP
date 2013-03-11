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
	
	private Square bottomLeft;
	private Square topRight;

	HashMap<Coordinate2D, Square> grid;
	
	/**
	 * the horizontal size and vertical size of the grid
	 */
	private int hSize, vSize;
	
	public Grid(int hSize, int vSize){
		GridBuilder builder = new GridBuilder(hSize, vSize);
		setHSize(hSize);
		setVSize(vSize);
		builder.constructSquares();
		builder.constructWalls();
		
		this.bottomLeft = builder.getBottomLeft();
		this.topRight = builder.getTopRight();
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
		connect(coordinate, square);
		grid.put(coordinate,square);
	}
	
	/**
	 * @param coordinate
	 * @param square
	 */
	private void connect(Coordinate2D coordinate, Square square) {
		for(Direction direction: Direction.values()){
			try{
				square.setNeighbor(direction, getNeighbor(square, direction));
			}catch(NoSuchElementException e){
				// If there's no neighbor, nothing needs to be connected
			}
		}
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
	
	public Coordinate2D getCoordinate(Square square){
		for(Coordinate2D coordinate : grid.keySet()){
			if(getSquare(coordinate).equals(square))
				return coordinate;
		}
		return null;
	}
	
	public Square getBottomLeft() {
		return this.bottomLeft;
	}
	
	public Square getTopRight() {
		return this.topRight;
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
}
