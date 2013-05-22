package square;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestPower {

	/**
	 * Upon constructing a square, the state must be a regular state.
	 */
	@Test
	public void testConstructor() {
		Square square = new Square();
		assertFalse(square.getPower().isFailing());
	}
	
	/**
	 * After a square receives a powerfailure state.
	 * The state must become regular after 3 turns.
	 */
	@Test(expected=IllegalStateException.class)
	public void testPowerFailure() {
		Square square = new Square();
		square.setPower(new PrimaryPowerFail());
		assertTrue(square.getPower().isFailing());
		square.getPower().decreaseTurn();
		
		assertTrue(square.getPower().isFailing());
		square.getPower().decreaseTurn();
		
		assertTrue(square.getPower().isFailing());
		square.getPower().decreaseTurn();
		
		assertFalse(square.getPower().isFailing());
	}

}
