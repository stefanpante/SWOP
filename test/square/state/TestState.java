package square.state;

import static org.junit.Assert.*;

import org.junit.Test;

import square.Square;

public class TestState {

	/**
	 * Upon constructing a square, the state must be a regular state.
	 */
	@Test
	public void testConstructor() {
		Square square = new Square();
		
		if(!(square.getState() instanceof RegularState))
			fail("Not yet implemented");
	}
	
	/**
	 * After a square receives a powerfailure state.
	 * The state must become regular after 3 turns.
	 */
	@Test
	public void testPowerFailure() {
		Square square = new Square();
		square.powerFail();
		
		assertEquals(PowerFailureState.getInstance(),square.getState());
		square.endTurn();
		
		assertEquals(PowerFailureState.getInstance(),square.getState());
		square.endTurn();
		
		assertEquals(PowerFailureState.getInstance(),square.getState());
		square.endTurn();
		
		assertEquals(RegularState.getInstance(),square.getState());
	}

}
