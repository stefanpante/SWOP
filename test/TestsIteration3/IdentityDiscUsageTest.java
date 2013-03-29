package TestsIteration3;

import static org.junit.Assert.*;

import java.util.ArrayList;

import items.IdentityDisc;
import game.Game;
import handlers.MoveHandler;
import handlers.PickUpHandler;
import handlers.ThrowItemHandler;
import handlers.UseItemHandler;

import org.junit.Test;

import player.Player;

import square.Direction;
import square.Square;
import utils.Coordinate;

public class IdentityDiscUsageTest {

	public IdentityDiscUsageTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Move to a square where an identity disc is situated an try to pick it up
	 */

	@Test
	public void pickUpIdentityDiscTest(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);

		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		Square next = game.getGrid().getNeighbor(currentPlayer.getPosition(), direction);
		IdentityDisc id = new IdentityDisc();
		next.getInventory().addItem(id);

		// Make sure that there are no powerfailures
		game.clearPowerFailures();

		// move into the direction that the Identitydisc is situated
		mh.move(direction);

		// Try to pick up the identity disc, should succeed
		ph.pickUp(id);
		assertTrue(currentPlayer.getInventory().hasItem(id));

	}

	@Test
	public void pickUpChargedDisk(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);

		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		Square next = game.getGrid().getNeighbor(currentPlayer.getPosition(), direction);
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		next.getInventory().addItem(id);

		// Make sure that there are no powerfailures
		game.clearPowerFailures();

		// move into the direction that the Identitydisc is situated
		mh.move(direction);

		// Try to pick up the identity disc, should succeed
		ph.pickUp(id);
		assertTrue(currentPlayer.getInventory().hasItem(id));

	}
	/**
	 * Move to a square without an identity disc and try to pick it up, should cause an exception
	 */
	@Test(expected = IllegalStateException.class)
	public void pickUpIdentityDiscEmpty(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);

		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		// Make sure that there are no powerfailures and obstacles
		game.clearPowerFailures();
		game.removeAllObstacles();

		// move into the direction that the Identitydisc is situated
		mh.move(direction);

		// Try to pick up the identity disc, should cause an exception
		ph.pickUp(new IdentityDisc());

	}

	@Test(expected = IllegalStateException.class)
	public void pickUpChargedDiskEmpty(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);

		Player currentPlayer = game.getCurrentPlayer();

		Direction direction = getValidDirection(game, currentPlayer.getPosition());

		// Make sure that there are no powerfailures and obstacles
		game.clearPowerFailures();
		game.removeAllObstacles();

		// move into the direction that the Identitydisc is situated
		mh.move(direction);

		// Try to pick up the identity disc, should cause an exception
		ph.pickUp(new ChargedIdentityDisc());

	}

	/**
	 * throwItem an identitydisc into a direction specified. Assert that is moves 4 squares
	 * (no obstacles or boundaries)
	 */
	@Test
	public void testthrowItemIdentityDisc(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);	

		// make sure there are no powerfailures
		game.clearPowerFailures();
		game.clearObstacles();

		// throwItem the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throwItem directions
		Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);

		// Place the player in the middle of the grid
		for(Direction direction: directions){
			// new identity disc
			IdentityDisc id = new IdentityDisc();
			// Player should have identitydisc in inventory
			game.getCurrentPlayer().getInventory().addItem(id);
			// throwItem the identitydisc in the given direction
			ih.throwItem(id,direction);
			Square position = game.getCurrentPlayer().getPosition();
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);
			position = game.getGrid().getNeighbor(position, direction);

			// assert that the square has the item
			assertTrue(position.getInventory().hasItem(id));

		}
	}
	
	/**
	 * Try to throwItem a identitydisc which isn't in the players inventory.
	 */
	@Test(expected = IllegalStateException.class)
	public void throwItemDiskNotInPlayer(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);
		ih.throwItem(new IdentityDisc(), Direction.NORTH);
	}

	/**
	 * throwItem a charged disk, assert that it stops at a boundary
	 */
	//TODO: replace hard coded coordinates with hSize -1 and vSize -1
	@Test
	public void throwItemChargedDisk(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);	

		// make sure there are no powerfailures
		game.clearPowerFailures();
		game.clearObstacles();

		// throwItem the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throwItem directions
		Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);

		// new identity disc
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id);
		// throwItem the identitydisc in the given direction
		ih.throwItem(id,Direction.NORTH);
		assertTrue(game.getGrid().getSquare(new Coordinate(4,0)).getInventory().hasItem(id);

		// new identity disc
		ChargedIdentityDisc id2 = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id2);
		// throwItem the identitydisc in the given direction
		ih.throwItem(id2,Direction.SOUTH);
		assertTrue(game.getGrid().getSquare(new Coordinate(4,9)).getInventory().hasItem(id2));


		// new identity disc
		ChargedIdentityDisc id3 = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id3);
		// throwItem the identitydisc in the given direction
		ih.throwItem(id3,Direction.WEST);
		assertTrue(game.getGrid().getSquare(new Coordinate(4,9)).getInventory().hasItem(id3);

		// new identity disc
		ChargedIdentityDisc id4 = new ChargedIdentityDisc();
		// Player should have identitydisc in inventory
		game.getCurrentPlayer().getInventory().addItem(id4);
		// throwItem the identitydisc in the given direction
		ih.throwItem(id4,Direction.EAST);
		assertTrue(game.getGrid().getSquare(new Coordinate(0,4)).getInventory().hasItem(id4);
	}

	/**
	 * throwItem an identity disc when there is a wall in the way. assert that it drops in front of the wall
	 */
	@Test
	public void throwItemDiskWall(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);	

		// make sure there are no powerfailures
		game.clearPowerFailures();
		game.clearObstacles();

		// throwItem the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throwItem directions
		Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);

		Square s1 = game.getGrid(new Coordinate(6,4));
		square s2 = game.getGrid(new Coordinate(6,5));
		Wall wall = new Wall(s1,s2);

		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);

		ih.throwItem(id, Direction.WEST);
		assertTrue(game.getGrid().getSquare(new Coordinate(5,4)).getInventory().hasItem(id));

	}

	/**
	 * throwItem a charged disk at a wall. Assert that it drops in front of the wall
	 */
	@Test
	public void throwItemChargedDiskWall(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);	

		// make sure there are no powerfailures
		game.clearPowerFailures();
		game.clearObstacles();

		// throwItem the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(4,4));
		// All possible throwItem directions
		Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);

		Square s1 = game.getGrid(new Coordinate(9,4));
		square s2 = game.getGrid(new Coordinate(9,5));
		Wall wall = new Wall(s1,s2);

		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);

		ih.throwItem(id, Direction.WEST);
		assertTrue(game.getGrid().getSquare(new Coordinate(8,4)).getInventory().hasItem(id));
	}

	/**
	 * throwItem an identity disc in the direction of the boundary the grid and test for effects
	 */
	@Test
	public void throwItemDiskBoundary(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);	

		// make sure there are no powerfailures
		game.clearPowerFailures();
		game.clearObstacles();

		// throwItem the identity disc in all possible directions.
		Square currentPosition = game.getGrid().getSquare(new Coordinate(2,4));
		// All possible throwItem directions
		Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
		game.getCurrentPlayer().move(currentPosition);
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		ih.throwItem(id, Direction.EAST)
		assertTrue(game.getGrid().getSquare(new Coordinate(0,4)).getInventory().hasItem(id));
		

	}
	/**
	 * throwItem an identity disc at another player. Assert that the other player loses a turn.
	 */
	@Test
	public void hitOtherPlayer(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);

		// Make sure there aren't any obstacles and powerfailures in the way
		game.clearPowerFailures();
		game.clearObstacles();

		// Place the players so they can hit each other
		game.getCurrentPlayer().move(game.getGrid().getSquare(new Coordinate(4,4)));
		game.getNextPlayer().move(game.getGrid().getSquare(new Coordinate(1,4)));

		// Current player should have identity disc in inventory
		assertEquals(game.getCurrentPlayer().getPosition(), game.getGrid().getSquare(new Coordinate(4,4)));
		assertEquals(game.getNextPlayer().getPosition(), game.getGrid().getSquare(new Coordinate(1,4)));

		// The inventory of the player should contain the identityDisc
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		// throwItem the identity disc at the other player
		ih.throwItem(id, Direction.NORTH);

		// Assert the effect of the identityDisc
		assertTrue(game.getNextPlayer().getPosition().getInventory().hasItem(id));
		assertFalse(game.getCurrentPlayer().getInventory().hasItem(id));
		// Player loses his turn
		assertTrue();


	}

	/**
	 * throwItem a charged Disk at another player, assert that he loses a turn.
	 */
	@Test
	public void chargedDiskAtOtherPlayer(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);

		// Make sure there aren't any obstacles and powerfailures in the way
		game.clearPowerFailures();
		game.clearObstacles();

		// Place the players so they can hit each other
		game.getCurrentPlayer().move(game.getGrid().getSquare(new Coordinate(4,4)));
		game.getNextPlayer().move(game.getGrid().getSquare(new Coordinate(1,4)));

		// Current player should have identity disc in inventory
		assertEquals(game.getCurrentPlayer().getPosition(), game.getGrid().getSquare(new Coordinate(9,4)));
		assertEquals(game.getNextPlayer().getPosition(), game.getGrid().getSquare(new Coordinate(0,4)));

		// The inventory of the player should contain the identityDisc
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		// throwItem the identity disc at the other player
		ih.throwItem(id, Direction.WEST);

		// Assert the effect of the identityDisc
		assertTrue(game.getNextPlayer().getPosition().getInventory().hasItem(id));
		assertFalse(game.getCurrentPlayer().getInventory().hasItem(id));
		// Player loses his turn
		assertTrue();
	}

	/**
	 * throwItem a Identity disc at a transporter. Assert that it is teleported to the other square connected and moves further
	 * to the teleporter.
	 */
	@Test 
	public void throwItemDiscTeleporter(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);
		
		// Make sure the grid is clear
		game.clearPowerFailures();
		game.clearObstacles();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwItem(id, Direction.NORTH);
		// Get the square were the disc should land.
		assertTrue(game.getGrid().getSquare(new Coordinate(3,2)).getInventory().hasItem(id));
	}

	/**
	 * throwItem a charged disk at a teleporter. assert that it is teleporter and continued along its path
	 */
	@Test
	public void throwItemChargedDiskTeleporter(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);
		
		// Make sure the grid is clear
		game.clearPowerFailures();
		game.clearObstacles();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		ChargedIdentityDisc id = new ChargedIdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwItem(id, Direction.NORTH);
		// Get the square were the disc should land.
		assertTrue(game.getGrid().getSquare(new Coordinate(3,0)).getInventory().hasItem(id));
	}

	/**
	 * throwItem the identity disc over a powerfailure. assert that it loses one in range
	 */
	@Test 
	public void throwItemDiscPowerFailure(){
		Game game = new Game(10,10);
		ThrowItemHandler ih = new ThrowItemHandler(game, null);
		
		// Make sure the grid is clear
		game.clearPowerFailures();
		game.clearObstacles();
		
		// add a teleporter 
		Square s1 = game.getGrid().getSquare(new Coordinate(0,7));
		Square s2 = game.getGrid().getSquare(new Coordinate(0,8));
		
		
		IdentityDisc id = new IdentityDisc();
		game.getCurrentPlayer().getInventory().addItem(id);
		
		ih.throwItem(id, Direction.NORTH);
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

	private void removeAllObstacles(Game game){
		ArrayList<Square> squares = game.getGrid().getAllSquares();
	}


}
