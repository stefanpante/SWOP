package grid.core;

import static org.junit.Assert.*;


import grid.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGrid {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGrid(){
		// test if a grid can be created with sizes less than minimum size
	}
	@Test
	public void testCreateSquares(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		int hSize = grid.getHorizontalSize();
		int vSize = grid.getVerticalSize();
		assertTrue(squares != null);
		assertTrue(squares.size() == hSize * vSize);
	}
	
	@Test
	//TODO check length of walls
	public void testCreateWalls(){
		Grid grid = new Grid(10,10);
		ArrayList<Obstacle> obstacles= grid.getObstacles();
		assertTrue(obstacles != null);
		int i = 0;
		
		for(Obstacle o: obstacles){
			if (o instanceof Wall){
				i++;
				assertTrue(o.getSquares().size() >= 2);
			}
		//TODO check specificaties	
		assertTrue(i <= 0.2 * grid.getHorizontalSize() * grid.getVerticalSize());
		}
		
		}
		
		
	}
	
	@Test
	public void testPlaceGrenades(){
	}

}
