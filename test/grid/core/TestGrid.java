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
		assertTrue(squares != null);
		assertTrue(squares.size() == grid.getSize());
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
				// wall cannot contain startposition
				assertFalse(o.contains(grid.getLowerLeft()));
				assertFalse(o.contains(grid.getUpperRight()));
			}
			//TODO check specificaties, check of muren correct geplaatst zijn ten opzichte van elkaar
			assertTrue(i <= Grid.PERCENTAGEWALLS * grid.getSize());
		}

	}

	//TODO add extra tests for grenades
	@Test
	public void testPlaceGrenades(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		int counter = 0;
		for(Square sq: squares.values()){
			if (!sq.getItems().isEmpty()) counter++;
		}

		int numberOfGrenades =(int) (Math.ceil(grid.getSize() * Grid.PERCENTAGEGRENADES));

		assertTrue(numberOfGrenades == counter);
	}
	
	@Test
	public void testGetNeighbours(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		
		// test corners
		Coordinate2D corner1 = new Coordinate2D(0,0);
		Square sq = squares.get(corner1);
		ArrayList<Square> neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 3);
		
		Coordinate2D n = new Coordinate2D(0,1);
		assertTrue(neighbours.contains(squares.get(n)));
		n = new Coordinate2D(1,0);
		assertTrue(neighbours.contains(squares.get(n)));
		n = new Coordinate2D(1,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		Coordinate2D corner2 = new Coordinate2D(0,9);
		sq = squares.get(corner2);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 3);
		
		n = new Coordinate2D(1,9);
		assertTrue(neighbours.contains(squares.get(n)));
		n = new Coordinate2D(0,8);
		assertTrue(neighbours.contains(squares.get(n)));
		n = new Coordinate2D(1,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		Coordinate2D corner3 = new Coordinate2D(9,0);
		sq = squares.get(corner3);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 3);
		
		Coordinate2D corner4 = new Coordinate2D(9,9);
		sq = squares.get(corner4);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 3);
		// test Edges
		Coordinate2D edge1;
		Coordinate2D edge2;
		Coordinate2D edge3;
		Coordinate2D edge4;
		
		// Test 8 neighbours
		
		Coordinate2D center;
	}

}
