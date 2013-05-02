package game;

import static org.junit.Assert.*;


import game.Game;
import grid.Grid;
import grid.GridProvider;

import item.Teleport;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.Player;
import square.Direction;
import square.Square;
import square.obstacle.Wall;
import util.Coordinate;

import controller.EndTurnHandler;
import controller.GameHandler;
import controller.MoveHandler;
import controller.PickUpHandler;
import controller.ThrowLaunchableHandler;
import controller.TurnHandler;
import controller.UseItemHandler;

/**
 * Scenario tests involving a launchable.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LaunchableScenarioTest {


	private Game game;

	private MoveHandler moveHandler;
	private PickUpHandler pickUpHandler;
	private UseItemHandler useItemHandler;
	private ThrowLaunchableHandler throwLaunchableHandler;
	private EndTurnHandler endTurnHandler;
	private TurnHandler turnHandler;
	
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
	}
	
	@Test
	public void pickUpIdentityDiscTest(){
		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		Square next = game.getGrid().getNeighbor(currentPlayer.getPosition(), direction);
		IdentityDisc id = new IdentityDisc();
		next.getInventory().addItem(id);

		// Make sure that there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// move into the direction that the Identitydisc is situated
		moveHandler.move(direction);

		// Try to pick up the identity disc, should succeed
		pickUpHandler.pickUp(id);
		assertTrue(currentPlayer.getInventory().hasItem(id));

	}

	@Test
	public void pickUpChargedDisk(){
		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		Square next = game.getGrid().getNeighbor(currentPlayer.getPosition(), direction);
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		next.getInventory().addItem(id);

		// Make sure that there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// move into the direction that the Identitydisc is situated
		moveHandler.move(direction);

		// Try to pick up the identity disc, should succeed
		pickUpHandler.pickUp(id);
		assertTrue(currentPlayer.getInventory().hasItem(id));

	}
	/**
	 * Move to a square without an identity disc and try to pick it up, should cause an exception
	 */
	@Test(expected = IllegalStateException.class)
	public void pickUpIdentityDiscEmpty(){
		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		// Make sure that there are no powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();

		// move into the direction that the Identitydisc is situated
		moveHandler.move(direction);

		// Try to pick up the identity disc, should cause an exception
		pickUpHandler.pickUp(new IdentityDisc());

	}

	@Test(expected = IllegalStateException.class)
	public void pickUpChargedDiskEmpty(){
		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		// Make sure that there are no powerfailures and obstacles
		game.getPowerManager().clearPowerFailures();
		// move into the direction that the Identitydisc is situated
		moveHandler.move(direction);

		// Try to pick up the identity disc, should cause an exception
		pickUpHandler.pickUp(new ChargedIdentityDisc());

	}

	/**
	 * Throw an identitydisc into a direction specified. Assert that is moves 4 squares
	 * (no obstacles or boundaries)
	 */
	@Test
	public void testThrowIdentityDisc(){

		// make sure there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// Throw the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(0,0));
		// All possible throw directions
		Direction[] directions = new Direction[]{Direction.SOUTH, Direction.EAST};
		game.getCurrentPlayer().move(currentPosition);

		// Place the player in the middle of the grid
		for(Direction direction: directions){
			// new identity disc
			IdentityDisc id = new IdentityDisc();
			// Player should have identitydisc in inventory
			game.getCurrentPlayer().getInventory().addItem(id);
			// throw the identitydisc in the given direction
			
			Square position = game.getCurrentPlayer().getPosition();
			throwLaunchableHandler.throwLaunchable(id,direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);

			// assert that the square has the item
			assertTrue(position.getInventory().hasItem(id));

		}
		
		currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throw directions
		directions = new Direction[]{Direction.NORTH, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);
		game.getPowerManager().clearPowerFailures();
		// Place the player in the middle of the grid
		for(Direction direction: directions){
			// new identity disc
			IdentityDisc id = new IdentityDisc();
			// Player should have identitydisc in inventory
			game.getCurrentPlayer().getInventory().addItem(id);
			// throw the identitydisc in the given direction
			
			Square position = game.getCurrentPlayer().getPosition();
			throwLaunchableHandler.throwLaunchable(id,direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);

			// assert that the square has the item
			assertTrue(position.getInventory().hasItem(id));

		}

	}
	
	/**
	 * Try to throw a identitydisc which isn't in the players inventory.
	 */
	@Test(expected = IllegalStateException.class)
	public void throwDiskNotInPlayer(){
		
		throwLaunchableHandler.throwLaunchable(new IdentityDisc(), Direction.NORTH);
	}

	/**
	 * Throw a charged disk, assert that it stops at a boundary
	 */
	@Test
	public void throwChargedDisk(){

		// make sure there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// Throw the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		game.getCurrentPlayer().move(currentPosition);

		// new identity disc
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id);
		// throw the identitydisc in the given direction
		throwLaunchableHandler.throwLaunchable(id,Direction.NORTH);
		assertTrue(game.getGrid().getSquare(new Coordinate(4,0)).getInventory().hasItem(id));

		// new identity disc
		ChargedIdentityDisc id2 = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id2);
		// throw the identitydisc in the given direction
		throwLaunchableHandler.throwLaunchable(id2,Direction.SOUTH);
		assertTrue(game.getGrid().getSquare(new Coordinate(4,9)).getInventory().hasItem(id2));


		// Player is switched after three actions.
		
		
	}
	/**
	 * Other cases
	 */
	@Test
	public void throwChargedDisk2(){
		Square position2 = game.getGrid().getSquare(new Coordinate(4,0));
		game.getCurrentPlayer().move(position2);
		
		// new identity disc
		ChargedIdentityDisc id3 = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		Player player = game.getCurrentPlayer();
		game.getCurrentPlayer().getInventory().addItem(id3);
		// throw the identitydisc in the given direction
		throwLaunchableHandler.throwLaunchable(id3,Direction.WEST);
		assertTrue(game.getGrid().getSquare(new Coordinate(0,0)).getInventory().hasItem(id3));

	}

	/**
	 * Throw an identity disc when there is a wall in the way. assert that it drops in front of the wall
	 */
	@Test
	public void throwDiskWall(){
		
		// make sure there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// Throw the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throw directions
		game.getCurrentPlayer().move(currentPosition);

		Square s1 = game.getGrid().getSquare(new Coordinate(6,4));
		Square s2 = game.getGrid().getSquare(new Coordinate(6,5));
		Wall wall = new Wall(s1,s2);

		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);

		throwLaunchableHandler.throwLaunchable(id, Direction.EAST);
		assertTrue(game.getGrid().getSquare(new Coordinate(5,4)).getInventory().hasItem(id));

	}

	/**
	 * Throw a charged disk at a wall. Assert that it drops in front of the wall
	 */
	@Test
	public void throwChargedDiskWall(){

		ThrowLaunchableHandler ih = new ThrowLaunchableHandler(game, null);	

		// make sure there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// Throw the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throw directions
		game.getCurrentPlayer().move(currentPosition);

		Square s1 = game.getGrid().getSquare(new Coordinate(9,4));
		Square s2 = game.getGrid().getSquare(new Coordinate(9,5));
		Wall wall = new Wall(s1,s2);

		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);

		ih.throwLaunchable(id, Direction.EAST);
		assertTrue(game.getGrid().getSquare(new Coordinate(8,4)).getInventory().hasItem(id));
	}

	/**
	 * Throw an identity disc in the direction of the boundary the grid and test for effects
	 */
	@Test
	public void throwDiskBoundary(){

		// make sure there are no powerfailures
		game.getPowerManager().clearPowerFailures();

		// Throw the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(2,4));
		// All possible throw directions
		Player player = game.getCurrentPlayer();
		
		game.getCurrentPlayer().move(currentPosition);
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		throwLaunchableHandler.throwLaunchable(id, Direction.WEST);
		assertTrue(game.getGrid().getSquare(new Coordinate(0,4)).getInventory().hasItem(id));
		

	}
	/**
	 * Throw an identity disc at another player. Assert that the other player loses a turn.
	 */
	@Test
	public void hitOtherPlayer(){

		ThrowLaunchableHandler ih = new ThrowLaunchableHandler(game, null);

		// Make sure there aren't any obstacles and powerfailures in the way
		game.getPowerManager().clearPowerFailures();

		// Place the players so they can hit each other
		game.getCurrentPlayer().move(game.getGrid().getSquare(new Coordinate(4,4)));
		game.getNextPlayer().move(game.getGrid().getSquare(new Coordinate(4,2)));


		// The inventory of the player should contain the identityDisc
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		// throw the identity disc at the other player
		ih.throwLaunchable(id, Direction.NORTH);

		// Assert the effect of the identityDisc
		assertTrue(game.getNextPlayer().getPosition().getInventory().hasItem(id));
		assertFalse(game.getCurrentPlayer().getInventory().hasItem(id));
		// Player loses his turn
		//assertTrue();


	}

	/**
	 * Throw a charged Disk at another player, assert that he loses a turn.
	 */
	@Test
	public void chargedDiskAtOtherPlayer(){

		// Make sure there aren't any obstacles and powerfailures in the way
		game.getPowerManager().clearPowerFailures();

		// Place the players so they can hit each other
		game.getCurrentPlayer().move(game.getGrid().getSquare(new Coordinate(4,4)));
		game.getNextPlayer().move(game.getGrid().getSquare(new Coordinate(1,4)));


		// The inventory of the player should contain the identityDisc
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		// throw the identity disc at the other player
		throwLaunchableHandler.throwLaunchable(id, Direction.WEST);

		// Assert the effect of the identityDisc
		assertTrue(game.getNextPlayer().getPosition().getInventory().hasItem(id));
		assertFalse(game.getCurrentPlayer().getInventory().hasItem(id));
		
		assertFalse(game.getNextPlayer().hasRemainingActions());
	}

	/**
	 * Throw a Identity disc at a transporter. Assert that it is teleported to the other square connected and moves further
	 * to the teleporter.
	 */
	@Test 
	public void throwDiscTeleporter(){
		
		ThrowLaunchableHandler ih = new ThrowLaunchableHandler(game, null);
		
		// Make sure the grid is clear
		game.getPowerManager().clearPowerFailures();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleport teleport = new Teleport();
		Teleport teleport2 = new Teleport();
		s1.getInventory().addItem(teleport);
		s2.getInventory().addItem(teleport2);
		teleport.setDestination(teleport2);
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwLaunchable(id, Direction.NORTH);
		// Get the square were the disc should land.
		assertTrue(game.getGrid().getSquare(new Coordinate(3,4)).getInventory().hasItem(id));
	}

	/**
	 * Throw a charged disk at a teleporter. assert that it is teleporter and continued along its path
	 */
	@Test
	public void throwChargedDiskTeleporter(){
		ThrowLaunchableHandler ih = new ThrowLaunchableHandler(game, null);
		
		// Make sure the grid is clear
		game.getPowerManager().clearPowerFailures();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleport teleport = new Teleport();
		Teleport teleport2 = new Teleport();
		teleport.setDestination(teleport2);
		s1.getInventory().addItem(teleport);
		s2.getInventory().addItem(teleport2);
		
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwLaunchable(id, Direction.NORTH);
		// Get the square were the disc should land.
		assertTrue(game.getGrid().getSquare(new Coordinate(3,0)).getInventory().hasItem(id));
	}

	/**
	 * Throw the identity disc over a powerfailure. assert that it loses one in range
	 */
	@Test 
	public void throwDiscPowerFailure(){
		
		ThrowLaunchableHandler ih = new ThrowLaunchableHandler(game, null);
		
		// Make sure the grid is clear
		game.getPowerManager().clearPowerFailures();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(0,8));
		
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwLaunchable(id, Direction.NORTH);
		// Get the square were the disc should land.
		assertTrue(game.getGrid().getSquare(new Coordinate(0,6)).getInventory().hasItem(id));
	}

	private Direction getValidDirection(Game game, Square currentPosition){
		Direction direction = null;
		Square next = null;
		while(next == null || next.isObstructed()){ 
			direction = Direction.getRandomDirection();
			try{
				next = game.getGrid().getNeighbor(currentPosition, direction);
			}
			catch(Exception e){}
		}
		return direction;
	}

}

