package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import square.Direction;
import square.Square;
import square.power.Power;
import square.power.RegularPower;
import square.power.failure.PrimaryPowerFail;
import util.Coordinate;
import grid.Grid;

/**
 * Manages all the power failures in the game.
 * 
 * This includes randomly activating primary power failures.
 * Making sure that secondary and tertiary power failures rotate.
 * 
 * @author vincent
 */
public class PowerManager {
	
	/**
	 * The grid the power manager is working on.
	 */
	private Grid grid;
	
	/**
	 * The possibility of a power failure in a square.
	 */
	private final float CHANCE_POWERFAILURE = 0.01f;
	
	/**
	 * Creates a power manager with a reference to the grid.
	 * 
	 * @param grid
	 */
	public PowerManager(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the grid.
	 */
	public Grid getGrid() {
		return this.grid;
	}
	
	/**
	 * Sets the state of any square to a PowerFailure state with a 1% chance.
	 */
	public void powerFailSquares() {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		Random random = new Random();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			
			if(random.nextFloat() <= CHANCE_POWERFAILURE){
				powerFailSquare(square);
			}
		}
		
		// Exclude starting positions
		Square bottomLeft = getGrid().getSquare(new Coordinate(0, getGrid().getVSize()-1));
		Square topRight = getGrid().getSquare(new Coordinate(getGrid().getHSize()-1, 0));
		bottomLeft.setPower(new RegularPower());
		topRight.setPower(new RegularPower());
	}

	/**
	 * We power fail one square and set two squares near it
	 * as a secondary and tertiary power fail.
	 * 
	 * @param square
	 */
	private void powerFailSquare(Square square) {
		Random random = new Random();
		
		Power primaryFail = new PrimaryPowerFail();
		Power secondaryFail = primaryFail.getChild();
		Power tertiaryFail = secondaryFail.getChild();
		
		// Set primary
		square.setPower(primaryFail);
		ArrayList<Square> neighbors = getGrid().getNeighborsAsList(square);
		
		// Set secondary
		Square neighbor = neighbors.get(random.nextInt(neighbors.size()));
		neighbor.setPower(secondaryFail);
		
		// Set tertiary
		Square tertiary = getNeighborTertiary(square, neighbor);
		tertiary.setPower(tertiaryFail);
	}
	
	/**
	 * We need to find a square that is suitable as a tertiary power failure.
	 * This square must be according to these constraints:
	 * 
	 * 1) The square may not be adjacent to F1
	 * 2) The square must be adjacent to F2
	 * 
	 * @param square
	 * @param neighbor
	 * @return
	 */
	private Square getNeighborTertiary(Square square, Square neighbor) throws IllegalArgumentException {
		Direction directionNeighbor = null;
		
		for (Direction direction: getGrid().getNeighbors(square).keySet()) {
			if(getGrid().getNeighbor(square, direction) == neighbor)
				directionNeighbor = direction;
		}
		
		if(directionNeighbor == null)
			throw new IllegalArgumentException("Invalid arguments given, these are not neighbors.");
		
		ArrayList<Direction> directions = directionNeighbor.neighborDirections();
		directions.add(directionNeighbor);
		
		Random random = new Random();
		Direction direction = directions.get(random.nextInt(directions.size()));
		
		Square tertiary = getGrid().getNeighbor(neighbor, direction);
		
		return tertiary;
	}

	/**
	 * Clears all powerFailures, for testing purposes.
	 */
	public void clearPowerFailures(){
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.setPower(new RegularPower());
		}
	}
	
	/**
	 * Removes the powers from the grid.
	 * 
	 * @param powers
	 */
	private void clearPowers(ArrayList<Power> powers) {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			
			if(powers.contains(square.getPower()));
				square.setPower(new RegularPower());
		}
	}
	
	/**
	 * Finds the parent square for a given power.
	 * 
	 * @param power
	 * @return
	 */
	private Square getParentPower(Power power) {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			
			if(square.getPower().hasChild())
				if(square.getPower().getChild() == power)
					return square;
		}
		
		return null;
	}
	
	private Square getSquare(Power power) {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			
			if(square.getPower() == power)
				return square;
		}
		
		return null;
	}
	
	/**
	 * Updates the power failures with the end of a turn.
	 */
	public void decreaseTurn() {
		for(Square square : getGrid().getAllSquares())
			square.getPower().decreaseTurn();
	}

	/**
	 * Decreases the action of a power failure.
	 */
	public void decreaseAction() {
		for(Square square : getGrid().getAllSquares()) {
			Power power = square.getPower();
			try{
				power.decreaseAction();
			} catch (IllegalStateException exc) {
				// Secondary
				if(power.hasChild()) {
					
				} else {
					
				}
			}
		}
	}

}
