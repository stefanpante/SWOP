package move;

import grid.Grid;
import item.Teleport;

import java.util.HashSet;
import java.util.NoSuchElementException;

import square.Direction;
import square.Square;


/**
 * A class functioning as a mediator between a grid and 
 * another class that needs a trajectory calculated.
 * 
 * @author Dieter
 */
public class MoveMediator {
	/**
	 * The grid of this TracjectoryMediator.
	 */
	private final Grid grid;
	
	/**
	 * Constructs a TrajectoryMediator with a given grid.
	 * @param 	grid
	 * 			The grid of this TrajectoryMediator.
	 */
	public MoveMediator(Grid grid){
		this.grid = grid;
	}
	
	/**
	 * Returns the last possible square an object can 
	 * 	move to in the given direction with the given maximum range.
	 * 	The method takes obstacles into account.
	 * 	A MultiObstacle makes the result end just before the obstacle.
	 * 	Another obstacle makes the result end on the obstacle.
	 * 
	 * @param 	startSquare
	 * 			The square on which the trajectory starts.
	 * @param 	direction
	 * 			The direction of the trajectory.
	 * @param 	maximumRange
	 * 			The maximum range of this trajectory.
	 * @return
	 */
	public Square getEndSquare(Square startSquare, Direction direction, int maximumRange){
		Square prevSquare = null;
		Square currentSquare = startSquare;
		HashSet<Teleport> passedDestinations = new HashSet<Teleport>();
		Teleport teleport = null, destination = null;
		Square destinationSquare = null;
		boolean prevWasTeleport = false;
		int currentRange = 0;
		do{
			prevSquare = currentSquare;
			teleport = prevSquare.getInventory().getTeleport();
			if(maximumRange > 0 && currentRange < maximumRange) {
				try {
					if(teleport!= null && !prevWasTeleport){
						destination = teleport.getDestination();
						if(passedDestinations.contains(destination)){
							return prevSquare;
						}
						destinationSquare = grid.findSquare(destination);
						passedDestinations.add(destination);
						currentSquare = destinationSquare;
						prevWasTeleport = true;
					} else {
						currentSquare = grid.getNeighbor(prevSquare, direction);
						prevWasTeleport = false;
					}
				} catch (NoSuchElementException e) {
					return prevSquare;
				} catch (IllegalArgumentException e){
					return prevSquare;
				}
				currentRange++;
			}
			if(prevSquare.getPower().isFailing()){//Afterwards a check because and ID can always move
				maximumRange--;
			}
		} while(!currentSquare.isObstructed() && currentRange < maximumRange);
		if(currentSquare.isObstructed() && currentSquare.getObstacle().bouncesBack()){
			return prevSquare;
		}
		return currentSquare;
	}
}
