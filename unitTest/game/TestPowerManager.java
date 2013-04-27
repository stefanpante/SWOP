package game;

import static org.junit.Assert.*;
import grid.Grid;
import grid.GridProvider;

import org.junit.Before;
import org.junit.Test;

import square.Direction;
import square.Square;
import square.power.Power;
import square.power.failure.PrimaryPowerFail;
import square.power.failure.SecondaryPowerFail;
import square.power.failure.TertiaryPowerFail;
import util.Coordinate;

/**
 * Tests the workings of the PowerManager.
 * Such as decrementing actions and turns.
 * 
 * Creating the secondary and tertiary power failures in a proper manner.
 * 
 * @author vincentreniers
 */
public class TestPowerManager {

	private PowerManager powerManager;
	
	private Grid grid;
	
	private Square square;
	
	private Coordinate coordinate;
	
	@Before
	public void setUpBefore() {
		this.grid = GridProvider.getEmptyGrid();
		Game game = new Game(grid);
		
		this.powerManager = game.getPowerManager();
		this.coordinate = new Coordinate(3,3);;
		this.square = grid.getSquare(coordinate);
		
		powerManager.clearPowerFailures();
	}
	
	@Test
	public void powerFailureSquare() {
		assertFalse(square.getPower().isFailing());
		
		this.powerManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
	}
	
	@Test
	public void clearPowerFailures() {
		this.powerManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
		
		this.powerManager.clearPowerFailures();
		
		assertFalse(square.getPower().isFailing());
	}
	
	@Test
	public void decreaseTurn() {
		this.powerManager.powerFailSquare(square);
		
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS);
		assertEquals(square.getPower().getRemainingActions(), PrimaryPowerFail.ACTIONS);
		
		this.powerManager.decreaseTurn();
		
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS - 1);
	}
	
	@Test
	public void decreaseTurns() {
		this.powerManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS);
		
		this.powerManager.decreaseTurn();
		this.powerManager.decreaseTurn();
		
		assertTrue(square.getPower().isFailing());
		
		this.powerManager.decreaseTurn();
		
		assertFalse(square.getPower().isFailing());
	}
	
	@Test
	public void decreaseAction() {
		this.powerManager.powerFailSquare(square);
		
		Power secondary = square.getPower().getChild();
		Power tertiary = secondary.getChild();
		
		assertEquals(secondary.getRemainingActions(), SecondaryPowerFail.ACTIONS);
		assertEquals(tertiary.getRemainingActions(), TertiaryPowerFail.ACTIONS);
		
		this.powerManager.decreaseAction();
		
		assertEquals(secondary.getRemainingActions(), SecondaryPowerFail.ACTIONS - 1);
		assertEquals(tertiary.getRemainingActions(), TertiaryPowerFail.ACTIONS); // (because it is reset)
	}
	
	/**
	 * Rotate secondary after each two actions.
	 */
	@Test
	public void rotationOfSecondary() {
		this.powerManager.powerFailSquare(square);
		
		Power secondary = square.getPower().getChild();
		
		assertTrue(secondary.getDirection() != null);
		
		Direction direction = secondary.getDirection();
		
		this.powerManager.decreaseAction();
		
		assertTrue(secondary.getDirection() == direction);
		assertEquals(secondary.getDirection(), direction);
		
		this.powerManager.decreaseAction();
		
		assertTrue(secondary.getDirection() != direction);
		assertEquals(secondary.getDirection(), secondary.getRotation().rotate(direction));
	}
	
	@Test
	public void creationOfSecondary() {
		this.powerManager.powerFailSquare(square);
		
		Power primary = square.getPower();
		Power secondary = primary.getChild();
		
		Square secondarySquare = powerManager.getSquare(secondary);
		
		assertTrue(grid.getNeighbors(square).containsValue(secondarySquare));
	}
	
	/**
	 * If tertiary and secondary are located on a square.
	 * The tertiary square should not be adjacent to the primary.
	 */
	@Test
	public void creationOfTertiary() {
		this.powerManager.powerFailSquare(square);
		
		Power primary = square.getPower();
		Power secondary = primary.getChild();
		Power tertiary = secondary.getChild();
		
		Square secondarySquare = powerManager.getSquare(secondary);
		Square tertiarySquare = powerManager.getSquare(tertiary);
		
		assertFalse(grid.getNeighbors(square).containsValue(tertiarySquare));
		assertTrue(grid.getNeighbors(secondarySquare).containsValue(tertiarySquare));
	}

}
