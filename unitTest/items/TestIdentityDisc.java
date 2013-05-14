package items;

import static org.junit.Assert.*;

import item.IdentityDisc;

import org.junit.Before;
import org.junit.Test;

import square.Direction;

/**
 * Unit test case for Identity Disc
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class TestIdentityDisc {
	
	private IdentityDisc identityDisc;

	@Before
	public void setUpBefore() {
		identityDisc = new IdentityDisc();
	}
	
	@Test
	public void getRange() {
		assertEquals(identityDisc.getRange(), IdentityDisc.MAX_TRAVEL_DISTANCE);
	}
	
	@Test
	public void isValidTravelDirection() {
		assertTrue(IdentityDisc.isValidTravelDirection(Direction.NORTH));
		assertTrue(IdentityDisc.isValidTravelDirection(Direction.SOUTH));
		assertTrue(IdentityDisc.isValidTravelDirection(Direction.EAST));
		assertFalse(IdentityDisc.isValidTravelDirection(Direction.SOUTHWEST));
		assertFalse(IdentityDisc.isValidTravelDirection(Direction.NORTHEAST));
		assertFalse(IdentityDisc.isValidTravelDirection(Direction.SOUTHEAST));
	} 
}
