package game;

import static org.junit.Assert.*;




import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

import controller.MoveHandler;
import controller.TurnHandler;
import item.LightGrenade;
import item.Teleport;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import game.Player;

import square.Direction;
import square.Square;
import square.obstacle.LightTrail;
import square.obstacle.Wall;
import square.power.failure.PrimaryPowerFail;
import util.Coordinate;

import game.Game;
import grid.Grid;
import grid.RandomGridBuilder;
import grid.GridProvider;

/**
 * Scenario test for the Use Case "Move".
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class MoveHandlerTest {

	private Game game;
	
	private MoveHandler moveHandler;
	
	private TurnHandler turnHandler;
	
	@Before
	public void setUpBefore(){
		Grid grid = GridProvider.getEmptyGrid();
		game = new Game(grid);
		
		moveHandler = new MoveHandler(game,null);
		turnHandler = new TurnHandler(game, null);
	}
	
	/**
	 * Players are in start position, 
	 * Move to the west, northwest, south west, south and south east should cause an IllegalStateException for player 1
	 * Move to the east, northeast, south east, north and north west should cause an IllegalStateException for player 2
	 */
	@Test(expected = NoSuchElementException.class) 
	public void testIllegalMove(){
		
		// For the first player, all these moves should throw an NoSuchElementException
		moveHandler.move(Direction.WEST);
		moveHandler.move(Direction.NORTHWEST);
		moveHandler.move(Direction.SOUTHWEST);
		moveHandler.move(Direction.SOUTH);
		moveHandler.move(Direction.SOUTHEAST);

		game.switchToNextPlayer();
		
		// For the second player, all these moves should throw an NoSuchElementException
		moveHandler.move(Direction.EAST);
		moveHandler.move(Direction.NORTHEAST);
		moveHandler.move(Direction.SOUTHEAST);
		moveHandler.move(Direction.NORTH);
		moveHandler.move(Direction.NORTHWEST);
	}

	/**
	 *  Tests if a LightGrenade is activated when the player moves from his current position
	 */
	@Test
	public void testMoveActiveLightGrenade(){
		Square currentPosition = game.getCurrentPlayer().getPosition();
		LightGrenade lg = new LightGrenade();

		if(!currentPosition.getInventory().hasLightGrenade()) {
			currentPosition.getInventory().addItem(lg);
			lg.drop();
		}else{
			lg = currentPosition.getInventory().getLightGrenade();
		}
		
		for(Direction direction: Direction.values()) {
			try{
				moveHandler.move(direction);
				break;
			}
			catch(Exception e){
				
			}
		}

		// When moved, the LightGrenade on the previous square should become active
		assertTrue(game.getCurrentPlayer().hasMoved());
		assertTrue(lg.isActive());
		assertTrue(currentPosition.getInventory().getLightGrenade().isActive());
	}

	/**
	 * Tests what happens when a player moves onto active lightGrenade
	 */
	@Test
	public void testMoveOntoActiveLightGrenade(){
		Player currentPlayer = game.getCurrentPlayer();
		int remainingActions = currentPlayer.getRemainingActions();
		
		assertEquals(remainingActions, Player.MAX_ALLOWED_ACTIONS);
		
		// Place a grenade on a position near the player
		Square currentPosition = game.getCurrentPlayer().getPosition();
		
		// Search for a square that isn't obstructed
		Direction direction = getNonObstructedDirection(currentPosition);
		Square next = game.getGrid().getNeighbor(currentPosition, direction);
		
		// add a LightGrenade to the square
		LightGrenade lg = new LightGrenade();
		next.getInventory().addItem(lg);
		lg.drop();
		lg.activate();
		
		// move to the square containing the active LightGrenade
		moveHandler.move(direction);
		assertFalse(currentPlayer.equals(game.getCurrentPlayer()));
		
		if(!(currentPlayer.getPosition().getPower().isFailing()))
			assertEquals(currentPlayer.getRemainingActions(), 2);
	}
	
	/**
	 * Tests what happens when a player moves onto an active LightGrenade 
	 * when the square is experiencing PowerFailure.
	 */
	@Test
	public void testMoveOntoActiveLightGrenadePowerFailure(){
		Player currentPlayer = game.getCurrentPlayer();
		
		// Place a grenade on a position near the player
		Square currentPosition = game.getCurrentPlayer().getPosition();
		
		// Search for a position on the grid where the player can move to
		Direction direction = getNonObstructedDirection(currentPosition);
		Square next = game.getGrid().getNeighbor(currentPosition, direction);
		
		// Set the state of the square to PowerFailure
		next.setPower(new PrimaryPowerFail());
		LightGrenade lg = new LightGrenade();
		next.getInventory().addItem(lg);
		lg.drop();
		lg.activate();
		
		// Move to the next square
		moveHandler.move(direction);
		// Test the effect of the LightGrenade
		assertFalse(currentPlayer.equals(game.getCurrentPlayer()));
		System.out.println(currentPlayer.getRemainingActions());
		assertEquals(currentPlayer.getRemainingActions(), 1);
	}
	
	/**
	 * Tests a move to a square were a wall is positioned
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToWall(){
		Square position  = game.getCurrentPlayer().getPosition();
		Square eastNeighbor = game.getGrid().getNeighbor(position, Direction.EAST);
		Square neighborOfEastNeighbor = game.getGrid().getNeighbor(eastNeighbor, Direction.EAST);
		
		@SuppressWarnings("unused")
		Wall wall = new Wall(eastNeighbor, neighborOfEastNeighbor);
		
		assertTrue(eastNeighbor.isObstructed());
		assertTrue(neighborOfEastNeighbor.isObstructed());
		
		moveHandler.move(Direction.EAST);	
	}
	
	/**
	 * Tests a move to a square were the lightTrial of another player is positioned
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToLightTrail(){
		Square currentPosition = game.getCurrentPlayer().getPosition();
		
		// Search for a square that isn't obstructed
		Direction direction = getNonObstructedDirection(currentPosition);
		Square next = game.getGrid().getNeighbor(currentPosition, direction);
		
		// add a LightTrail to the square
		LightTrail lt = new LightTrail();
		lt.addSquare(next);
		
		// Move to a LightTrail should not be allowed.
		moveHandler.move(direction);
	}
	
	/**
	 * Tests a move to a square were another player is situated
	 */
	@Test(expected = IllegalStateException.class)
	public void testMoveToOtherPlayer() {
		Player otherPlayer =  game.getNextPlayer();
		
		/* 	Search a neighboring square of the other player position where the current player
			can be situated, to test the move to. */
		Square otherPos = otherPlayer.getPosition();
		
		Direction direction = getNonObstructedDirection(otherPos);
		Square next = game.getGrid().getNeighbor(otherPos, direction);
		
		// Positions the player next to the other Player
		Player currentPlayer = game.getCurrentPlayer();
		currentPlayer.move(next);
		
		// Moves to the position where the other player is situated
		assertTrue(game.getCurrentPlayer() == currentPlayer);
		// Should throw IllegalStateException
		moveHandler.move(direction.opposite());		
	}
	
	/**
	 * Tests a move to a square with a power failure
	 */
	@Test
	public void testMoveToPowerFailure(){
		Player currentPlayer = game.getCurrentPlayer();
		
		// search for a square that isn't obstructed near the player
		Square currentPosition = game.getCurrentPlayer().getPosition();
		
		Direction direction = getNonObstructedDirection(currentPosition);
		Square next = game.getGrid().getNeighbor(currentPosition, direction);
		
		// Set a PowerFailure on the square
		next.setPower(new PrimaryPowerFail());
		
		// Move to the square with the PowerFailure
		moveHandler.move(direction);
		
		// Test the effect of the PowerFailure
		assertFalse(currentPlayer.equals(game.getCurrentPlayer()));
		assertEquals(currentPlayer.getRemainingActions(), 3);
	}
	
	/**
	 * Tests if the endAction method is valid
	 */
	@Test
	public void testHasWon(){
		assertFalse(moveHandler.hasWon());
		
		Square currentPosition = game.getCurrentPlayer().getStartPosition();
		
		Direction direction = getNonObstructedDirection(currentPosition);
		Square next = game.getGrid().getNeighbor(currentPosition, direction);
		
		game.getNextPlayer().move(next);
		
		// to remove the lighttrail 
		game.getNextPlayer().alertObservers();
		game.getNextPlayer().alertObservers();
		game.getNextPlayer().alertObservers();
		game.getNextPlayer().alertObservers();
	
		game.getCurrentPlayer().move(game.getNextPlayer().getStartPosition());
		
		assertTrue(moveHandler.hasWon());
	}
	
	@Test
	public void testTeleport() {		
		Square currentPosition = game.getCurrentPlayer().getStartPosition();
		Square otherPosition = game.getNextPlayer().getStartPosition();
		
		Direction directionOne = getNonObstructedDirection(currentPosition);
		Square squareOne = game.getGrid().getNeighbor(currentPosition, directionOne);
		
		Direction directionTwo = getNonObstructedDirection(otherPosition);
		Square squareTwo = game.getGrid().getNeighbor(otherPosition, directionTwo);
		
		Teleport teleport = new Teleport();
		Teleport teleportDestination = new Teleport();
		
		teleport.setDestination(teleportDestination);
		teleportDestination.setDestination(teleport);
		
		// We have 2 squares. Now we place the teleport items.
		squareOne.getInventory().addItem(teleport);
		squareTwo.getInventory().addItem(teleportDestination);
		
		moveHandler.move(directionOne);
		
		// Player should now be on SquareTwo
		assertEquals(game.getCurrentPlayer().getPosition(), squareTwo);
	}
	
	/**
	 * Finds a direction from the given square which is not obstructed.
	 */
	private Direction getNonObstructedDirection(Square currentPosition) {
		Direction[] directions = Direction.values();
		Random random = new Random();
		
		Direction direction = null;
		Square next = null;
		
		while(next == null || next.isObstructed() || next.getInventory().hasTeleport() || next.getInventory().hasLightGrenade()){ 
			direction = directions[random.nextInt(directions.length)];
			try{
				next = game.getGrid().getNeighbor(currentPosition, direction);
			}
			catch(Exception e){}
		}
		
		return direction;
	}


}
