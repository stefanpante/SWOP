package game;

import static org.junit.Assert.*;

import controller.PickUpHandler;
import controller.TurnHandler;
import item.LightGrenade;
import item.inventory.PlayerInventory;
import game.Game;
import grid.RandomGridBuilder;

import org.junit.Before;
import org.junit.Test;

import command.AbstractGameCommand;


import square.Square;

/**
 * Scenario test for the "pick up" use case.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 * 
 */
public class PickUpHandlertTest {
	
	/**
	 * The TurnHandler is needed as an observer to the other handlers.
	 * It can be notified on certain actions when for example a turn needs to end.
	 */
	private TurnHandler turnHandler;
	
	private PickUpHandler pickUpHandler;
	
	private Game game;
	
	@Before
	public void setUpBefore() {
		RandomGridBuilder gridBuilder = new RandomGridBuilder(10, 10);
		this.game = new Game(gridBuilder.getGrid());
		this.turnHandler = new TurnHandler(game, null);
		this.pickUpHandler = new PickUpHandler(game,null);
	}
	
	/**
	 * Test if the square does not contain item
	 */
	@Test(expected=IllegalStateException.class)
	public void EmptyPickUpTest() throws Exception {
		assertTrue(game.getCurrentPlayer().getPosition().getInventory().isEmpty());
		
		pickUpHandler.pickUp(new LightGrenade());
	}
	
	/**
	 * Test the pick up when there is an item on the square 
	 * and the player's inventory is not full
	 */
	@Test
	public void pickUpTest() throws Exception{
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		
		currentPosition.getInventory().addItem(lg);
		assertFalse(currentPosition.getInventory().isEmpty());
		
		pickUpHandler.pickUp(lg);
	}
	
	/**
	 * Test the pick up when there is an item on the square,
	 * but the inventory of the player is full.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void pickUpFullInventoryTest() throws Exception{
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();
		currentPosition.getInventory().addItem(lg);
		assertFalse(currentPosition.getInventory().isEmpty());
		
		PlayerInventory inventory = game.getCurrentPlayer().getInventory();
		LightGrenade item = new LightGrenade();
		
		while(inventory.canHaveAsItem(item)) {
			inventory.addItem(item);
			item = new LightGrenade();
		}
		
		LightGrenade lg2 = new LightGrenade();
		
		pickUpHandler.pickUp(lg2);	
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
