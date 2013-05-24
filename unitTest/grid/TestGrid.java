/**
 * 
 */
package grid;

import org.junit.Test;
import square.GridElement;
import square.Square;
import util.Coordinate;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class TestGrid {
	
	public Square getSquare(){
		return new Square();
	}
	
	
		
		/**
		 * Tests using getGridElements() by adding a bunch of squares to the grid.
		 */
		@Test
		public void testGetGridElements(){
			AbstractGridBuilder gb = new RandomGridBuilder();
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
			
			ArrayList<GridElement> squares = grid.getGridElements(coordinates);
			assertTrue(squares.contains(grid.getGridElement(co11)));
			assertTrue(squares.contains(grid.getGridElement(co01)));
			assertTrue(squares.contains(grid.getGridElement(co00)));
			assertTrue(squares.contains(grid.getGridElement(co99)));
			assertEquals(4,squares.size());
		}
		
		
}
