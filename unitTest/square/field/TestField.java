package square.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import square.Square;

/**
 * Test case for the Field (collection of squares).
 * 
 * @author vincentreniers
 */
public class TestField {
	
	private ForceField forceField;
	
	@Before
	public void setUpBefore() {
		forceField = new ForceField();
	}

	@Test
	public void addSquare() {
		Square square = new Square();
		
		assertTrue(forceField.isValidSquare(square));
		
		forceField.addSquare(square);
		
		assertEquals(forceField.getLength(), 1);
		assertTrue(forceField.contains(square));
	}
	
	@Test
	public void isValidSquare() {
		Square square = new Square();
		
		assertFalse(forceField.isValidSquare(null));
		assertTrue(forceField.isValidSquare(square));
		
		forceField.addSquare(square);
		
		assertFalse(forceField.isValidSquare(square));
	}

}
