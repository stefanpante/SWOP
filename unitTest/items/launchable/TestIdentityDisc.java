package items.launchable;

import static org.junit.Assert.*;
import item.Item;
import item.launchable.IdentityDisc;
import item.launchable.LaunchableItem;

import org.junit.Before;
import org.junit.Test;

import square.Direction;

/**
 * Unit test case for Identity Disc
 * 
 * @author Vincent R.
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
	public void isValidDistanceTraveled() {
		assertTrue(identityDisc.isValidDistanceTraveled(0));
		
		assertTrue(identityDisc.isValidDistanceTraveled(identityDisc.getRange() - 1));
		assertTrue(identityDisc.isValidDistanceTraveled(identityDisc.getRange()));
		assertFalse(identityDisc.isValidDistanceTraveled(identityDisc.getRange() + 1));
	}
	
	@Test
	public void isValidTravelDirection() {
		assertTrue(IdentityDisc.isValidTravelDirection(Direction.NORTH));
		assertFalse(IdentityDisc.isValidTravelDirection(Direction.SOUTHEAST));
	}

}
