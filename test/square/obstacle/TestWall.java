package square.obstacle;

import static org.junit.Assert.*;

import org.junit.*;

import square.Direction;
import square.Square;
import square.obstacles.Wall;

public class TestWall {
	
	/**
	 * Test for constructing with two duplicate squares.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException() {
		Square squareOne = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
	}
	
	/**
	 * Test for constructing and adding unconnected squares.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingUnconnected() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, squareTwo);
	}
	
	/**
	 * Test for constructing and adding a duplicate square.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithDuplicateSquares() {
		Square squareOne = new Square();
		
		Wall wall = new Wall(squareOne, squareOne);
	}
	
	/**
	 * Test for adding connected squares.
	 */
	@Test
	public void testConstructingConnectedSquares() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		Wall wall = new Wall(squareOne, squareTwo);
	}
	
	/**
	 * Test after constructing if squares are contained.
	 */
	@Test
	public void testContainsSquare() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertTrue(wall.contains(squareOne));
		assertTrue(wall.contains(squareTwo));
		assertFalse(wall.contains(new Square()));
	}
	
	/**
	 * Test for checking if length of wall is correct.
	 */
	@Test
	public void testGetLength() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertEquals(wall.getLength(), 2);
		
		Square squareThree = new Square();
		squareTwo.setNeighbor(Direction.NORTH, squareThree);
		wall.addSquare(squareThree);
		
		assertEquals(wall.getLength(), 3);
	}
	
	/**
	 * Test for adding a square to a wall.
	 */
	@Test
	public void testAddingSquare() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		squareTwo.setNeighbor(Direction.NORTH, squareThree);
		
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertTrue(wall.isValidSquare(squareThree));
		assertFalse(wall.isValidSquare(new Square()));
		assertFalse(wall.contains(squareThree));
		
		wall.addSquare(squareThree);
		
		assertTrue(wall.contains(squareThree));
	}
	
	/**
	 * Test adding square that is not connected.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddingUnconnectedSquare() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		
		Wall wall = new Wall(squareOne, squareTwo);
		wall.addSquare(squareThree);
	}
	
	/**
	 * Test isValidSquare for unconnected and connected squares.
	 */
	@Test
	public void testIsValidSquare(){
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();
		
		squareOne.setNeighbor(Direction.NORTH, squareTwo);
		squareTwo.setNeighbor(Direction.NORTH, squareThree);
		
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertTrue(wall.isValidSquare(squareThree));
		assertFalse(wall.isValidSquare(new Square()));
		
		wall.addSquare(squareThree);
		
		assertFalse(wall.isValidSquare(squareThree));
	}

}
