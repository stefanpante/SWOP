package handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import game.Game;

import org.junit.Test;

import player.Player;

/**
 * 
 * Scenario test for the use case "use Item"
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 * 
 *
 */
public class UseItemHandlerTest {

	/**
	 * The player should have more than action left 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCheckToProceed(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game);
		assertTrue(uh.checkToProceed());
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS - 1; i++)
			game.getCurrentPlayer().incrementActions();
		assertFalse(uh.checkToProceed());
	}

}
