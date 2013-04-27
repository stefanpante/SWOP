package game;

import static org.junit.Assert.*;
import grid.Grid;
import grid.GridProvider;

import org.junit.Before;
import org.junit.Test;

import square.Square;
import square.power.Power;
import square.power.failure.PrimaryPowerFail;
import util.Coordinate;

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
	public void decreaseAction() {
		this.powerManager.powerFailSquare(square);
		
		Power secondary = square.getPower().getChild();
		Power tertiary = square.getPower().getChild();
	}
	
	@Test
	public void creationOfSecondary() {
		
	}
	
	@Test
	public void creationOfTertiary() {
		
	}
	
	@Test
	public void rotationOfSecondary() {
		
	}

}
