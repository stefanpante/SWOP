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
import square.obstacles.Wall;
import utils.Coordinate;

/**
 * @author jonas
 *
 */
public class TestGridBuilder {
	
	@Test
	public void testWallsCoverage(){
		int hSize = 40;
		int vSize = 40;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		double amountOfSquares = hSize*vSize;
		gb.constructSquares();
		gb.constructWalls();
		Grid grid = gb.getGrid();
		ArrayList<Coordinate> wallsPos = gb.getWallCoordinates();
		ArrayList<Wall> walls = gb.getWalls();
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
		int hSize = 40;
		int vSize = 40;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		double amountOfSquares = hSize*vSize;
		gb.constructSquares();
		gb.constructWalls();
		Grid grid = gb.getGrid();
		ArrayList<Coordinate> wallsPos = gb.getWallCoordinates();
		Coordinate lowerleft = new Coordinate(0, 0);
		assertFalse(grid.getSquare(lowerleft).isObstructed());
		assertFalse(wallsPos.contains(lowerleft));
		Coordinate upperRight = new Coordinate(hSize-1, vSize-1);
		assertFalse(grid.getSquare(upperRight).isObstructed());
		assertFalse(wallsPos.contains(upperRight));
	}
	
	@Test
	public void testWallsNoIntersection(){
		int hSize = 40;
		int vSize = 40;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		gb.constructSquares();
		gb.constructWalls();
		Grid grid = gb.getGrid();
		ArrayList<Wall> walls = gb.getWalls();
		HashSet<Square> wallNeighborSquares = new HashSet<Square>();
		HashSet<Square> squareSet = new HashSet<Square>();
		for(Wall w :walls){
			for(Square sq: w.getSquares()){
				squareSet.add(sq);
				for(Direction dir: Direction.values()){
					try{
						squareSet.add(grid.getNeighbor(sq, dir));
					} catch(NoSuchElementException e){
						//NOOP
					}
				}
			}
			
			for(Square s: squareSet){
				assertFalse(wallNeighborSquares.contains(s));
				wallNeighborSquares.add(s);
			}
			squareSet.clear();
		}
		assertTrue(true);
	}
	
	
	@Test
	public void testWallsLength(){
		int hSize = 40;
		int vSize = 40;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		gb.constructSquares();
		gb.constructWalls();
		Grid grid = gb.getGrid();
		ArrayList<Wall> walls = gb.getWalls();
		for(Wall w: walls){
			assertTrue(Math.ceil(w.getLength()/hSize) <= Grid.LENGTH_PERCENTAGE_WALL);
		}
	}
	
	
	@Test
	public void testLGNearStart(){
		int hSize = 40;
		int vSize = 40;
		GridBuilder gb = new GridBuilder(hSize, vSize);
		gb.constructSquares();
		gb.constructWalls();
		gb.constructLightGrenades();
		Grid grid = gb.getGrid();	
		//TODO: CONVENTION???
		Coordinate lowerleft = new Coordinate(0, 0);
		for(Coordinate coor = lowerleft; coor.getY() <= 2; coor = coor.getNeighbor(Direction.NORTH)){
			for(Coordinate coor2 = coor; coor2.getX() <= 2  ; coor2 = coor2.getNeighbor(Direction.EAST)){
				System.out.println(coor2);
				if(grid.getSquare(coor2).getInventory().hasLightGrenade()){
					assert(true);
					break;
				}
			}
		}
		
		Coordinate upperRight = new Coordinate(hSize-1, vSize-1);
		for(Coordinate coor = upperRight; coor.getY() >= vSize - 3; coor = coor.getNeighbor(Direction.SOUTH)){
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
