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

	HashMap<Coordinate2D, Square> grid;
	private int hSize, vSize;
	
	public Grid(int hSize, int vSize){
		setHSize(hSize);
		setVSize(vSize);
	}
	
	public void setHSize(int size){
		this.hSize = size;
	}
	
	public void setVSize(int size){
		this.vSize = size;
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
				square.setNeighbor(direction, neighbor(square, direction));
			}catch(NoSuchElementException e){
				// If there's no neighbor, nothing needs to be connected
			}
		}
	}
	
	private Square neighbor(Square square, Direction direction) throws NoSuchElementException {
		int x = getCoordinate(square).getX(); 
		int y = getCoordinate(square).getY(); 
		switch (direction) {
		case NORTH:
			y++;
			break;
		case NORTHEAST:
			x++; y++; 
			break;
		case EAST:
			x++;
			break;
		case SOUTHEAST:
			x++; y--;
			break;
		case SOUTH:
			y--;
			break;
		case SOUTHWEST:
			x--;y--;
			break;
		case WEST:
			x--;
			break;
		case NORTHWEST:
			x--; y++;
			break;
		}
		Coordinate2D coordinate = new Coordinate2D(x, y);
		if(contains(coordinate)){
			return getSquare(coordinate);
		}
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
