package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import square.Square;
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
	 * Sets the state of any square to a PowerFailure state with a 5% chance.
	 */
	public void powerFailSquares() {
		Iterator<Square> iterator = getGrid().getAllSquares().iterator();
		Random random = new Random();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			if(random.nextFloat() <= CHANCE_POWERFAILURE){
				square.setPower(new PrimaryPowerFail());
				ArrayList<Square> neighbors = getGrid().getNeighborsAsList(square);
				for(Square s: neighbors){
					s.setPower(new PrimaryPowerFail());
				}
			}
		}
		
		// Exclude starting positions
		Square bottomLeft = getGrid().getSquare(new Coordinate(0, getGrid().getVSize()-1));
		Square topRight = getGrid().getSquare(new Coordinate(getGrid().getHSize()-1, 0));
		bottomLeft.setPower(new RegularPower());
		topRight.setPower(new RegularPower());
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
		//for(Square square : getGrid().getAllSquares())
			//square.getPower().decreaseAction();
	}

}
