package square.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests switching of state of a force field.
 * 
 * @author vincentreniers
 */
public class TestForceField {
	
	private ForceField forceField;
	
	@Before
	public void setUpBefore() {
		this.forceField = new ForceField();
	}
	
	@Test
	public void decreaseActions() {
		assertTrue(this.forceField.isActive());
		
		this.forceField.decreaseActions();
		this.forceField.decreaseActions();
		
		assertFalse(this.forceField.isActive());
	}

}
