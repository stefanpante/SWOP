package game;

import static org.junit.Assert.assertEquals;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import game.Game;
import grid.GridProvider;
import controller.EndTurnHandler;
import controller.MoveHandler;
import controller.TurnHandler;

import org.junit.Before;
import org.junit.Test;

import game.Player;
import square.Direction;
import square.Square;
import square.power.failure.PrimaryPowerFail;

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
		game = new Game(GridProvider.getEmptyGrid());
		
		endTurnHandler = new EndTurnHandler(game, null);
		turnHandler = new TurnHandler(game, null);
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
		game.getPowerManager().clearPowerFailures();
		
		assertFalse(endTurnHandler.hasMoved());
		
		MoveHandler moveHandler = new MoveHandler(game, null);
		Direction direction = getValidMoveDirection(game);
		
		//Move in a valid direction.
		try{
			moveHandler.move(direction);		
			assertTrue(endTurnHandler.hasMoved());	
		}
		catch(Exception e){
			fail("Shouldn't have thrown an exception");
		}
			
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
		try{
		moveHandler.move(direction);		
		
		assertTrue(endTurnHandler.checkToProceed());
		assertTrue(endTurnHandler.hasMoved());
		
		assertEquals(player, game.getCurrentPlayer());
		
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
		
		assertEquals(nextPlayer, game.getCurrentPlayer());
		}
		catch(Exception e){
			fail("MoveHandler shouldn't throw an exception");
		}
	}
	
	/**
	 * Tests what happens if the player ends a turn without moving.
	 * Should throw an exception to notify loss of player.
	 */
	// cannot expect a general exception
	public void endTurnWithoutMoving(){
		Player player = game.getCurrentPlayer();
		assertTrue(endTurnHandler.checkToProceed());
		
		endTurnHandler.confirm(true);
		try {
			endTurnHandler.endTurn();
			fail("Should throw an exception");
		} catch (Exception e) {	}
	}
	
	/**
	 * We move the player onto a new square which is different than the starting position.
	 * We then end it's turn. We do the same for the second player.
	 * 
	 * When we switch back to the first player, we see if the power failed square
	 * results in 1 less action.
	 */
	@Test
	public void startOnPowerFailure() {
		Player player = game.getCurrentPlayer();
		Player otherPlayer = game.getNextPlayer();
		
		// 1) First Player: move, let position power fail and end Turn.
		
		Direction direction = getValidMoveDirection(game);
		Square square = game.getGrid().getNeighbor(player.getPosition(), direction);
		
		MoveHandler moveHandler = new MoveHandler(game, null);
		try {
			moveHandler.move(direction);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldn't throw an exception");
		}
		
		square.setPower(new PrimaryPowerFail());
		assertTrue(square.getPower().isFailing());
		
		endTurnHandler.confirm(true);
		
		try {
			endTurnHandler.endTurn();
		} catch (Exception e) {
			e.printStackTrace();
			fail("EndTurnHandler shouldn't throw an exception");
		}
		
		// 2) Move the second player and  then endTurn.
		assertEquals(game.getCurrentPlayer(), otherPlayer);
		
		direction = getValidMoveDirection(game);
		square = game.getGrid().getNeighbor(otherPlayer.getPosition(), direction);
		
		try {
			moveHandler.move(direction);
		} catch (Exception e1) {
			fail("MoveHandler Shouldn't throw an exception");
			e1.printStackTrace();
		}
		
		// Sometimes player has already been switched because of move to PowerFailure.
		if(game.getCurrentPlayer().equals(otherPlayer)) {
			endTurnHandler.confirm(true);
			try {
				endTurnHandler.endTurn();
			} catch (Exception e) {
				e.printStackTrace();
				fail("EndTurnHandler shouldn't throw an exception");
			}
		}
		
		// Test: Check if the first player is on a power failed square and starts with 1 less action.
		assertTrue(player.getPosition().getPower().isFailing());
		assertTrue(game.getCurrentPlayer().getPosition().getPower().isFailing());
		
		assertEquals(game.getCurrentPlayer(), player);
		assertEquals(player.getRemainingActions(), Player.MAX_ALLOWED_ACTIONS - 1);
	}

}
