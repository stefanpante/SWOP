package game;

import static org.junit.Assert.*;

import item.IdentityDisc;
import item.Item;
import item.Teleport;


import org.junit.Before;
import org.junit.Test;

import grid.Grid;
import grid.GridProvider;
import controller.EndTurnHandler;
import controller.MoveHandler;
import controller.PickUpHandler;
import controller.ThrowIdentityDiskHandler;
import controller.TurnHandler;
import controller.UseItemHandler;
import util.Direction;
import square.Square;
import square.multi.LightTrail;
import util.Coordinate;

/**
 * 
 * Scenario tests involving a teleport.
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public class TeleportScenarioTest {

	private Game game;

	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private ThrowIdentityDiskHandler throwLaunchableHandler;
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
		throwLaunchableHandler = new ThrowIdentityDiskHandler(game, null);
		endTurnHandler = new EndTurnHandler(game, null);
		turnHandler = new TurnHandler(game,null);
		
		squareOne = game.getGrid().getGridElement(new Coordinate(0,8));
		squareTwo = game.getGrid().getGridElement(new Coordinate(3,4));
		
		teleport = new Teleport();
		teleportDestination = new Teleport();
		
		squareOne.getInventory().addItem(teleport);
		squareTwo.getInventory().addItem(teleportDestination);
		
		teleport.setDestination(squareTwo);
		teleportDestination.setDestination(squareOne);
		
		// Place players
		Square nextToTeleport = game.getGrid().getGridElement(new Coordinate(0,7));
		game.getCurrentPlayer().setPosition(nextToTeleport);
		
	}
	/**
	 * Move a player onto a teleporter. Assert that the player is moved to the other square that is part
	 * of the teleporter
	 */
	@Test
	public void basicTeleporterTest() throws Exception {
		// clear all powerfailures and obstacles
		game.getPowerGayManager().clearPowerFailures();
		
		assertFalse(teleport.getDestination().isObstructed());
		moveHandler.move(Direction.SOUTH);
		
		Coordinate coord = game.getGrid().getCoordinate(game.getCurrentPlayer().getPosition());
		System.out.println(coord);
		
		assertEquals(game.getCurrentPlayer().getPosition(), squareTwo);
	}
	
	/**
	 *Move a player onto a teleporter. Assert that he cannot pick up an item from the source teleporter.
	 *
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterFirstSquare() throws Exception {
		// clear all powerfailures and obstacles
		game.getPowerGayManager().clearPowerFailures();
		
		Item item = new IdentityDisc();
		squareOne.getInventory().addItem(item);
		
		moveHandler.move(Direction.SOUTH);
		// should throw an exception
		pickUpHandler.pickUp(item);
	}

	/**
	 * Move a player onto a teleporter. Assert that he can pick up an item fromt the destination square.
	 */
	@Test
	public void pickupTeleporterSecSquare() throws Exception{
		// clear all powerfailures and obstacles
		game.getPowerGayManager().clearPowerFailures();
		
		Item item = new IdentityDisc();
		squareTwo.getInventory().addItem(item);
		
		moveHandler.move(Direction.SOUTH);
		pickUpHandler.pickUp(item);
		
		assertTrue(game.getCurrentPlayer().getInventory().hasItem(item));
		assertFalse(squareTwo.getInventory().hasItem(item));
	}
	
	/** 
	 * Move a player onto a teleporter. Another player is situated on the other teleporter.
	 * Assert that he is not transported.
	 */
	@Test(expected = Exception.class)
	public void testOtherPlayerDestination() throws Exception {
		// clear all powerfailures and obstacles
		game.getPowerGayManager().clearPowerFailures();
		
		game.getNextPlayer().setPosition(squareTwo);
		moveHandler.move(Direction.SOUTH);
	}
	
	/**
	 * Teleport a player through a teleporter. Assert that his lighttrail is split.
	 */
	@Test
	public void testSplitLightTrail() throws Exception{
		// clear all powerfailures and obstacles
		game.getPowerGayManager().clearPowerFailures();
				
		moveHandler.move(Direction.SOUTH);
		assertEquals(game.getCurrentPlayer().getPosition(),squareTwo);
		moveHandler.move(Direction.SOUTH);
		LightTrail lt = game.getCurrentPlayer().getLightTrail();
		
		assertTrue(lt.contains(squareOne));
		assertTrue(lt.contains(squareTwo));
	}

}
