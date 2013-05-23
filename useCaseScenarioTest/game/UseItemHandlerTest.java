package game;

import static org.junit.Assert.assertFalse;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import controller.MoveHandler;
import controller.TurnHandler;
import controller.UseItemHandler;
import item.LightGrenade;
import grid.Grid;
import grid.RandomGridBuilder;

import org.junit.Before;
import org.junit.Test;


import util.Direction;
import square.Square;
import square.multi.LightTrail;
import square.obstacle.Wall;

/**
 * Scenario test for the use case "use Item"
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class UseItemHandlerTest {
	
	private Game game;
	
	private UseItemHandler useItemHandler;
	
	private TurnHandler turnHandler;
	
	@Before
	public void setUpBefore() {
		RandomGridBuilder gridBuilder = new RandomGridBuilder(10, 10);
		game = new Game(gridBuilder.getGrid());
		
		useItemHandler = new UseItemHandler(game,null);
		turnHandler = new TurnHandler(game, null);
	}

	/**
	 * Test the placement of a grenade on a square when there is
	 * no other grenade placed on the square or used on the square
	 */
	@Test
	public void testPlaceGrenade() throws Exception {		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
		
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		useItemHandler.useItem(lg);
		
		// Item shouldn't be in the inventory of the player anymore
		assertFalse(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// Item should be in the inventory of the square
		assertTrue(game.getCurrentPlayer().getPosition().getInventory().getAllItems().contains(lg));
		
		// The state of the lightGrenade should still be inactive when placed until moved
		assertTrue(lg.isDropped());
		assertFalse(lg.isActive());
		
		// Item should become active when moved
		MoveHandler mh = new MoveHandler(game,null);
		
		for(Direction direction: Direction.values()) {
			try{
				mh.move(direction);
				System.out.println("moved");
				break;
			} catch (Exception e) {
			}
		}
		
		System.out.println(lg.isActive());
		assertTrue(lg.isActive());		
	}
	
	/**
	 * Test the placement of a grenade when there is already an active
	 * light grenade on the square
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade2() throws Exception {		
		LightGrenade lg2 = new LightGrenade();
		game.getCurrentPlayer().getPosition().getInventory().addItem(lg2);
		lg2.activate();
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
	
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// This should throw an exception
		useItemHandler.useItem(lg);
	}
	
	/**
	 * Test the placement of a grenade when there is an already used
	 * grenade on the Square.
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceGrenade3() throws Exception {
		LightGrenade lg2 = new LightGrenade();
		game.getCurrentPlayer().getPosition().getInventory().addItem(lg2);
		lg2.wearOut();
		
		LightGrenade lg = new LightGrenade();
		game.getCurrentPlayer().getInventory().addItem(lg);
	
		// Item should be in the inventory of the player
		assertTrue(game.getCurrentPlayer().getInventory().getAllItems().contains(lg));
		// This should throw an exception
		useItemHandler.useItem(lg);	
	}
	
	/**
	 * Try to place an item on a wall object
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceOnWall() throws Exception {
		LightGrenade lg = new LightGrenade();
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
		game.getCurrentPlayer().getInventory().addItem(lg);
		useItemHandler.useItem(lg);
		
	}
	
	/**
	 * Try to place an item on a LightTrail object 
	 */
	@Test(expected = IllegalStateException.class)
	public void testPlaceOnLightTrail() throws Exception {
		LightGrenade lg = new LightGrenade();
		Grid grid = game.getGrid();
		
		Random random = new Random();
		
		ArrayList<Square> squaresgrid = grid.getAllSquares();
		Square obstructedSquare = squaresgrid.get(random.nextInt(squaresgrid.size()));
		
		LightTrail lt = new LightTrail();
		lt.addSquare(obstructedSquare);
		game.getCurrentPlayer().move(obstructedSquare);
		// Should never get to this.
		game.getCurrentPlayer().getInventory().addItem(lg);
		useItemHandler.useItem(lg);
	}
}
