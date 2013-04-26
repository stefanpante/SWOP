package game;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import square.Direction;
import square.Square;
import square.power.Power;
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
	private final Grid grid;
	
	/**
	 * The possibility of a power failure in a square.
	 */
	private final float CHANCE_POWERFAILURE = 0.01f;
	
	/**
	 * Primary power failures.
	 */
	private final ArrayList<Power> primaryPowerFailures = new ArrayList<Power>();
	
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
	//TODO: Check if power fail twice on a square in row.
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
		bottomLeft.setPower(Power.getRegularPower());
		topRight.setPower(Power.getRegularPower());
	}

	/**
	 * We power fail one square and set two squares near it
	 * as a secondary and tertiary power fail.
	 * 
	 * @param square
	 */
	private void powerFailSquare(Square square) {
		Random random = new Random();
		
		final Power primaryFail = Power.getPowerFailure();
		final Power secondaryFail = primaryFail.getChild();
		final Power tertiaryFail = secondaryFail.getChild();
		
		primaryPowerFailures.add(primaryFail);
		
		// Set primary
		square.setPower(primaryFail);
		ArrayList<Square> neighbors = getGrid().getNeighborsAsList(square);
		
		// Set secondary
		Square neighbor = neighbors.get(random.nextInt(neighbors.size()));
		Direction neighborDirection = getGrid().getNeighborDirection(square, neighbor);
		
		neighbor.setPower(secondaryFail);
		secondaryFail.setDirection(neighborDirection);
		
		// Set tertiary
		try{
			Square tertiary = getNeighborTertiary(square, neighbor);
			tertiary.setPower(tertiaryFail);
		} catch(NoSuchElementException exc) {
			// Square off-grid
		}
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
	private Square getNeighborTertiary(Square square, Square neighbor) throws IllegalArgumentException, NoSuchElementException {
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
		
		final Square tertiary = getGrid().getNeighbor(neighbor, direction);
		
		return tertiary;
	}

	/**
	 * Clears all powerFailures, for testing purposes.
	 */
	public void clearPowerFailures(){
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.setPower(Power.getRegularPower());
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
				square.setPower(Power.getRegularPower());
		}
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
	 * 
	 * If an exception is catched, it is because a primary power failure has
	 * ended. Thus itself and its children will be removed from the grid.
	 */	
	public void decreaseTurn() {
		for(Power power: primaryPowerFailures) {
			try {
				power.decreaseTurn();
			} catch (IllegalStateException exc) {
				ArrayList<Power> powers = power.getChildren();
				powers.add(power);
				
				clearPowers(powers);
			}
		}
	}

	/**
	 * Decreases the action of a power failure.
	 */
	public void decreaseAction() {
		for(Power power: primaryPowerFailures) {

			while(power.hasChild()) {
				power = power.getChild();
				
				try{
					power.decreaseAction();
				} catch (IllegalStateException exc) {					
					resetPower(power);
					
					break;
				}
			}			
		}
	}
	
	
	/**
	 * Resets the power and his children, if they have a rotation
	 * we move them in the direction.
	 * 
	 * If there is a child it must be in line in a certain manner (tertiary).
	 * 
	 * @param powers
	 */
	private void resetPower(Power power) {
		if(power.hasChild()) {
			resetSecondary(power);
		} else {
			resetTertiary(power);
		}
	}
	
	/**
	 * When resetting a secondary power, we need to move it into
	 * a new direction.
	 * 
	 * Then we need to set it's tertiary power to a new square aswell.
	 * 
	 * @param power
	 */
	private void resetSecondary(Power power) {
		power.resetCount();
		power.getRotation().rotate(power.getDirection());
		
		Square primary = getSquare(power.getParent());
		
		try {
			Square secondary = getGrid().getNeighbor(primary, power.getDirection());
			
			if(secondary.getPower().isFailing() && power.isLifeSpanLonger(secondary.getPower()) || !secondary.getPower().isFailing())
				secondary.setPower(power);
			
			resetTertiary(power.getChild());
		} catch (NoSuchElementException exc) {
			// Square is off-grid.
		}
	}
	
	/**
	 * When resetting a tertiary power, we need to determine the
	 * primary and secondary location and then take a random pick from
	 * the possible squares.
	 * 
	 * @param power
	 */
	private void resetTertiary(Power power) {
		power.resetCount();
		
		Square primary = getSquare(power.getParent().getParent());
		
		try{
			Square secondary = getSquare(power.getParent());
			Square tertiary = getNeighborTertiary(primary, secondary);
			
			if(tertiary.getPower().isFailing() && power.isLifeSpanLonger(tertiary.getPower()) || !tertiary.getPower().isFailing())
				tertiary.setPower(power);			
		} catch (NoSuchElementException exc) {
			// Secondary does not have a square.
			// or off-grid for tertiary.
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
			
			if(square.getPower() == power.getParent())
				return square;
		}
		
		return null;
	}
	

}
