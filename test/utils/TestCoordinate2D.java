package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import square.Direction;
import utils.Coordinate;

//TODO look if this test can be overhauled to use the new neighbor implementation.
@Deprecated
public class TestCoordinate2D {

	@Test
	public void testToString() {
		Coordinate coord = new Coordinate(1,1);
		assertEquals(coord.toString(), new String("(1,1)"));
	}
	
	public TestCoordinate2D() {
		
	}
	
	@Test
	public void TestEquals(){
		Coordinate c1 = new Coordinate(1,1);
		Coordinate c2 = new Coordinate(1,1);
		
		assertTrue(c1.equals(c2));
		
		c2 = new Coordinate(-1,1);
		
		assertFalse(c1.equals(c2));	
	}
	
	@Test
	public void TestGetNeighbour() {
		Coordinate coordinate = new Coordinate(1,1);
		
		Coordinate neighborNorthEast = new Coordinate(2,2);
		Coordinate neighborNorth = new Coordinate(1,2);
		Coordinate neighborNorthWest = new Coordinate(0,2);
		Coordinate neighborWest = new Coordinate(0,1);
		Coordinate neighborSouthWest = new Coordinate(0,0);
		Coordinate neighborSouth = new Coordinate(1,0);
		Coordinate neighborSouthEast = new Coordinate(2,0);
		Coordinate neighborEast = new Coordinate(2,1);
		
		assertTrue(neighborNorthEast.equals(coordinate.getNeighbor(Direction.NORTHEAST)));
		assertTrue(neighborNorth.equals(coordinate.getNeighbor(Direction.NORTH)));
		assertTrue(neighborNorthWest.equals(coordinate.getNeighbor(Direction.NORTHWEST)));
		assertTrue(neighborWest.equals(coordinate.getNeighbor(Direction.WEST)));
		assertTrue(neighborSouthWest.equals(coordinate.getNeighbor(Direction.SOUTHWEST)));
		assertTrue(neighborSouth.equals(coordinate.getNeighbor(Direction.SOUTH)));
		assertTrue(neighborSouthEast.equals(coordinate.getNeighbor(Direction.SOUTHEAST)));
		assertTrue(neighborEast.equals(coordinate.getNeighbor(Direction.EAST)));
	}
	
	@Test
	public void TestGetNeighbourNegative() {
		Coordinate coordinate = new Coordinate(-1,0);
		
		Coordinate neighborNorthEast = new Coordinate(0,1);
		Coordinate neighborNorth = new Coordinate(-1,1); 
		Coordinate neighborNorthWest = new Coordinate(-2,1);
		Coordinate neighborWest = new Coordinate(-2,0); 
		Coordinate neighborSouthWest = new Coordinate(-2,-1); 
		Coordinate neighborSouth = new Coordinate(-1,-1); 
		Coordinate neighborSouthEast = new Coordinate(0,-1); 
		Coordinate neighborEast = new Coordinate(0,0); 
		
		assertTrue(neighborNorthEast.equals(coordinate.getNeighbor(Direction.NORTHEAST)));
		assertTrue(neighborNorth.equals(coordinate.getNeighbor(Direction.NORTH)));
		assertTrue(neighborNorthWest.equals(coordinate.getNeighbor(Direction.NORTHWEST)));
		assertTrue(neighborWest.equals(coordinate.getNeighbor(Direction.WEST)));
		assertTrue(neighborSouthWest.equals(coordinate.getNeighbor(Direction.SOUTHWEST)));
		assertTrue(neighborSouth.equals(coordinate.getNeighbor(Direction.SOUTH)));
		assertTrue(neighborSouthEast.equals(coordinate.getNeighbor(Direction.SOUTHEAST)));
		assertTrue(neighborEast.equals(coordinate.getNeighbor(Direction.EAST)));
	}

}
