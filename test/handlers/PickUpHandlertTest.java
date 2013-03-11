package handlers;

import static org.junit.Assert.*;
import items.LightGrenade;
import items.PlayerInventory;
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
		PickUpHandler ph = new PickUpHandler(game);
		assertTrue(game.getCurrentPlayer().getPosition().getPickUpInventory().isEmpty());
		ph.pickUp(new LightGrenade());
		
	}
	
	/**
	 * Test the pick up when there is an item on the square 
	 * and the player's inventory is not full
	 */
	@Test(expected = IllegalStateException.class)
	public void pickUpTest(){
		Game game = new Game(10,10);
		PickUpHandler ph = new PickUpHandler(game);
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		currentPosition.getPickUpInventory().addItem(lg);
		assertFalse(currentPosition.getPickUpInventory().isEmpty());
		ph.pickUp(lg);
	}
	
	/**
	 * Test the pick up when there is an item on the square,
	 * but the inventory of the player is full.
	 */
	@Test(expected = IllegalStateException.class)
	public void pickUpFullInventoryTest(){
		Game game = new Game(10,10);
		PickUpHandler ph = new PickUpHandler(game);
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		currentPosition.getPickUpInventory().addItem(lg);
		assertFalse(currentPosition.getPickUpInventory().isEmpty());
		
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
