/**
 * 
 */
package grid;

import org.junit.Before;
import org.junit.Test;
import square.Brick;
import square.GridElement;
import square.multi.Wall;
import util.Coordinate;
import util.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Unit test case for Grid Builder.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class TestGridBuilder {
	
	private int hSize = 10;
	
	private int vSize = 10;
	
	private RandomGridBuilder gridBuilder;
	
	private Grid grid;
	
	@Before
	public void setUpBefore() {
		gridBuilder = new RandomGridBuilder(hSize, vSize);
		grid = gridBuilder.getGrid();
	}
	
	@Test
	public void testWallsCoverage(){
		ArrayList<Wall> walls = gridBuilder.getWalls();
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		for(Wall wall: walls){
			bricks.addAll(wall.getGridElements());
		}
		ArrayList<Coordinate> wallsPos = grid.getCoordinates(bricks);
		
		Coordinate coor;
		
		double amountOfSquares = hSize*vSize;
		
		int coveredSquares = 0;
		for(int x = 0; x < hSize; x++){
			for(int y = 0; y < vSize; y++){
				coor = new Coordinate(x, y);
				GridElement sq =grid.getGridElement(coor);
				if(wallsPos.contains(coor)){
					coveredSquares++;
					assertTrue(sq.isObstacle());
					assertTrue(bricks.contains(sq));
				} else {
					assertFalse(grid.getGridElement(coor).isObstacle());
					assertFalse(bricks.contains(sq));
				}
			}
		}
		
		double precentageCovered = coveredSquares/amountOfSquares;
		assertTrue(precentageCovered >= Grid.SMALLEST_WALL_LENGTH/amountOfSquares);
		assertTrue(precentageCovered <= Grid.PERCENTAGE_WALLS);
	}
	
	@Test
	public void testWallsNotOnStartPos(){
		ArrayList<Wall> walls = gridBuilder.getWalls();
		ArrayList<Brick> bricks = new ArrayList<Brick>();
		for(Wall wall: walls){
			bricks.addAll(wall.getGridElements());
		}
		ArrayList<Coordinate> wallsPos = grid.getCoordinates(bricks);
		Coordinate lowerleft = new Coordinate(0, vSize -1);

		assertFalse(grid.getGridElement(lowerleft).isObstacle());
		assertFalse(wallsPos.contains(lowerleft));
		Coordinate upperRight = new Coordinate(hSize-1, 0);
		assertFalse(grid.getGridElement(upperRight).isObstacle());
		assertFalse(wallsPos.contains(upperRight));
	}
	
	@Test
	public void testWallsNoIntersection(){
		ArrayList<Wall> walls = gridBuilder.getWalls();
		HashSet<GridElement> wallSquares = new HashSet<GridElement>();
		HashSet<GridElement> wallNeighborSquares = new HashSet<GridElement>();
		
		for(Wall w :walls){
			for(GridElement sq: w.getGridElements()){
				if(!wallSquares.contains(sq)){
					wallSquares.add(sq);
				} else  {
					//This would mean that walls intersect.
					assert(false);
				}
				for(Direction dir: Direction.values()){
					try{
						System.out.println("sq is null"  + dir == null );
						GridElement neighbor = sq.getNeighbor(dir);
						if(!w.getGridElements().contains(neighbor)){
							wallNeighborSquares.add(neighbor);
						}
					} catch(NoSuchElementException e){
						//NOOP
					}
				}
			}
		}
		
		for(GridElement s: wallNeighborSquares){
			assertFalse(wallSquares.contains(s));
		}
	}
	
	
	@Test
	public void testWallsLength(){
		ArrayList<Wall> walls = gridBuilder.getWalls();
		
		for(Wall w: walls){
			assertTrue(Math.ceil(w.getLength()/hSize) <= Grid.LENGTH_PERCENTAGE_WALL);
		}
	}
	
	/**
	 * Checks if the default random grid builder returns an
	 * empty grid.
	 */
	@Test
	public void testEmptyGrid() {
		this.gridBuilder = new RandomGridBuilder();
		this.grid = this.gridBuilder.getGrid();
		
		Iterator<GridElement> iterator = grid.getAllGridElements().iterator();
		
		while(iterator.hasNext())
			assertFalse(iterator.next().isObstacle());
	}
}
