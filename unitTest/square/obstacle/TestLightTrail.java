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

		lightTrail.addGridElement(squareOne);
		assertEquals(lightTrail.getLength(), 1);
		
		lightTrail.addGridElement(squareTwo);
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
		Square squareFive = new Square();

		assertEquals(lightTrail.getLength(), 0); 

		lightTrail.addGridElement(squareOne);
		assertEquals(lightTrail.getLength(), 1);
		
		lightTrail.addGridElement(squareTwo);
		assertEquals(lightTrail.getLength(), 2);
		
		lightTrail.addGridElement(squareThree);
		assertEquals(lightTrail.getLength(), 3);
		
		lightTrail.addGridElement(squareFour);
		assertEquals(lightTrail.getLength(), 4);
		
		lightTrail.addGridElement(squareFive);
		assertEquals(lightTrail.getLength(), 4);
		
		assertFalse(lightTrail.getGridElements().contains(squareOne));
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

		lightTrail.addGridElement(squareOne);
		lightTrail.addGridElement(squareOne);
	}

	/**
	 * Duplicate square must not be valid.
	 */
	@Test
	public void testIsValidDuplicate() {
		LightTrail lightTrail = new LightTrail();

		Square squareOne = new Square();

		lightTrail.addGridElement(squareOne);
		assertFalse(lightTrail.isValidGridElement(squareOne));
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

		lightTrail.addGridElement(new Square());

		assertEquals(lightTrail.getGridElements().size(), 1);
		assertEquals(lightTrail.getGridElements().size(), lightTrail.getLength());
	}


}