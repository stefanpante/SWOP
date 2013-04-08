package TestsIteration3;

import static org.junit.Assert.*;


import org.junit.Test;

import game.Game;
import controller.MoveHandler;
import controller.PickUpHandler;
import square.Direction;
import square.Square;
import square.obstacle.LightTrail;
import util.Coordinate;

public class TeleporterUsageTest {

	public TeleporterUsageTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Move a player onto a teleporter. Assert that the player is moved to the other square that is part
	 * of the teleporter
	 */
	@Test
	public void basicTeleporterTest(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		
		// clear all powerfailures and obstacles
		game.clearPowerFailures();
		game.clearObstacles();
		
		Square s1 = game.getGrid().getSquare(new Coordinate(0,8));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		mh.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(), s2);
		
	}
	
	/**
	 *Move a player onto a teleporter. Assert that he cannot pick up an item from the source teleporter.
	 *
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterFirstSquare(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);
		
		// clear all powerfailures and obstacles
		game.clearPowerFailures();
		game.clearObstacles();
		
		Square s1 = game.getGrid().getSquare(new Coordinate(0,8));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		Item item = new Item();
		s1.getInventory().addItem(item);
		
		mh.move(Direction.NORTH);
		// should throw an exception
		ph.pickUp(item);
		
	}
	

	/**
	 * Move a player onto a teleporter. Assert that he can pick up an item fromt the destination square.
	 */
	@Test(expected = IllegalStateException.class)
	public void pickupTeleporterSecSquare(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);
		
		// clear all powerfailures and obstacles
		game.clearPowerFailures();
		game.clearObstacles();
		
		Square s1 = game.getGrid().getSquare(new Coordinate(0,8));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		Item item = new Item();
		s2.getInventory().addItem(item);
		
		mh.move(Direction.NORTH);
		ph.pickUp(item);
		
		assertTrue(game.getCurrentPlayer().getInventory().hasItem(item));
		assertFalse(s2.getInventory().hasItem(item));
	}
	
	/** 
	 * Move a player onto a teleporter. Another player is situated on the other teleporter.
	 * Assert that he is not transported.
	 */
	@Test
	public void testOtherPlayerDestination(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);
		
		// clear all powerfailures and obstacles
		game.clearPowerFailures();
		game.clearObstacles();
		
		Square s1 = game.getGrid().getSquare(new Coordinate(0,8));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		
		game.getNextPlayer().move(s2);
		mh.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(),s1);
		assertEquals(game.getNextPlayer().getPosition(), s2);
	}
	
	/**
	 * Teleport a player through a teleporter. Assert that his lighttrail is split.
	 */
	
	@Test
	public void testSplitLightTrail(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game, null);
		PickUpHandler ph = new PickUpHandler(game, null);
		
		// clear all powerfailures and obstacles
		game.clearPowerFailures();
		game.clearObstacles();
		
		Square s1 = game.getGrid().getSquare(new Coordinate(0,8));
		Square s2 = game.getGrid().getSquare(new Coordinate(3,4));
		Teleporter teleporter = new Teleporter(s1, s2);
		mh.move(Direction.NORTH);
		assertEquals(game.getCurrentPlayer().getPosition(),s2);
		mh.move(Direction.NORTH);
		LightTrail lt = game.getLightTrail(game.getCurrentPlayer());
		assertTrue(lt.contains(s1));
		assertTrue(lt.contains(game.getCurrentPlayer().getStartPosition()));
		assertTrue(lt.contains(s2));
		
	}

}
