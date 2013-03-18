package TestsIteration3;

import items.IdentityDisc;
import game.Game;
import handlers.MoveHandler;
import handlers.PickUpHandler;

import org.junit.Test;

import player.Player;

import square.Direction;
import square.Square;

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
	
		// Make sure that there are no powerfailures
		game.clearPowerFailures();
		
		// move into the direction that the Identitydisc is situated
		mh.move(direction);
		
		// Try to pick up the identity disc, should cause an exception
		ph.pickUp(new IdentityDisc());
	}
	
	/**
	 * Throw an identitydisc into a direction specified. Assert that is moves 4 squares
	 * (no obstacles or boundaries)
	 */
	
	/**
	 * Throw an identity disc when there is a wall in the way
	 */
	
	/**
	 * Throw an identity disc in the direction of the boundary the grid and test for effects
	 */
	
	/**
	 * Throw an identity disc at another player. Assert that the other player loses a turn.
	 */
	
	/**
	 * Throw a lightdisc at a transporter. Assert that it is teleported to the other square connected
	 * to the teleporter.
	 */
	
	/**
	 * Throw the identity disc over a powerfailure. assert that it loses one in range
	 */
	
	/**
	 * throw the identity disc over multiple powerFailures. assert it lost range.
	 */
	
	/**
	 * assert that an identity disc cannot be placed on a wall.
	 */

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
