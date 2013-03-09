package handlers;

import static org.junit.Assert.*;

import java.util.Random;

import items.LightGrenade;

import org.junit.Test;

import square.Direction;
import square.Square;

import game.Game;

public class MoveHandlerTest {

	public MoveHandlerTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * IncrementActions causes IllegalArgumentException due to the setObstacle in Square
	 */
	@Test(expected = IllegalArgumentException.class) // needed to catch exceptions
	public void testCheckToProceed(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		assertTrue(mh.checkToProceed());
		game.getCurrentPlayer().incrementActions();
		game.getCurrentPlayer().incrementActions();
		game.getCurrentPlayer().incrementActions();
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

		game.switchPlayer();

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

		currentPosition.addUsedItem(lg);
		assertFalse(lg.isActive());

		Random random = new Random();
		//TODO: need to check if there is a wall
		while(!game.getCurrentPlayer().hasMoved()){
			Direction[] directions = Direction.values();
			Direction direction = directions[random.nextInt(directions.length)];
			try{
				mh.move(direction);
			}
			catch(Exception e){}
		}

		assertTrue(lg.isActive());
		assertTrue(currentPosition.hasActiveLightGrenade());
	}

	/**
	 * Tests if the endAction method is valid
	 */
	@Test
	public void testEndAction(){
		Game game = new Game(10,10);
		MoveHandler mh = new MoveHandler(game);
		
		assertFalse(mh.endAction());
		game.getCurrentPlayer().move(game.getOtherPlayer().getStartPosition());
		assertTrue(mh.endAction());
	}



}
