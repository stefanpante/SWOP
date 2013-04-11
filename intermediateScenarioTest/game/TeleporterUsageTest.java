package game;

import static org.junit.Assert.*;
import item.LightGrenade;
import item.Teleport;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Game;
import grid.GridProvider;
import controller.MoveHandler;
import controller.PickUpHandler;
import controller.TurnHandler;
import event.AbstractGameEvent;
import square.Direction;
import square.Square;
import square.obstacle.LightTrail;
import util.Coordinate;

/**
 * Tests the usage of Teleport with Move and PickUp.
 * 
 * @author vincent
 */
public class TeleporterUsageTest {
	
	private Game game;
	
	private MoveHandler moveHandler;
	
	private PickUpHandler pickUpHandler;
	
	private Square squareOne, squareTwo;
	
	private Teleport teleport, teleportDestination;
	
	@Before
	public void setUpBefore() {
		game = new Game(GridProvider.getEmptyGrid());
		
		moveHandler =  new MoveHandler(game, null);
		pickUpHandler = new PickUpHandler(game, null);
		
		AbstractGameEvent.setObserver(new TurnHandler(game, null));
		
		squareOne = game.getGrid().getSquare(new Coordinate(0,8));
		squareTwo = game.getGrid().getSquare(new Coordinate(3,4));
		
		teleport = new Teleport();
		teleportDestination = new Teleport(teleport);
		
		teleport.setDestination(teleportDestination);
		
		squareOne.getInventory().addItem(teleport);
		squareTwo.getInventory().addItem(teleportDestination);
	}

	/**
	 * Move a player onto a teleport. Assert that the player is moved to the other square that is part
	 * of the teleport
	 */
	@Test
	public void basicTeleporterTest(){
		moveHandler.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(), squareTwo);
		
	}
	
	/**
	 *Move a player onto a teleport. Assert that he cannot pick up an item from the source teleport.
	 *
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterFirstSquare(){		
		LightGrenade item = new LightGrenade();
		squareOne.getInventory().addItem(item);
		
		moveHandler.move(Direction.NORTH);
		// should throw an exception
		pickUpHandler.pickUp(item);		
	}
	

	/**
	 * Move a player onto a Teleport. Assert that he can pick up an item from the destination square.
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterSecSquare(){
		LightGrenade item = new LightGrenade();
		squareTwo.getInventory().addItem(item);
		
		moveHandler.move(Direction.NORTH);
		pickUpHandler.pickUp(item);
		
		assertTrue(game.getCurrentPlayer().getInventory().hasItem(item));
		assertFalse(squareTwo.getInventory().hasItem(item));
	}
	
	/** 
	 * Move a player onto a Teleport. Another player is situated on the other Teleport.
	 * Assert that he is not transported.
	 */
	@Test
	public void testOtherPlayerDestination(){		
		game.getNextPlayer().move(squareTwo);
		moveHandler.move(Direction.NORTH);
		
		assertEquals(game.getCurrentPlayer().getPosition(),squareOne);
		assertEquals(game.getNextPlayer().getPosition(), squareTwo);
	}
	
	/**
	 * Teleport a player through a Teleport. Assert that his LightTrail is split.
	 */
	
	@Test
	public void testSplitLightTrail(){
		moveHandler.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(),squareTwo);
		
		moveHandler.move(Direction.NORTH);
		LightTrail lt = game.getLightTrail(game.getCurrentPlayer());
		assertTrue(lt.contains(squareOne));
		assertTrue(lt.contains(game.getCurrentPlayer().getStartPosition()));
		assertTrue(lt.contains(squareTwo));
		
	}

}
