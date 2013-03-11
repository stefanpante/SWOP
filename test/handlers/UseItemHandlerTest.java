package handlers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import items.LightGrenade;
import game.Game;

import org.junit.Test;

import player.Player;
import square.Direction;

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
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game);
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
		
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		uh.useItem(lg);
		
		// Item shouldn't be in the inventory of the player anymore
		assertFalse(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// Item should be in the inventory of the square
		assertTrue(game.getCurrentPlayer().getPosition().getInventory().getAllItems().contains(lg));
		// The state of the lightGrenade should still be inactive when placed until moved
		assertFalse(lg.isActive());
		
		
		// Item should become active when moved
		MoveHandler mh = new MoveHandler(game);
		Direction[] directions = Direction.values();
		Random random = new Random();
		while(!game.getCurrentPlayer().hasMoved()){
			try{
				mh.move(directions[random.nextInt(directions.length)]);
			}
			catch(Exception e){}
		}
		
		assertTrue(lg.isActive());
		
	}
	/**
	 * Test the placement of a grenade when there is already an active
	 * lightgrenade on the square
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade2(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game);
	}
	
	/**
	 * Test the placement of a grenade when there is an already used
	 * grenade on the Square.
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade3(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game);
	}

}
