package grid.core;

import static org.junit.Assert.*;
import grid.obstacles.LightTrail;

import org.junit.*;

public class TestLightTrail {
	
	
	/**
	 * Maximum length must remain correct after adding more squares.
	 */
	@Test
	public void testLightTrailLength() {
		LightTrail lightTrail = new LightTrail();
		
		for (int i=1; i <= LightTrail.MAX_LENGTH; i++) {
			lightTrail.addSquare(new Square());
		}
		
		assertEquals(lightTrail.getLength(), LightTrail.MAX_LENGTH);
		
		lightTrail.addSquare(new Square());
		
		assertEquals(lightTrail.getLength(), LightTrail.MAX_LENGTH);
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
