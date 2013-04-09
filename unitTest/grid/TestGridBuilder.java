/**
 * 
 */
package grid;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.Test;

import square.Direction;
import square.Square;
import square.obstacle.Wall;
import util.Coordinate;

/**
 * @author jonas
 *
 */
public class TestGridBuilder {
	
	@Test
	public void testWallsCoverage(){
		int hSize = 10;
		int vSize = 10;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		double amountOfSquares = hSize*vSize;
		Grid grid = gb.getGrid();
		ArrayList<Wall> walls = gb.getWalls();
		ArrayList<Coordinate> wallsPos = gb.getCoordinatesOfWalls(walls);
		Coordinate coor;
		int coveredSquares = 0;
		for(int x = 0; x < hSize; x++){
			for(int y = 0; y < vSize; y++){
				coor = new Coordinate(x, y);
				Square sq =grid.getSquare(coor);
				if(wallsPos.contains(coor)){
					coveredSquares++;
					assertTrue(sq.isObstructed());
					assertTrue(walls.contains(sq.getObstacle()));
				} else {
					assertFalse(grid.getSquare(coor).isObstructed());
					assertFalse(walls.contains(sq.getObstacle()));
				}
			}
		}
		double precentageCovered = coveredSquares/amountOfSquares;
		assertTrue(precentageCovered >= Grid.SMALLEST_WALL_LENGTH/amountOfSquares);
		assertTrue(precentageCovered <= Grid.PERCENTAGE_WALLS);
	}
	
	@Test
	public void testWallsNotOnStartPos(){
		int hSize = 10;
		int vSize = 10;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		double amountOfSquares = hSize*vSize;
		Grid grid = gb.getGrid();
		ArrayList<Coordinate> wallsPos = gb.getCoordinatesOfWalls(gb.getWalls());
		Coordinate lowerleft = new Coordinate(0, vSize -1);

		assertFalse(grid.getSquare(lowerleft).isObstructed());
		assertFalse(wallsPos.contains(lowerleft));
		Coordinate upperRight = new Coordinate(hSize-1, 0);
		assertFalse(grid.getSquare(upperRight).isObstructed());
		assertFalse(wallsPos.contains(upperRight));
	}
	
	@Test
	public void testWallsNoIntersection(){
		int hSize = 10;
		int vSize = 10;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		Grid grid = gb.getGrid();
		ArrayList<Wall> walls = gb.getWalls();
		HashSet<Square> wallSquares = new HashSet<Square>();
		HashSet<Square> wallNeighborSquares = new HashSet<Square>();
		HashSet<Square> squareSet = new HashSet<Square>();
		for(Wall w :walls){
			for(Square sq: w.getSquares()){
				if(!wallSquares.contains(sq)){
					wallSquares.add(sq);
				} else  {
					//This would mean that walls intersect.
					assert(false);
				}
				for(Direction dir: Direction.values()){
					try{
						Square neighbor = grid.getNeighbor(sq, dir);
						if(!w.getSquares().contains(neighbor)){
							wallNeighborSquares.add(neighbor);
						}
					} catch(NoSuchElementException e){
						//NOOP
					}
				}
			}
		}
		
		for(Square s: wallNeighborSquares){
			assertFalse(wallSquares.contains(s));
		}
	}
	
	
	@Test
	public void testWallsLength(){
		int hSize = 10;
		int vSize = 10;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		Grid grid = gb.getGrid();
		ArrayList<Wall> walls = gb.getWalls();
		for(Wall w: walls){
			assertTrue(Math.ceil(w.getLength()/hSize) <= Grid.LENGTH_PERCENTAGE_WALL);
		}
	}
	
	
	@Test
	public void testLGNearStart(){
		int hSize = 10;
		int vSize = 10;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		Grid grid = gb.getGrid();
		Coordinate lowerleft = new Coordinate(0, vSize-1);
		for(Coordinate coor = lowerleft; coor.getY() >= vSize-3; coor = coor.getNeighbor(Direction.NORTH)){
			for(Coordinate coor2 = coor; coor2.getX() <= 2  ; coor2 = coor2.getNeighbor(Direction.EAST)){
				System.out.println(coor2);
				if(grid.getSquare(coor2).getInventory().hasLightGrenade()){
					assert(true);
					break;
				}
			}
		}
		
		Coordinate upperRight = new Coordinate(hSize-1, 0);
		for(Coordinate coor = upperRight; coor.getY() <= 2; coor = coor.getNeighbor(Direction.SOUTH)){
			for(Coordinate coor2 = coor; coor2.getX() >= hSize - 3  ; coor2 = coor2.getNeighbor(Direction.WEST)){
				System.out.println(coor2);
				if(grid.getSquare(coor2).getInventory().hasLightGrenade()){
					assert(true);
					break;
				}
			}
		}
	}
}
