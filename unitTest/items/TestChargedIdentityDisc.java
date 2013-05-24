package items;

import item.ChargedIdentityDisc;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test case for Charged Identity Disc
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
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
