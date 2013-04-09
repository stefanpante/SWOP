package scenariotests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import event.AbstractGameEvent;
import game.Game;
import controller.EndTurnHandler;
import controller.MoveHandler;
import controller.TurnHandler;

import org.junit.Before;
import org.junit.Test;

import player.Player;
import square.Direction;
import square.Square;

/**
 * 
 * Scenario test for the use case "End Turn"
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandlerTest {
	
	private Game game;
	
	private TurnHandler turnHandler;
	
	private EndTurnHandler endTurnHandler;
	
	@Before
	public void setUpBefore() {
		game = new Game(10,10);
		
		endTurnHandler = new EndTurnHandler(game, null);
		turnHandler = new TurnHandler(game, null);
		
		AbstractGameEvent.setObserver(turnHandler);
	}
	
	/**
	 * Tests the check to proceed
	 */
	@Test
	public void checkToProceedTest(){
		assertTrue(endTurnHandler.checkToProceed());
		
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS; i++)
			game.getCurrentPlayer().decrementActions();
		
		assertFalse(endTurnHandler.checkToProceed());
	}

	/**
	 * tests if the player has moved.
	 */
	@Test 
	public void hasMoveTest(){	
		game.clearPowerFailures();
		
		assertFalse(endTurnHandler.hasMoved());
		
		MoveHandler moveHandler = new MoveHandler(game, null);
		Direction direction = getValidMoveDirection(game);
		
		//Move in a valid direction.
		moveHandler.move(direction);		
		assertTrue(endTurnHandler.hasMoved());		
	}


	/**
	 * Returns a valid move direction
	 * 
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
		Player player = game.getCurrentPlayer();
		assertTrue(endTurnHandler.checkToProceed());
		
		game.getCurrentPlayer().getPosition().getPower().fail();
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
		
		assertEquals(2,player.getRemainingActions());
	}

}
