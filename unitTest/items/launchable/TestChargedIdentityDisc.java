package items.launchable;

import static org.junit.Assert.*;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;
import item.launchable.LaunchableItem;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test case for Charged Identity Disc
 * 
 * @author Vincent R.
 */
public class TestChargedIdentityDisc {

	private ChargedIdentityDisc chargedDisc;

	@Before
	public void setUpBefore() {
		chargedDisc = new ChargedIdentityDisc();
	}
	
	@Test
	public void isValidDistanceTraveled() {
		assertFalse(chargedDisc.isValidDistanceTraveled(-1));
		assertTrue(chargedDisc.isValidDistanceTraveled(0));
		
		assertTrue(chargedDisc.isValidDistanceTraveled(chargedDisc.getRange() - 1));
		assertTrue(chargedDisc.isValidDistanceTraveled(chargedDisc.getRange()));
		assertFalse(chargedDisc.isValidDistanceTraveled(chargedDisc.getRange() + 1));
	}
	
	@Test
	public void getRange() {
		assertEquals(chargedDisc.getRange(), ChargedIdentityDisc.MAX_TRAVEL_DISTANCE);
	}

}
