package grid.core;

import static org.junit.Assert.*;



import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import square.Square;
import square.obstacles.Obstacle;
import square.obstacles.Wall;

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
	//TODO check length of walls, 
	public void testCreateWalls(){
		
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		int counter = 0;
		for(Square s: squares.values()){
			if(s.isObstructed()) counter++;
		}
		System.out.println("Number of walls: " + counter );
		assertTrue(counter <= grid.getMaxCoverage());

	}

	//TODO add extra tests for grenades, 
	@Test
	public void testPlaceGrenades(){
			Grid grid = new Grid(10,10);
			HashMap<Coordinate2D, Square> squares = grid.getSquares();
			int counter = 0;
			for(Square sq: squares.values()){
				if (!sq.getInventory().isEmpty()) counter++;
			}
			
			assertTrue(counter == 5);
	}
	
	
	@Test
	public void testEdges(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		
		Coordinate2D edge1 = new Coordinate2D(0,4);
		Square sq = squares.get(edge1);
		ArrayList<Square>neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 5);
		
		Coordinate2D n = new Coordinate2D(0,3);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(1,3);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(1,4);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(0,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(1,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		
		Coordinate2D edge2 = new Coordinate2D(4,0);
		sq = squares.get(edge2);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 5);
		
		n = new Coordinate2D(3,0);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(3,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(4,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,0);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		Coordinate2D edge3 = new Coordinate2D(9,4);
		sq = squares.get(edge3);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 5);
		
		n = new Coordinate2D(9,3);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(9,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,4);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,3);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		Coordinate2D edge4 = new Coordinate2D(4,9);
		sq = squares.get(edge4);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 5);
		
		n = new Coordinate2D(4,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(3,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(3,9);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,9);
		assertTrue(neighbours.contains(squares.get(n)));
		
	}
	
	@Test
	public void testCorners(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		
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
		
		n = new Coordinate2D(9,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,0);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,1);
		assertTrue(neighbours.contains(squares.get(n)));
		
		Coordinate2D corner4 = new Coordinate2D(9,9);
		sq = squares.get(corner4);
		neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 3);
		
		n = new Coordinate2D(8,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(9,8);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(8,9);
		assertTrue(neighbours.contains(squares.get(n)));
		
	}
	
	@Test
	public void testMiddle(){
		Grid grid = new Grid(10,10);
		HashMap<Coordinate2D, Square> squares = grid.getSquares();
		
		Coordinate2D center = new Coordinate2D(5,5);
		Square sq = squares.get(center);
		ArrayList<Square> neighbours = grid.getNeighbours(sq);
		assertTrue(neighbours.size() == 8);
		
		Coordinate2D n = new Coordinate2D(6,6);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(4,4);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(4,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,4);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(6,5);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(5,6);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(4,6);
		assertTrue(neighbours.contains(squares.get(n)));
		
		n = new Coordinate2D(6,4);
		assertTrue(neighbours.contains(squares.get(n)));
		
	}

}
