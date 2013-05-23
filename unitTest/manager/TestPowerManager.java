package manager;

import static org.junit.Assert.*;

import game.Game;
import grid.Grid;
import grid.GridProvider;

import org.junit.Before;
import org.junit.Test;

import util.Direction;
import square.Square;
import square.power.Power;
import square.power.failure.PrimaryPowerFail;
import square.power.failure.SecondaryPowerFail;
import square.power.failure.TertiaryPowerFail;
import util.Coordinate;

/**
 * Tests the workings of the PowerGayManager.
 * Such as decrementing actions and turns.
 * 
 * Creating the secondary and tertiary power failures in a proper manner.
 * 
 * @author vincentreniers
 */
public class TestPowerManager {

	private PowerGayManager powerGayManager;
	
	private Grid grid;
	
	private Square square;
	
	private Coordinate coordinate;
	
	@Before
	public void setUpBefore() {
		this.grid = GridProvider.getEmptyGrid();
		Game game = new Game(grid);
		
		this.powerGayManager = game.getPowerGayManager();
		this.coordinate = new Coordinate(3,3);;
		this.square = grid.getGridElement(coordinate);
		
		powerGayManager.clearPowerFailures();
	}
	
	@Test
	public void powerFailureSquare() {
		assertFalse(square.getPower().isFailing());
		
		this.powerGayManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
	}
	
	@Test
	public void clearPowerFailures() {
		this.powerGayManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
		
		this.powerGayManager.clearPowerFailures();
		
		assertFalse(square.getPower().isFailing());
	}
	
	@Test
	public void decreaseTurn() {
		this.powerGayManager.powerFailSquare(square);
		
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS);
		assertEquals(square.getPower().getRemainingActions(), PrimaryPowerFail.ACTIONS);
		
		this.powerGayManager.decreaseTurn();
		
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS - 1);
	}
	
	@Test
	public void decreaseTurns() {
		this.powerGayManager.powerFailSquare(square);
		
		assertTrue(square.getPower().isFailing());
		assertEquals(square.getPower().getRemainingTurns(), PrimaryPowerFail.TURNS);
		
		this.powerGayManager.decreaseTurn();
		this.powerGayManager.decreaseTurn();
		
		assertTrue(square.getPower().isFailing());
		
		this.powerGayManager.decreaseTurn();
		
		assertFalse(square.getPower().isFailing());
	}
	
	@Test
	public void decreaseAction() {
		this.powerGayManager.powerFailSquare(square);
		
		Power secondary = square.getPower().getChild();
		Power tertiary = secondary.getChild();
		
		assertEquals(secondary.getRemainingActions(), SecondaryPowerFail.ACTIONS);
		assertEquals(tertiary.getRemainingActions(), TertiaryPowerFail.ACTIONS);
		
		this.powerGayManager.decreaseAction();
		
		assertEquals(secondary.getRemainingActions(), SecondaryPowerFail.ACTIONS - 1);
		assertEquals(tertiary.getRemainingActions(), TertiaryPowerFail.ACTIONS); // (because it is reset)
	}
	
	/**
	 * Rotate secondary after each two actions.
	 */
	@Test
	public void rotationOfSecondary() {
		this.powerGayManager.powerFailSquare(square);
		
		Power secondary = square.getPower().getChild();
		
		assertTrue(secondary.getDirection() != null);
		
		Direction direction = secondary.getDirection();
		
		this.powerGayManager.decreaseAction();
		
		assertTrue(secondary.getDirection() == direction);
		assertEquals(secondary.getDirection(), direction);
		
		this.powerGayManager.decreaseAction();
		
		assertTrue(secondary.getDirection() != direction);
		assertEquals(secondary.getDirection(), secondary.getRotation().rotate(direction));
	}
	
	@Test
	public void creationOfSecondary() {
		this.powerGayManager.powerFailSquare(square);
		
		Power primary = square.getPower();
		Power secondary = primary.getChild();
		
		Square secondarySquare = powerGayManager.getGridElement(secondary);
		
		assertTrue(grid.getNeighbors(square).containsValue(secondarySquare));
	}
	
	/**
	 * If tertiary and secondary are located on a square.
	 * The tertiary square should not be adjacent to the primary.
	 */
	@Test
	public void creationOfTertiary() {
		this.powerGayManager.powerFailSquare(square);
		
		Power primary = square.getPower();
		Power secondary = primary.getChild();
		Power tertiary = secondary.getChild();
		
		Square secondarySquare = powerGayManager.getGridElement(secondary);
		Square tertiarySquare = powerGayManager.getGridElement(tertiary);
		
		assertFalse(grid.getNeighbors(square).containsValue(tertiarySquare));
		assertTrue(grid.getNeighbors(secondarySquare).containsValue(tertiarySquare));
	}

}
