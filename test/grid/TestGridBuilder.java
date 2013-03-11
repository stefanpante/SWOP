/**
 * 
 */
package grid;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import utils.Coordinate;
import utils.Coordinate2D;

/**
 * @author jonas
 *
 */
public class TestGridBuilder {
	
	@Test
	public void testWalls(){
		GridBuilder gb = new GridBuilder(100, 100);
		gb.constructSquares();
		gb.constructWalls();
		Grid grid = gb.getGrid();
		ArrayList<Coordinate> wallsPos = gb.getWalls();
		Coordinate coor;
		for(int x = 0; x < grid.getHSize(); x++){
			for(int y = 0; y < grid.getVSize(); y++){
				coor = new Coordinate(x, y);
				if(wallsPos.contains(coor)){
					assertTrue(grid.getSquare(coor).isObstructed());
				} else {
					assertFalse(grid.getSquare(coor).isObstructed());
				}
			}
		}
	}
}
