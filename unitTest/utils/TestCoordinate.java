package utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import util.Direction;
import util.Coordinate;


public class TestCoordinate {

	@Test
	public void testToString() {
		Coordinate coord = new Coordinate(1,1);
		assertEquals(coord.toString(), new String("(1,1)"));
	}
	
	public TestCoordinate() {
		
	}
	
	@Test
	public void TestEquals1(){
		Coordinate c1 = new Coordinate(1,1);
		Coordinate c2 = new Coordinate(1,1);
		
		assertTrue(c1.equals(c2));
		
		Coordinate c3 = new Coordinate(-1,1);
		
		assertFalse(c1.equals(c3));	
	}
	
	
	@Test
	public void TestEquals2(){
		Coordinate c1 = new Coordinate(-1,-1);
		Coordinate c2 = new Coordinate(-1,-1);
		
		assertTrue(c1.equals(c2));
		
		Coordinate c3 = new Coordinate(-1,1);
		Coordinate c4 = new Coordinate(1,-1);
		
		assertFalse(c1.equals(c3));
		assertFalse(c1.equals(c4));
	}
	
	@Test
	public void TestGetNeighbour() {
		Coordinate coordinate = new Coordinate(1,1);
		
		Coordinate neighborNorthEast = new Coordinate(2,0);
		Coordinate neighborNorth = new Coordinate(1,0);
		Coordinate neighborNorthWest = new Coordinate(0,0);
		Coordinate neighborWest = new Coordinate(0,1);
		Coordinate neighborSouthWest = new Coordinate(0,2);
		Coordinate neighborSouth = new Coordinate(1,2);
		Coordinate neighborSouthEast = new Coordinate(2,2);
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
		
		Coordinate neighborNorthEast = new Coordinate(0,-1);
		Coordinate neighborNorth = new Coordinate(-1,-1);
		Coordinate neighborNorthWest = new Coordinate(-2,-1);
		Coordinate neighborWest = new Coordinate(-2,0);
		Coordinate neighborSouthWest = new Coordinate(-2,1);
		Coordinate neighborSouth = new Coordinate(-1,1);
		Coordinate neighborSouthEast = new Coordinate(0,1);
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
	
	@Test
	public void TestGetAllNeighbours(){
		Coordinate coordinate = new Coordinate(-1,0);
		
		Coordinate neighborNorthEast = new Coordinate(0,-1);
		Coordinate neighborNorth = new Coordinate(-1,-1);
		Coordinate neighborNorthWest = new Coordinate(-2,-1);
		Coordinate neighborWest = new Coordinate(-2,0);
		Coordinate neighborSouthWest = new Coordinate(-2,1);
		Coordinate neighborSouth = new Coordinate(-1,1);
		Coordinate neighborSouthEast = new Coordinate(0,1);
		Coordinate neighborEast = new Coordinate(0,0);
		
		HashMap<Direction, Coordinate> allNeighbors = coordinate.getAllNeighbors();
		assertTrue(allNeighbors.containsValue(neighborNorthEast));
		assertTrue(allNeighbors.containsValue(neighborNorth));
		assertTrue(allNeighbors.containsValue(neighborNorthWest));
		assertTrue(allNeighbors.containsValue(neighborWest));
		assertTrue(allNeighbors.containsValue(neighborSouthWest));
		assertTrue(allNeighbors.containsValue(neighborSouth));
		assertTrue(allNeighbors.containsValue(neighborSouthEast));
		assertTrue(allNeighbors.containsValue(neighborEast));
		assertEquals(8, allNeighbors.size());
	}
	
	 @Test
	    public void coordinateTest(){
	        Coordinate c1 = new Coordinate(2,6);
	        Coordinate c2 = new Coordinate(4,8);
	        assertEquals(c1.directionTo(c2), Direction.SOUTHEAST);
	    }

	    @Test
	    public void coordinate2Test(){
	        Coordinate c1 = new Coordinate(2,6);
	        Coordinate c2 = new Coordinate(4,8);
	        assertEquals(c2.directionTo(c1), Direction.NORTHWEST);
	    }

	    @Test
	    public void coordinate3Test(){
	        Coordinate c1 = new Coordinate(1,6);
	        Coordinate c2 = new Coordinate(2,6);
	        assertEquals(c1.directionTo(c2), Direction.EAST);
	    }

	    @Test
	    public void coordinate4Test(){
	        Coordinate c1 = new Coordinate(1,6);
	        Coordinate c2 = new Coordinate(2,6);
	        assertEquals(c2.directionTo(c1), Direction.WEST);
	    }


	    @Test
	    public void coordinate5Test(){
	        Coordinate c1 = new Coordinate(1,1);
	        Coordinate c2 = new Coordinate(4,4);
	        System.out.println(c1.getCoordinatesTo(c2));
	    }

}
