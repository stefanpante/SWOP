package old;

import static org.junit.Assert.*;

import org.junit.Test;

import square.Direction;
import utils.Coordinate2D;

//TODO look if this test can be overhauled to use the new neighbor implementation.
@Deprecated
public class TestCoordinate2D {

	@Test
	public void testToString() {
		Coordinate2D coord = new Coordinate2D(1,1);
		assertEquals(coord.toString(), new String("(1,1)"));
	}
	
	public TestCoordinate2D() {
		
	}
	
	@Test
	public void TestEquals(){
		Coordinate2D c1 = new Coordinate2D(1,1);
		Coordinate2D c2 = new Coordinate2D(1,1);
		
		assertTrue(c1.equals(c2));
		
		c2 = new Coordinate2D(-1,1);
		
		assertFalse(c1.equals(c2));	
	}
	
	@Test
	public void TestGetNeighbour() {
		Coordinate2D coordinate = new Coordinate2D(1,1);
		
		Coordinate2D neighborNorthEast = new Coordinate2D(2,2);
		Coordinate2D neighborNorth = new Coordinate2D(1,2);
		Coordinate2D neighborNorthWest = new Coordinate2D(0,2);
		Coordinate2D neighborWest = new Coordinate2D(0,1);
		Coordinate2D neighborSouthWest = new Coordinate2D(0,0);
		Coordinate2D neighborSouth = new Coordinate2D(1,0);
		Coordinate2D neighborSouthEast = new Coordinate2D(2,0);
		Coordinate2D neighborEast = new Coordinate2D(2,1);
		
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
		Coordinate2D coordinate = new Coordinate2D(-1,0);
		
		Coordinate2D neighborNorthEast = new Coordinate2D(0,1);
		Coordinate2D neighborNorth = new Coordinate2D(-1,1); 
		Coordinate2D neighborNorthWest = new Coordinate2D(-2,1);
		Coordinate2D neighborWest = new Coordinate2D(-2,0); 
		Coordinate2D neighborSouthWest = new Coordinate2D(-2,-1); 
		Coordinate2D neighborSouth = new Coordinate2D(-1,-1); 
		Coordinate2D neighborSouthEast = new Coordinate2D(0,-1); 
		Coordinate2D neighborEast = new Coordinate2D(0,0); 
		
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
