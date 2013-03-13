package handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Game;

import org.junit.Test;

import player.Player;

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
		MoveHandler mh = new MoveHandler(game,null);
		assertTrue(mh.checkToProceed());
		
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS; i++)
			game.getCurrentPlayer().decrementActions();
		
		assertFalse(mh.checkToProceed());
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
	
	@Test 
	public void hasMoveTest(){
		
	}
	

}
