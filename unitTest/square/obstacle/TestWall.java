package square.obstacle;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	public void testConstructingOneNull() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, null);
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
	public void testConstructingSquares() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
		Wall wall = new Wall(squareOne, squareTwo);
		assertTrue(squareOne.isObstructed());
		assertTrue(squareTwo.isObstructed());
		assertTrue(wall.equals(squareOne.getObstacle()));
		assertTrue(wall.equals(squareTwo.getObstacle()));
	}
	
	/**
	 * Test after constructing if squares are contained.
	 */
	@Test
	public void testContainsSquare() {
		Square squareOne = new Square();
		Square squareTwo = new Square();
		
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
		
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertEquals(wall.getLength(), 2);
		
		Square squareThree = new Square();
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
				
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertTrue(wall.isValidSquare(squareThree));
		assertTrue(wall.isValidSquare(new Square()));
		assertFalse(wall.contains(squareThree));
		
		wall.addSquare(squareThree);
		
		assertTrue(wall.contains(squareThree));
	}
	
	/**
	 * Test isValidSquare for unconnected and connected squares.
	 */
	@Test
	public void testIsValidSquare(){
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();
		
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertFalse(wall.isValidSquare(squareTwo));
		assertFalse(wall.isValidSquare(null));
		assertTrue(wall.isValidSquare(squareThree));
		
		wall.addSquare(squareThree);
		
		assertFalse(wall.isValidSquare(squareThree));
	}
	
	/**
	 * Test adding a bunch of squares at once.
	 */
	@Test
	public void testConstructorSquareList(){
		Square square1 = new Square();
		Square square2 = new Square();
		Square square3 = new Square();
		Square square4 = new Square();
		
		ArrayList<Square> list = new ArrayList<Square>();
		
		list.add(square1);
		list.add(square2);
		list.add(square3);
		list.add(square4);
		
		Wall w = new Wall(list);
		
		assertTrue(w.contains(square1));
		assertTrue(w.contains(square2));
		assertTrue(w.contains(square3));
		assertTrue(w.contains(square4));
		assertFalse(w.contains(null));
		assertFalse(w.contains(new Square()));
		assertTrue(w.equals(square1.getObstacle()));
		assertTrue(w.equals(square2.getObstacle()));
		assertTrue(w.equals(square3.getObstacle()));
		assertTrue(w.equals(square4.getObstacle()));
		assertFalse(w.equals((new Square().getObstacle())));
		assertTrue(square1.isObstructed());
		assertTrue(square2.isObstructed());
		assertTrue(square3.isObstructed());
		assertTrue(square4.isObstructed());
	}
	
	
	/**
	 * Test adding a bunch of squares at once.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorSquareList2(){
		Square square1 = new Square();
		Square square2 = new Square();
		Square square3 = new Square();
		Square square4 = new Square();
		
		ArrayList<Square> list = new ArrayList<Square>();
		
		list.add(square1);
		list.add(square2);
		list.add(square3);
		list.add(square4);
		//Double added should fail!!!
		list.add(square4);
		list.add(null);
		
		Wall w = new Wall(list);
	}
}