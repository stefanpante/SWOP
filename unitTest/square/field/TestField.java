package square.field;

import org.junit.Before;
import org.junit.Test;
import square.Square;

import static org.junit.Assert.*;

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
		
		assertTrue(forceField.isValidGridElement(square));
		forceField.addGridElement(square);		
		assertEquals(forceField.getLength(), 1);
		assertTrue(forceField.contains(square));
	}
	
	@Test
	public void isValidSquare() {
		Square square = new Square();
		
		assertFalse(forceField.isValidGridElement(null));
		assertTrue(forceField.isValidGridElement(square));
		
		forceField.addGridElement(square);
		
		assertFalse(forceField.isValidGridElement(square));
	}

}
