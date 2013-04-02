package scenariotests;

import static org.junit.Assert.*;
import handler.PickUpHandler;
import item.LightGrenade;
import item.inventory.PlayerInventory;
import game.Game;

import org.junit.Test;

import square.Square;

/**
 * Scenario test for the "pick up" use case.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 *
 *TODO: refactor to use static numberofactions in turn
 */
public class PickUpHandlertTest {
	
	/**
	 * Test if the square does not contain item
	 */
	@Test(expected = IllegalStateException.class)
	public void EmptyPickUpTest(){
		Game game = new Game(10,10);
		PickUpHandler ph = new PickUpHandler(game,null);
		assertTrue(game.getCurrentPlayer().getPosition().getInventory().isEmpty());
		ph.pickUp(new LightGrenade());
		
	}
	
	/**
	 * Test the pick up when there is an item on the square 
	 * and the player's inventory is not full
	 */
	@Test
	public void pickUpTest(){
		Game game = new Game(10,10);
		PickUpHandler ph = new PickUpHandler(game,null);
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		currentPosition.getInventory().addItem(lg);
		assertFalse(currentPosition.getInventory().isEmpty());
		ph.pickUp(lg);
	}
	
	/**
	 * Test the pick up when there is an item on the square,
	 * but the inventory of the player is full.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void pickUpFullInventoryTest(){
		Game game = new Game(10,10);
		PickUpHandler ph = new PickUpHandler(game,null);
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		currentPosition.getInventory().addItem(lg);
		assertFalse(currentPosition.getInventory().isEmpty());
		
		PlayerInventory inventory = game.getCurrentPlayer().getInventory();
		LightGrenade item = new LightGrenade();
		while(inventory.canHaveAsItem(item)){
			inventory.addItem(item);
			item = new LightGrenade();
		}
		LightGrenade lg2 = new LightGrenade();
		ph.pickUp(lg2);
		
		
		
	}
	/**
	 * 
	 */
	@Test
	public void checkToProceedTest(){
		
	}
	
	@Test
	public void endActionTest(){
		
	}
}
