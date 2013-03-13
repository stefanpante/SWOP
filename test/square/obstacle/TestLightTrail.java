package square.obstacle;

import static org.junit.Assert.*;
import square.*;
import square.obstacles.*;

import org.junit.*;

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
		
		assertFalse(lightTrail.getSquares().contains(squareOne));
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
		lightTrail.addSquare(squareOne);
	}

	/**
	 * Duplicate square must not be valid.
	 */
	@Test
	public void testIsValidDuplicate() {
		LightTrail lightTrail = new LightTrail();

		Square squareOne = new Square();

		lightTrail.addSquare(squareOne);
		assertFalse(lightTrail.isValidSquare(squareOne));
	}
	
//	/**
//	 * Test if not connected squares are invalid.
//	 */
//	@Test
//	public void testIsValidNotConnected() {
//		LightTrail lightTrail = new LightTrail();
//		
//		lightTrail.addSquare(new Square());
//		assertFalse(lightTrail.isValidSquare(new Square()));
//	}
//	
	//TODO: what should happen with this method
//	/**
//	 * Test if not connected squares are invalid in a longer LightTrail.
//	 */
//	@Test
//	public void testIsValidNotConnectedMultiple() {
//		LightTrail lightTrail = new LightTrail();
//		
//		Square squareOne = new Square();
//		Square squareTwo = new Square();
//		
//		squareOne.setNeighbor(Direction.SOUTH, squareTwo);
//		
//		assertTrue(lightTrail.isValidSquare(squareOne));
//		
//		lightTrail.addSquare(squareOne);
//		
//		assertTrue(lightTrail.isValidSquare(squareTwo));
//		lightTrail.addSquare(squareTwo);
//		
//		assertFalse(lightTrail.isValidSquare(new Square()));
//	}

	/**
	 * Test adding new square must be valid.
	 */
	@Test
	public void testIsValid() {
		LightTrail lightTrail = new LightTrail();

		assertTrue(lightTrail.isValidSquare(new Square()));
	}

	/**
	 * Test if getLength is actually correct.
	 */
	@Test
	public void testGetLength() {
		LightTrail lightTrail = new LightTrail();

		assertEquals(lightTrail.getSquares().size(), 0);
		assertEquals(lightTrail.getSquares().size(), lightTrail.getLength());

		lightTrail.addSquare(new Square());

		assertEquals(lightTrail.getSquares().size(), 1);
		assertEquals(lightTrail.getSquares().size(), lightTrail.getLength());
	}


}