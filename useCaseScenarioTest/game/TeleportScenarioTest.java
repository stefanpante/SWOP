package game;

import static org.junit.Assert.*;

import item.Item;
import item.Teleport;
import item.launchable.IdentityDisc;


import org.junit.Before;
import org.junit.Test;

import game.Game;
import grid.Grid;
import grid.GridProvider;
import controller.EndTurnHandler;
import controller.MoveHandler;
import controller.PickUpHandler;
import controller.ThrowLaunchableHandler;
import controller.TurnHandler;
import controller.UseItemHandler;
import square.Direction;
import square.Square;
import square.obstacle.LightTrail;
import util.Coordinate;

/**
 * 
 * Scenario tests involving a teleport.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class TeleportScenarioTest {

	private Game game;

	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private ThrowLaunchableHandler throwLaunchableHandler;
	private EndTurnHandler endTurnHandler;
	private TurnHandler turnHandler;
	
	private Square squareOne, squareTwo;
	
	private Teleport teleport, teleportDestination;
	
	/**
	 * Move to a square where an identity disc is situated an try to pick it up
	 */
	
	@Before
	public void setUpGame(){
		Grid grid = GridProvider.getEmptyGrid();
		game = new Game(grid);

		moveHandler = new MoveHandler(game, null);
		pickUpHandler = new PickUpHandler(game,null);
		useItemHandler = new UseItemHandler(game, null);
		throwLaunchableHandler = new ThrowLaunchableHandler(game, null);
		endTurnHandler = new EndTurnHandler(game, null);
		turnHandler = new TurnHandler(game,null);
		
		squareOne = game.getGrid().getSquare(new Coordinate(0,8));
		squareTwo = game.getGrid().getSquare(new Coordinate(3,4));
		
		teleport = new Teleport();
		teleportDestination = new Teleport(teleport);
		
		teleport.setDestination(teleportDestination);
		
		squareOne.getInventory().addItem(teleport);
		squareTwo.getInventory().addItem(teleportDestination);
	}
	/**
	 * Move a player onto a teleporter. Assert that the player is moved to the other square that is part
	 * of the teleporter
	 */
	@Test
	public void basicTeleporterTest(){
		// clear all powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
		
		moveHandler.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(), squareTwo);
		
	}
	
	/**
	 *Move a player onto a teleporter. Assert that he cannot pick up an item from the source teleporter.
	 *
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterFirstSquare(){
		// clear all powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
		
		Item item = new IdentityDisc();
		squareOne.getInventory().addItem(item);
		
		moveHandler.move(Direction.NORTH);
		// should throw an exception
		pickUpHandler.pickUp(item);
		
	}
	

	/**
	 * Move a player onto a teleporter. Assert that he can pick up an item fromt the destination square.
	 */
	@Test
	public void pickupTeleporterSecSquare() {
		// clear all powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
		
		Item item = new IdentityDisc();
		squareTwo.getInventory().addItem(item);
		
		moveHandler.move(Direction.NORTH);
		pickUpHandler.pickUp(item);
		
		assertTrue(game.getCurrentPlayer().getInventory().hasItem(item));
		assertFalse(squareTwo.getInventory().hasItem(item));
	}
	
	/** 
	 * Move a player onto a teleporter. Another player is situated on the other teleporter.
	 * Assert that he is not transported.
	 */
	@Test(expected = IllegalStateException.class)
	public void testOtherPlayerDestination(){
		// clear all powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
		
		game.getNextPlayer().move(squareTwo);
		moveHandler.move(Direction.NORTH);
	}
	
	/**
	 * Teleport a player through a teleporter. Assert that his lighttrail is split.
	 */
	
	@Test
	public void testSplitLightTrail(){
		// clear all powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
				
		moveHandler.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(),squareTwo);
		moveHandler.move(Direction.NORTH);
		LightTrail lt = game.getLightTrail(game.getCurrentPlayer());
		assertTrue(lt.contains(squareOne));
		assertTrue(lt.contains(game.getCurrentPlayer().getStartPosition()));
		assertTrue(lt.contains(squareOne));
		
	}

}
