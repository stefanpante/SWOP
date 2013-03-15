package scenariotests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.Game;
import handlers.EndTurnHandler;
import handlers.MoveHandler;

import org.junit.Test;

import player.Player;
import square.Direction;
import square.Square;
import square.state.PowerFailureState;

/**
 * 
 * Scenario test for the use case "End Turn"
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandlerTest {
	
	
	/**
	 * Tests the check to proceed
	 */
	@Test
	public void checkToProceedTest(){
		Game game = new Game(10,10);
		EndTurnHandler eh = new EndTurnHandler(game, null);
		assertTrue(eh.checkToProceed());
		
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS; i++)
			game.getCurrentPlayer().decrementActions();
		
		assertFalse(eh.checkToProceed());
	}

	/**
	 * tests if the player has moved.
	 */
	@Test 
	public void hasMoveTest(){	
		Game game = new Game(10,10);
		game.clearPowerFailures();
		EndTurnHandler eh = new EndTurnHandler(game, null);
		assertFalse(eh.hasMoved());
		MoveHandler mh = new MoveHandler(game, null);
		
		
		Direction direction = getValidMoveDirection(game);
		
		//Move in a valid direction.
		mh.move(direction);		
		assertTrue(eh.hasMoved());		
	}


	/**
	 * Returns a valid move direction
	 * @param game
	 * @return
	 */
	private Direction getValidMoveDirection(Game game) {
		Direction direction = null;
		Square currentPosition = game.getCurrentPlayer().getPosition();
		Square next = null;
		while(next == null || next.isObstructed()){
			try {
				direction = Direction.getRandomDirection();
				next = game.getGrid().getNeighbor(currentPosition, direction);
			} catch (Exception e) {

			}
			
		}
		return direction;
	}
	
	/**
	 * Tests what happens if the player ends a turn on a powerfailure
	 */
	@Test
	public void EndTurnTestPowerFailure(){
		Game game = new Game(10,10);
		Player player = game.getCurrentPlayer();
		EndTurnHandler eh = new EndTurnHandler(game, null);
		assertTrue(eh.checkToProceed());
		game.getCurrentPlayer().getPosition().powerFail();
		eh.confirm(true);
		assertEquals(2,player.getRemainingActions());
	}

}
