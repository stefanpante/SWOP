package handlers;

import game.Game;
import grid.TrajectoryMediator;

import items.Item;
import items.Launchable;

import java.beans.PropertyChangeListener;

import square.Direction;
import square.Square;

public class ThrowLaunchableHandler extends Handler {

	/**
	 * The TrajectoryMediator which resolves the path of the launchable.
	 */
	TrajectoryMediator trajectoryMediator;
	
	/**
	 * Creates an new ThrowLaunchableHandler with a given game instance.
	 * @param game	The given game instance
	 */
	public ThrowLaunchableHandler(Game game) {
		super(game);
		trajectoryMediator = new TrajectoryMediator(game.getGrid());
	}

	/**
	 * Creates a new ThrowLaunchableHandler with a given game instance.
	 * @param game		The game instance.
	 * @param listener	the	PropertyChangeListener for this handler.
	 */
	public ThrowLaunchableHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
		trajectoryMediator = new TrajectoryMediator(game.getGrid());
	}
	
	/**
	 * Throws a launchable in the given direction.
	 * @param launchable	the launchable to be thrown
	 * @param direction		the direction in which the launchable is thrown.
	 */
	public void throwLaunchable(Launchable launchable, Direction direction){
		Square startSquare = getGame().getCurrentPlayer().getPosition();
		int maximumRange = 	launchable.getRange();
		Square endSquare = trajectoryMediator.getEndSquare(startSquare, direction, maximumRange);
		//TODO: check if player is hit in mediator 
		endSquare.getInventory().addItem(launchable);
	}

}
