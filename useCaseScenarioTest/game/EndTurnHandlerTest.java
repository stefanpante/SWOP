package game;

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
 * Scenario test for the use case "End Turn"
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
	
	@Test
	public void endTurn() {
		Player player = game.getCurrentPlayer();
		Player nextPlayer = game.getNextPlayer();
		
		assertFalse(endTurnHandler.hasMoved());
		
		MoveHandler moveHandler = new MoveHandler(game, null);
		Direction direction = getValidMoveDirection(game);
		
		//Move in a valid direction.
		moveHandler.move(direction);		
		
		assertTrue(endTurnHandler.checkToProceed());
		assertTrue(endTurnHandler.hasMoved());
		
		assertEquals(player, game.getCurrentPlayer());
		
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
		
		assertEquals(nextPlayer, game.getCurrentPlayer());
	}
	
	/**
	 * Tests what happens if the player ends a turn without moving.
	 * Should throw an exception to notify loss of player.
	 */
	@Test(expected=IllegalStateException.class)
	public void endTurnWithoutMoving(){
		Player player = game.getCurrentPlayer();
		assertTrue(endTurnHandler.checkToProceed());
		
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
	}

}
