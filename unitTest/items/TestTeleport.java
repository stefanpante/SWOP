package items;

import static org.junit.Assert.*;
import item.Teleport;

import org.junit.Before;
import org.junit.Test;

import square.Square;

/**
 * Unit test case for Teleport.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
		Square square = new Square();
		square.getInventory().addItem(teleport);
		assertFalse(teleport.canHaveAsDestination(square));
		
		Square sq2 = new Square();
		sq2.getInventory().addItem(teleportTwo);
		assertTrue(teleport.canHaveAsDestination(sq2));
	}
	
	@Test
	public void setDestination() {
		Square square = new Square();
		Square square2 = new Square();
		square.getInventory().addItem(teleport);
		square2.getInventory().addItem(teleportTwo);
		teleport.setDestination(square2);
		assertEquals(teleport.getDestination(), square2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDestinationSelf() {
		Square sq = new Square();
		sq.getInventory().addItem(teleport);
		teleport.setDestination(sq);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDestinationNull() {
		teleport.setDestination(null);
	}

}
