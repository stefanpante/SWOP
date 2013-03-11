package handlers;

import static org.junit.Assert.*;


import java.util.Random;

import items.LightGrenade;

import org.junit.Test;

import player.Player;

import square.Direction;
import square.Square;
import square.obstacles.Wall;

import game.Game;

/**
 * Scenario test for the Use Case "Move".
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandlerTest {

	/**
	 * IncrementActions causes IllegalArgumentException due to the setObstacle in Square
	 */
	@Test(expected = IllegalArgumentException.class) // needed to catch exceptions
	public void testCheckToProceed(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		assertTrue(mh.checkToProceed());
		
		for(int i = 0; i < Player.MAX_ALLOWED_ACTIONS; i++)
			game.getCurrentPlayer().incrementActions();
		
		assertFalse(mh.checkToProceed());
	}

	/**
	 *  tests the check to proceed after real moves.
	 */
	@Test
	public void testCheckToProceedAfterMove(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		Direction[] directions = Direction.values();
		Random random = new Random();
		int moves = 0;
		
		assertTrue(mh.checkToProceed());
		while(moves < 3){
			try{
				mh.move(directions[random.nextInt(directions.length)]);
				moves++;
			}
			catch(Exception e){}
		}
		
		assertFalse(mh.checkToProceed());
	}
	
	/**
	 * Players are in start position, 
	 * Move to the west, northwest, south west, south and south east should cause an IllegalStateException for player 1
	 * Move to the east, northeast, south east, north and north west should cause an IllegalStateException for player 2
	 */
	@Test(expected = IllegalArgumentException.class) 
	public void testIllegalMove(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		mh.move(Direction.WEST);
		mh.move(Direction.NORTHWEST);
		mh.move(Direction.SOUTHWEST);
		mh.move(Direction.SOUTH);
		mh.move(Direction.SOUTHEAST);

		game.switchToNextPlayer();

		mh.move(Direction.EAST);
		mh.move(Direction.NORTHEAST);
		mh.move(Direction.SOUTHEAST);
		mh.move(Direction.NORTH);
		mh.move(Direction.NORTHWEST);
	}

	/**
	 * When a player moves onto a light grenade, his turn should end
	 */
	@Test
	public void testMoveActiveLightGrenade(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);

		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();

		currentPosition.getUsedInventory().addItem(lg);
		assertFalse(lg.isActive());

		Random random = new Random();
		//TODO: need to check if there is a wall
		while(!game.getCurrentPlayer().hasMoved()){
			Direction[] directions = Direction.values();
			Direction direction = directions[random.nextInt(directions.length)];
			try{
				mh.move(direction);
				System.out.println(game.getCurrentPlayer().hasMoved());
			}
			catch(Exception e){}
		}

		assertTrue(lg.isActive());
		assertTrue(currentPosition.getUsedInventory().hasActiveLightGrenade());
	}

	/**
	 * Tests what happens when a player moves onto active lightGrenade
	 */
	@Test
	public void testMoveOntoActiveLightGrenade(){
		fail("Not yet implemented");
	}
	
	/**
	 * Tests what happens when a player moves onto an active lightgrenade 
	 * when the square is experiencing powerfailure.
	 */
	@Test
	public void testMoveOntoActiveLightGrenadePowerFailure(){
		fail("Not yet implemented");
	}
	
	/**
	 * Tests a move to a square were a wall is positioned
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToWall(){
		Game game = new Game(20,20);
		MoveHandler mh = new MoveHandler(game);
		
		Square position  = game.getCurrentPlayer().getPosition();
		Square eastNeighbor = game.getGrid().getNeighbor(position, Direction.EAST);
		Square neighborOfEastNeighbor = game.getGrid().getNeighbor(eastNeighbor, Direction.EAST);
		
		Wall wall = new Wall(eastNeighbor, neighborOfEastNeighbor);
		eastNeighbor.setObstacle(wall);
		neighborOfEastNeighbor.setObstacle(wall);
		
		assertTrue(eastNeighbor.isObstructed());
		assertTrue(neighborOfEastNeighbor.isObstructed());
		
		mh.move(Direction.EAST);
		
	}
	
	/**
	 * Tests a move to a square were the lightTrial of another player is positioned
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToLightTrail(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		
		// replace the grid representation with a grid without walls
		// TODO: wait until grid is implemented
	}
	
	/**
	 * Tests a move to a square were another player is situated
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToOtherPlayer() {
		
	}
	
	/**
	 * Tests a move to a square with a power failure
	 */
	@Test
	public void testMoveToPowerFailure(){
		fail("Not yet implemented");
	}
	
	/**
	 * Tests if the endAction method is valid
	 */
	@Test
	public void testEndAction(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		
		assertFalse(mh.endAction());
		game.getCurrentPlayer().move(game.getNextPlayer().getStartPosition());
		assertTrue(mh.endAction());
	}



}
