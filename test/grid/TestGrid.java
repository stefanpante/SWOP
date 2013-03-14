/**
 * 
 */
package grid;

import static org.junit.Assert.*;

import java.util.ArrayList;


import org.junit.Test;

import square.obstacles.LightTrail;

import square.Direction;
import square.Square;
import square.obstacles.Wall;
import utils.Coordinate;

/**
 * @author jonas
 *
 */
public class TestGrid {
	
	public Square getSquare(){
		return new Square();
	}
	
	/**
	 * Test canMoveTo in the corners without walls.
	 */
	@Test 
	public void testCanMoveToCorners1(){
			GridBuilder gb = new GridBuilder(10,10);
			gb.constructSquares();
			Grid grid = gb.getGrid();
			Square bl = grid.getSquare(new Coordinate(0,9));
			Square tr = grid.getSquare(new Coordinate(9,0));
			
			//TEST CORNERS.
			//	Bottom Left
			//		Possible
			assertTrue(grid.canMoveTo(bl,Direction.NORTH));
			assertTrue(grid.canMoveTo(bl,Direction.NORTHEAST));
			assertTrue(grid.canMoveTo(bl,Direction.EAST));
			//		Impossible
			assertFalse(grid.canMoveTo(bl,Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(bl,Direction.SOUTH));
			assertFalse(grid.canMoveTo(bl,Direction.SOUTHWEST));
			assertFalse(grid.canMoveTo(bl,Direction.WEST));
			assertFalse(grid.canMoveTo(bl,Direction.NORTHWEST));
			//	Top Right
			//		Impossible
			assertFalse(grid.canMoveTo(tr, Direction.NORTH));
			assertFalse(grid.canMoveTo(tr,Direction.NORTHEAST));
			assertFalse(grid.canMoveTo(tr,Direction.EAST));
			assertFalse(grid.canMoveTo(tr,Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(tr, Direction.NORTHWEST));
			//		Possible
			assertTrue(grid.canMoveTo(tr,Direction.SOUTH));
			assertTrue(grid.canMoveTo(tr,Direction.SOUTHWEST));
			assertTrue(grid.canMoveTo(tr,Direction.WEST));
					
		}
		
		/**
		 * Test the canMoveTo with walls
		 */
		@Test 
		public void testCanMoveTo2(){
			GridBuilder gb = new GridBuilder(10, 10);
			gb.constructSquares();
			Grid grid = gb.getGrid();
			Square bl = grid.getSquare(new Coordinate(0,9));
			Square tr = grid.getSquare(new Coordinate(9,0));
			
			
			Wall blWall = new Wall(grid.getNeighbor(bl, Direction.EAST),grid.getNeighbor(bl, Direction.NORTHEAST));
			Wall trWall = new Wall(grid.getNeighbor(tr,Direction.SOUTHWEST), grid.getNeighbor(tr,Direction.SOUTH));

			
			//TEST CORNERS.
			//	Bottom Left
			//		Possible
			assertTrue(grid.canMoveTo(bl, Direction.NORTH));
			//		Impossible
			assertFalse(grid.canMoveTo(bl,Direction.NORTHEAST));
			assertFalse(grid.canMoveTo(bl,Direction.EAST));
			assertFalse(grid.canMoveTo(bl,Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(bl,Direction.SOUTH));
			assertFalse(grid.canMoveTo(bl,Direction.SOUTHWEST));
			assertFalse(grid.canMoveTo(bl,Direction.WEST));
			assertFalse(grid.canMoveTo(bl,Direction.NORTHWEST));
			//	Top Right
			//		Impossible
			assertFalse(grid.canMoveTo(tr, Direction.NORTH));
			assertFalse(grid.canMoveTo(tr, Direction.NORTHEAST));
			assertFalse(grid.canMoveTo(tr, Direction.EAST));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTH));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTHWEST));
			//		Possible
			assertTrue(grid.canMoveTo(tr, Direction.WEST));
			//		Impossible
			assertFalse(grid.canMoveTo(tr, Direction.NORTHWEST));	
			
		}
		
		/**
		 * Tests canMoveTo with a lightTrail
		 */
		@Test
		public void testCanMoveTo3(){
			GridBuilder gb = new GridBuilder(10, 10);
			gb.constructSquares();
			Grid grid = gb.getGrid();
			Square bl = grid.getSquare(new Coordinate(0,9));
			Square tr = grid.getSquare(new Coordinate(9,0));
			
			LightTrail blLightTrail = new LightTrail();
			try {
				blLightTrail.addSquare(grid.getNeighbor(bl, Direction.NORTH));
				blLightTrail.addSquare(grid.getNeighbor(bl, Direction.EAST));
			} catch (Exception e) {
				System.out.println("YES");
			}
			
			assertTrue(blLightTrail.contains(grid.getNeighbor(bl, Direction.NORTH)));
			assertTrue(blLightTrail.contains(grid.getNeighbor(bl, Direction.EAST)));
			
			LightTrail trLightTrail = new LightTrail();
			trLightTrail.addSquare(grid.getNeighbor(tr, Direction.SOUTH));
			trLightTrail.addSquare(grid.getNeighbor(tr, Direction.WEST));

			//TEST CORNERS.
			//	Bottem Left
			//		Impossible
			assertTrue(grid.getNeighbor(bl,Direction.NORTH).isObstructed());
			assertTrue(grid.getNeighbor(bl,Direction.EAST).isObstructed());

			assertFalse(grid.canMoveTo(bl, Direction.NORTH));
			assertFalse(grid.canMoveTo(bl, Direction.NORTHEAST));
			assertFalse(grid.canMoveTo(bl, Direction.EAST));
			assertFalse(grid.canMoveTo(bl, Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(bl, Direction.SOUTH));
			assertFalse(grid.canMoveTo(bl, Direction.SOUTHWEST));
			assertFalse(grid.canMoveTo(bl, Direction.WEST));
			assertFalse(grid.canMoveTo(bl, Direction.NORTHWEST));
			//	Top Right
			assertFalse(grid.canMoveTo(tr, Direction.NORTH));
			assertFalse(grid.canMoveTo(tr, Direction.NORTHEAST));
			assertFalse(grid.canMoveTo(tr, Direction.EAST));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTHEAST));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTH));
			assertFalse(grid.canMoveTo(tr, Direction.SOUTHWEST));
			assertFalse(grid.canMoveTo(tr, Direction.WEST));
			assertFalse(grid.canMoveTo(tr, Direction.NORTHWEST));		
			
		}
		
		/**
		 * Tests using getSquares() by adding a bunch of squares to the grid.
		 */
		@Test
		public void testGetSquares(){
			GridBuilder gb = new GridBuilder(10, 10);
			gb.constructSquares();
			Grid grid = gb.getGrid();
			Coordinate co11 = new Coordinate(1, 1);
			Coordinate co01 = new Coordinate(0, 1);
			Coordinate co00 = new Coordinate(0, 0);
			Coordinate coMin11 = new Coordinate(-1, 1);
			Coordinate co99= new Coordinate(9, 9);

			ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
			coordinates.add(co11);
			coordinates.add(co01);
			coordinates.add(co00);
			coordinates.add(coMin11);
			coordinates.add(co99);
			
			ArrayList<Square> squares = grid.getSquares(coordinates);
			assertTrue(squares.contains(grid.getSquare(co11)));
			assertTrue(squares.contains(grid.getSquare(co01)));
			assertTrue(squares.contains(grid.getSquare(co00)));
			assertTrue(squares.contains(grid.getSquare(co99)));
			assertEquals(4,squares.size());
		}
		
		@Test
		public void testLulz(){
			assert(true);
		}
		
}
