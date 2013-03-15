package handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import game.Game;

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

	
	@Test 
	public void hasMoveTest(){	
		Game game = new Game(10,10);
		EndTurnHandler eh = new EndTurnHandler(game, null);
		assertFalse(eh.hasMoved());
		MoveHandler mh = new MoveHandler(game, null);
		
		
		Direction direction = getValidMoveDirection(game);
		
		//Move in a valid direction.
		mh.move(direction);
		
		assertTrue(eh.hasMoved());
		
		
	}


	private Direction getValidMoveDirection(Game game) {
		Direction direction = null;
		Square currentPosition = game.getCurrentPlayer().getPosition();
		Square next = null;
		while(next == null || next.isObstructed()){
			try {
				direction = Direction.getRandomDirection();
				System.out.println("direction");
				next = game.getGrid().getNeighbor(currentPosition, direction);
				System.out.println(next);
			} catch (Exception e) {

			}
			
		}
		return direction;
	}
	
	
	/**
	 * Tests the basic case of endTurn
	 */
	@Test
	public void EndTurnTest(){
		
	}
	
	@Test
	public void EndTurnTestPowerFailure(){
		
	}
	@Test
	public void EndActionTest(){
		
	}
}
