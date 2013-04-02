package grid;

import java.util.NoSuchElementException;

import square.Direction;
import square.Square;


/**
 * A class functioning as a mediator between a grid and 
 * another class that needs a trajectory calculated
 */
public class TrajectoryMediator {
	/**
	 * The grid of this TracjectoryMediator.
	 */
	private final Grid grid;
	
	/**
	 * Constructs a TrajectoryMediator with a given grid.
	 * @param 	grid
	 * 			The grid of this TrajectoryMediator.
	 */
	public TrajectoryMediator(Grid grid){
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
		Square prevSquare = startSquare;
		Square currentSquare;
		int currentRange = 0;
		//TODO implement powerfailure and teleport
		try{
			currentSquare = grid.getNeighbor(prevSquare, direction);
		} catch(NoSuchElementException e){
			return startSquare;
		}
		while(!currentSquare.isObstructed() && currentRange < maximumRange){
			prevSquare = currentSquare;
			try {
				currentSquare = grid.getNeighbor(prevSquare, direction);
			} catch (NoSuchElementException e) {
				return prevSquare;
			}
			currentRange++;
		}
		if(currentSquare.getObstacle().bouncesBack()){
			return prevSquare;
		}
		return currentSquare;
	}
}
