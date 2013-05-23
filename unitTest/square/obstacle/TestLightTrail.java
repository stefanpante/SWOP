package square.obstacle;

import static org.junit.Assert.*;

import square.*;

import org.junit.*;
import square.multi.LightTrail;

public class TestLightTrail {


	/**
	 * Maximum length must remain correct after adding more squares.
	 */
	@Test
	public void testLightTrailLength() {
		LightTrail lightTrail = new LightTrail();
		
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();

		assertEquals(lightTrail.getLength(), 0); 

		lightTrail.addSquare(squareOne);
		assertEquals(lightTrail.getLength(), 1);
		
		lightTrail.addSquare(squareTwo);
		assertEquals(lightTrail.getLength(), 2);
		assertTrue(lightTrail.squaresPointBack());
	}

	/**
	 * The lightTrail shouldn't be longer than three squares
	 */
	@Test
	public void testLightTrailLength2(){
		LightTrail lightTrail = new LightTrail();
		
		Square squareOne = new Square();
		Square squareTwo = new Square();
		Square squareThree = new Square();
		Square squareFour = new Square();

		assertEquals(lightTrail.getLength(), 0); 

		lightTrail.addSquare(squareOne);
		assertEquals(lightTrail.getLength(), 1);
		
		lightTrail.addSquare(squareTwo);
		assertEquals(lightTrail.getLength(), 2);
		
		lightTrail.addSquare(squareThree);
		assertEquals(lightTrail.getLength(), 3);
		
		lightTrail.addSquare(squareFour);
		assertEquals(lightTrail.getLength(), 3);
		
		assertFalse(lightTrail.getGridElements().contains(squareOne));
		assertTrue(lightTrail.squaresPointBack());
	}
	
	/**
	 * Empty light trail length must be 0.
	 */
	@Test
	public void testEmptyLightTrailLength() {
		LightTrail lightTrail = new LightTrail();
		assertEquals(lightTrail.getLength(), 0);
	}

	/**
	 * Adding a duplicate square must result in an exception.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddingDuplicateSquare() {
		LightTrail lightTrail = new LightTrail();

		Square squareOne = new Square();

		lightTrail.addSquare(squareOne);
		assertTrue(lightTrail.squaresPointBack());
		lightTrail.addSquare(squareOne);
	}
	
	/**
	 * Test if adding a square results in the square
	 * beeing set as obstructed.
	 */
	@Test
	public void testAdding() {
		LightTrail lightTrail = new LightTrail();
		
		Square squareOne = new Square();
		assertFalse(squareOne.isObstructed());
		
		lightTrail.addSquare(squareOne);
		assertTrue(squareOne.isObstructed());
		
		Square squareTwo = new Square();
		assertFalse(squareTwo.isObstructed());
		
		lightTrail.addSquare(squareTwo);
		assertTrue(squareTwo.isObstructed());
		assertTrue(lightTrail.squaresPointBack());
	}
	
	/**
	 * Test adding a square after maximum capacity,
	 * the last one is set to non obstructed.
	 */
	@Test
	public void testAddingMax() {
		LightTrail lightTrail = new LightTrail();
		
		Square firstSquare = new Square();
		lightTrail.addSquare(firstSquare);
		
		assertTrue(firstSquare.isObstructed());
		
		lightTrail.addSquare(new Square());
		lightTrail.addSquare(new Square());
		lightTrail.addSquare(new Square());
		
		assertFalse(firstSquare.isObstructed());
		assertTrue(lightTrail.squaresPointBack());
	}

	/**
	 * Duplicate square must not be valid.
	 */
	@Test
	public void testIsValidDuplicate() {
		LightTrail lightTrail = new LightTrail();

		Square squareOne = new Square();

		lightTrail.addSquare(squareOne);
		assertFalse(lightTrail.isValidGridElement(squareOne));
		assertTrue(lightTrail.squaresPointBack());
	}

	/**
	 * Test adding new square must be valid.
	 */
	@Test
	public void testIsValid() {
		LightTrail lightTrail = new LightTrail();

		assertTrue(lightTrail.isValidGridElement(new Square()));
	}

	/**
	 * Test if getLength is actually correct.
	 */
	@Test
	public void testGetLength() {
		LightTrail lightTrail = new LightTrail();

		assertEquals(lightTrail.getGridElements().size(), 0);
		assertEquals(lightTrail.getGridElements().size(), lightTrail.getLength());

		lightTrail.addSquare(new Square());

		assertEquals(lightTrail.getGridElements().size(), 1);
		assertEquals(lightTrail.getGridElements().size(), lightTrail.getLength());
		assertTrue(lightTrail.squaresPointBack());
	}


}