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
	
	/**
	 * Test the placement of a grenade on a square when there is
	 * no other grenade placed on the square or used on the square
	 */
	@Test
	public void testPlaceGrenade(){
		
	}
	/**
	 * Test the placement of a grenade when there is already an active
	 * lightgrenade on the square
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade2(){
		
	}
	
	/**
	 * Test the placement of a grenade when there is an already used
	 * grenade on the Square.
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade3(){
		
	}

}
