package square.obstacle;

import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.*;

import square.Brick;
import square.Square;
import square.multi.Wall;

public class TestWall {
	

	/**
	 * Test for constructing and adding unconnected squares.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingOneNull() {
		Brick brick = new Brick();
		
		Wall wall = new Wall(brick, null);
	}
	
	/**
	 * Test for constructing and adding a duplicate square.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructingWithDuplicateSquares() {
		Brick brick = new Brick();
		Wall wall = new Wall(brick, brick);
	}

	/**
	 * Test for adding a square to a wall.
	 */
	@Test
	public void testAddingSquare() {
		Brick squareOne = new Brick();
		Brick squareTwo = new Brick();
		Brick squareThree = new Brick();
				
		Wall wall = new Wall(squareOne, squareTwo);
		
		assertTrue(wall.isValidGridElement(squareThree));
		assertTrue(wall.isValidGridElement(new Brick()));
		assertFalse(wall.contains(squareThree));
		
		wall.addGridElement(squareThree);
		
		assertTrue(wall.contains(squareThree));

	}
	
	/**
	 * Test isValidSquare for unconnected and connected squares.
	 */
	@Test
	public void testIsValidSquare(){
		Brick brickOne = new Brick();
		Brick squareTwo = new Brick();
		Brick squareThree = new Brick();
		
		Wall wall = new Wall(brickOne, squareTwo);
		
		assertFalse(wall.isValidGridElement(squareTwo));
		assertFalse(wall.isValidGridElement(null));
		assertTrue(wall.isValidGridElement(squareThree));
		
		wall.addGridElement(squareThree);
		
		assertFalse(wall.isValidGridElement(squareThree));

	}
	
	/**
	 * Test adding a bunch of squares at once.
	 */
	@Test
	public void testConstructorSquareList(){
		Brick square1 = new Brick();
		Brick square2 = new Brick();
		Brick square3 = new Brick();
		Brick square4 = new Brick();
		
		ArrayList<Brick> list = new ArrayList<Brick>();
		
		list.add(square1);
		list.add(square2);
		list.add(square3);
		list.add(square4);
		
		Wall wall = new Wall(list);
		
		assertTrue(wall.contains(square1));
		assertTrue(wall.contains(square2));
		assertTrue(wall.contains(square3));
		assertTrue(wall.contains(square4));
		assertFalse(wall.contains(null));
		assertFalse(wall.contains(new Brick()));

	}
	
	
	/**
	 * Test adding a bunch of squares at once.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorSquareList2(){
		Brick square1 = new Brick();
		Brick square2 = new Brick();
		Brick square3 = new Brick();
		Brick square4 = new Brick();
		
		ArrayList<Brick> list = new ArrayList<Brick>();
		
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
