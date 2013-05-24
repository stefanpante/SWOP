package square.field;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
		
		this.forceField.decreaseActions();
		this.forceField.decreaseActions();
		
		assertTrue(this.forceField.isActive());
	}

}
