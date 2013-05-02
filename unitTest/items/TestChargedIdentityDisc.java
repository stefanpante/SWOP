package items;

import static org.junit.Assert.*;
import item.ChargedIdentityDisc;

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
	public void getRange() {
		assertEquals(chargedDisc.getRange(), ChargedIdentityDisc.MAX_TRAVEL_DISTANCE);
	}

}
