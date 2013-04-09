package items;

import static org.junit.Assert.*;
import item.Teleport;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test case for Teleport.
 * 
 * @author vincent
 */
public class TestTeleport {
	
	private Teleport teleport;
	
	private Teleport teleportTwo;
	
	@Before
	public void setUpBefore() {
		teleport = new Teleport();
		teleportTwo = new Teleport();
	}

	@Test
	public void isValidDestination() {
		assertFalse(Teleport.isValidDestination(null));
	}
	
	@Test
	public void canHaveAsDestination() {
		assertFalse(teleport.canHaveAsDestination(null));
		assertFalse(teleport.canHaveAsDestination(teleport));
		assertTrue(teleport.canHaveAsDestination(teleportTwo));
	}
	
	@Test
	public void setDestination() {
		teleport.setDestination(teleportTwo);
		assertEquals(teleport.getDestination(), teleportTwo);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDestinationSelf() {
		teleport.setDestination(teleport);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDestinationNull() {
		teleport.setDestination(null);
	}

}
