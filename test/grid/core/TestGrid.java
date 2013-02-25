package grid.core;

import static org.junit.Assert.*;

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
	public void testCreateSquares(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		int hSize = grid.getHorizontalSize();
		int vSize = grid.getVerticalSize();
		assertTrue(squares != null);
		assertTrue(squares.size() == hSize * vSize);
	}
	
	@Test
	public void testCreateWalls(){
		
		
	}
	
	@Test
	public void testPlaceGrenades(){
		
	}

}
