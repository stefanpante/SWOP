package game;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for all the interactions with a forcefield.
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class ForceFieldScenarioTest {

	public ForceFieldScenarioTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setup(){
		
	}
	
	/**
	 * Tests whether the force field is turned on after the player performs two actions after placing it.
	 * and test wheter is turns off after an additional two actions.
	 */
	@Test
	public void testFFOnOf(){
		
	}
	/**
	 * Assert that a forcefield can only be placed when there are two forcefield generators within 3 squares.
	 */
	@Test(expected = IllegalStateException.class)
	public void testForceFieldPlacement(){
		
	}
	
	/**
	 * Test the forcefield in all the directions
	 */
	@Test
	public void testForceFieldDirections(){
		
	}
	
	/**
	 * Test that the identityDisc is destroyed when it hits a forcefield
	 */
	@Test
	public void testIdentityDiscDestroyed(){
		
	}
	
	/**
	 * Test that a player loses the game when he is trapped by a forcefield for the second time.
	 */
	@Test
	public void testPlayerLoss(){
		
	}

}
