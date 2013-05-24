package game;

import controller.EndTurnHandler;
import controller.MoveHandler;
import effect.imp.PowerFailureEffect;
import game.mode.RaceGameMode;
import org.junit.Before;
import org.junit.Test;
import square.GridElement;
import square.Square;
import util.Direction;

import static org.junit.Assert.*;

/**
 * Scenario test for the use case "End Turn"
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class EndTurnHandlerTest {
	
	private Game game;
	private EndTurnHandler endTurnHandler;
	
	@Before
	public void setUpBefore(){
		game = new Game(new RaceGameMode(10,10),2);
		endTurnHandler = new EndTurnHandler(game, null);
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
	public void hasMoveTest() throws Exception{	
		
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
		GridElement currentPosition = game.getCurrentPlayer().getPosition();
		GridElement next = null;
		
		while(next == null || next.isObstacle()){
			try {
				direction = Direction.getRandomDirection();
				next = currentPosition.getNeighbor(direction);
			} catch (Exception e) {

			}
			
		}
		
		return direction;
	}
	
	@Test
	public void endTurn() throws Exception {
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
		
		assertFalse(player == game.getCurrentPlayer());
	}
	
	/**
	 * Tests what happens if the player ends a turn without moving.
	 * Should throw an exception to notify loss of player.
	 */
	// cannot expect a general exception
	@Test
	public void endTurnWithoutMoving() throws Exception {
		Player player = game.getCurrentPlayer();
		assertTrue(endTurnHandler.checkToProceed());
		
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
	}
	
	/**
	 * We move the player onto a new square which is different than the starting position.
	 * We then end it's turn. We do the same for the second player.
	 * 
	 * When we switch back to the first player, we see if the power failed square
	 * results in 1 less action.
	 */
	@Test
	public void startOnPowerFailure() throws Exception {
		Player player = game.getCurrentPlayer();
		Player otherPlayer = game.getNextPlayer();
		
		// 1) First Player: move, let position power fail and end Turn.
		
		Direction direction = getValidMoveDirection(game);
		Square square = (Square) player.getPosition().getNeighbor(direction);
		
		MoveHandler moveHandler = new MoveHandler(game, null);
		moveHandler.move(direction);
		
		square.addSquareEffect(new PowerFailureEffect());
		
		endTurnHandler.endTurn();
		endTurnHandler.confirm(true);
		endTurnHandler.endTurn();
		
		// 2) Move the second player and  then endTurn.
		assertEquals(game.getCurrentPlayer(), otherPlayer);
		
		direction = getValidMoveDirection(game);
		square = (Square) otherPlayer.getPosition().getNeighbor(direction);
		
		moveHandler.move(direction);
		
		// Sometimes player has already been switched because of move to PowerFailure.
		if(game.getCurrentPlayer().equals(otherPlayer)) {
			endTurnHandler.confirm(true);
			endTurnHandler.endTurn();
		}
		
		
		assertEquals(game.getCurrentPlayer(), player);
		assertEquals(Player.MAX_ALLOWED_ACTIONS - 1, player.getRemainingActions());
	}

}
