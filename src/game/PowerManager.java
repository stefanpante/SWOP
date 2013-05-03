package game;

import java.util.ArrayList;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import square.Direction;
import square.Square;
import square.power.Power;
import grid.Grid;

/**
 * Manages all the power failures in the game.
 * 
 * This includes randomly activating primary power failures.
 * Making sure that secondary and tertiary power failures rotate.
 * 
 * 1) DecreaseTurn:
 * 		Only primary failures will throw exceptions here.
 * 		If an exception is catched, we know the power failure is at end of life.
 * 		We remove itself and its children.
 * 2) DecreaseAction:
 * 		Only secondary and tertiary powerfailures are afflicted by decrease in actions.
 * 		If an exception is catched, the distinction can be made if the power has a child.
 * 		Secondaries will rotate in a direction, and the tertiaries will move with them.
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
		Square bottomLeft = getGrid().getStartPlayerOne();
		Square topRight = getGrid().getStartPlayerTwo();
		bottomLeft.setPower(Power.getRegularPower());
		topRight.setPower(Power.getRegularPower());
	}

	/**
	 * We power fail one square and set two squares near it
	 * as a secondary and tertiary power fail.
	 * 
	 * @param square
	 */
	protected void powerFailSquare(Square square) {
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
			Square tertiary = getCandidateTertiary(square, neighbor);
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
	 * @param	square
	 * @param	neighbor
	 * @throws	IllegalArgumentException
	 * 			If the given squares are no neighbors.
	 * @throws	NoSuchElementException
	 * 			If no suitable candidate can be found, only off-grid.
	 * @return
	 */
	private Square getCandidateTertiary(Square square, Square neighbor) throws IllegalArgumentException, NoSuchElementException {
		Direction directionNeighbor = getGrid().getNeighborDirection(square, neighbor);
		
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
			
			if(powers.contains(square.getPower()))
				square.setPower(Power.getRegularPower());
		}
	}
	
	/**
	 * Gets the square associated with the power.
	 * 
	 * @param	power
	 * @return	Square	If the power can be found on a square.
	 * 			Null	If there is no square with the power.
	 */
	protected Square getSquare(Power power) {
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
		ArrayList<Power> usedFailures = new ArrayList<Power>();
		
		for(Power power: primaryPowerFailures) {
			try {
				power.decreaseTurn();		
				
			} catch (IllegalStateException exc) {
				ArrayList<Power> powers = power.getChildren();
				powers.add(power);
				
				clearPowers(powers);
				usedFailures.add(power);
			}
		}
		
		primaryPowerFailures.removeAll(usedFailures);
	}

	/**
	 * Decreases the action of a power failure.
	 * 
	 * Actions only affect on secondary and tertiary.
	 * Primary will only throw exceptions on a decrease in turn.
	 * 
	 * We can make the distinction based on wether the power has a child or not.
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
	 * Then we need to set it's tertiary power to a new square as well.
	 * 
	 * @param power
	 */
	private void resetSecondary(Power power) {
		power.resetCount();
		
		Direction newDirection = power.getRotation().rotate(power.getDirection());
		power.setDirection(newDirection);
		
		Square primary = getSquare(power.getParent());
		Square oldSquare = getSquare(power);
		
		if(oldSquare != null)
			oldSquare.setPower(Power.getRegularPower());
		
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
		Square oldSquare = getSquare(power);
		
		if(oldSquare != null)
			oldSquare.setPower(Power.getRegularPower());
		
		try{			
			Square secondary = getSquare(power.getParent());
			
			if(secondary != null) {	
				Square tertiary = getCandidateTertiary(primary, secondary);
				
				if(tertiary.getPower().isFailing() && power.isLifeSpanLonger(tertiary.getPower()) || !tertiary.getPower().isFailing())
					tertiary.setPower(power);
			}
		} catch (NoSuchElementException exc) {
			// Secondary does not have a square.
			// or off-grid for tertiary.
		}
	}

}
