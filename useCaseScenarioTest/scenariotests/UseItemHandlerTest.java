package scenariotests;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import handler.MoveHandler;
import handler.UseItemHandler;
import item.LightGrenade;
import game.Game;
import grid.Grid;

import org.junit.Test;

import player.Player;
import square.Direction;
import square.Square;
import square.obstacles.LightTrail;
import square.obstacles.Wall;

/**
 * 
 * Scenario test for the use case "use Item"
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 * 
 *
 */
public class UseItemHandlerTest {

	/**
	 * Test the placement of a grenade on a square when there is
	 * no other grenade placed on the square or used on the square
	 */
	@Test
	public void testPlaceGrenade(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game,null);
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
		
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		uh.useItem(lg);
		
		// Item shouldn't be in the inventory of the player anymore
		assertFalse(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// Item should be in the inventory of the square
		assertTrue(game.getCurrentPlayer().getPosition().getInventory().getAllItems().contains(lg));
		// The state of the lightGrenade should still be inactive when placed until moved
		assertFalse(lg.isActive());
		
		
		// Item should become active when moved
		MoveHandler mh = new MoveHandler(game,null);
		Direction[] directions = Direction.values();
		Random random = new Random();
		while(!game.getCurrentPlayer().hasMoved()){
			try{
				mh.move(directions[random.nextInt(directions.length)]);
			}
			catch(Exception e){}
		}
		
		assertTrue(lg.isActive());
		
	}
	
	/**
	 * Test the placement of a grenade when there is already an active
	 * light grenade on the square
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade2(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game,null);
		
		LightGrenade lg2 = new LightGrenade();
		game.getCurrentPlayer().getPosition().getInventory().addItem(lg2);
		lg2.activate();
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
	
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// This should throw an exception
		uh.useItem(lg);
	}
	
	/**
	 * Test the placement of a grenade when there is an already used
	 * grenade on the Square.
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade3(){
		Game game = new Game(10,10);
		UseItemHandler uh = new UseItemHandler(game,null);
		
		LightGrenade lg2 = new LightGrenade();
		game.getCurrentPlayer().getPosition().getInventory().addItem(lg2);
		lg2.wearOut();
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
	
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// This should throw an exception
		uh.useItem(lg);
		
	}
	
	/**
	 * Try to place an item on a wall object
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceOnWall(){
		LightGrenade lg = new LightGrenade();
		Game game = new Game(10, 10);
		Grid grid = game.getGrid();
		Square obstructedSquare = null;
		for(Square square: grid.getAllSquares()){
			if(square.isObstructed() && square.getObstacle() instanceof Wall){
				obstructedSquare = square;
				break;
			}
		}
		
		game.getCurrentPlayer().move(obstructedSquare);
		// Should never get to this.
		UseItemHandler uh = new UseItemHandler(game,null);
		game.getCurrentPlayer().getInventory().addItem(lg);
		uh.useItem(lg);
		
	}
	
	/**
	 * Try to place an item on a LightTrail object 
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceOnLightTrail(){
		LightGrenade lg = new LightGrenade();
		Game game = new Game(10, 10);
		Grid grid = game.getGrid();
		Random random = new Random();
		ArrayList<Square> squaresgrid = grid.getAllSquares();
		Square obstructedSquare = squaresgrid.get(random.nextInt(squaresgrid.size()));
		LightTrail lt = new LightTrail();
		lt.addSquare(obstructedSquare);
		game.getCurrentPlayer().move(obstructedSquare);
		// Should never get to this.
		UseItemHandler uh = new UseItemHandler(game,null);
		game.getCurrentPlayer().getInventory().addItem(lg);
		uh.useItem(lg);
	}
}
