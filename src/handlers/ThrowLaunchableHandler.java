package handlers;

import game.Game;

import items.Item;
import items.Launchable;

import java.beans.PropertyChangeListener;

import square.Direction;

public class ThrowLaunchableHandler extends Handler {

	/**
	 * The TrajectoryMediator which resolves the path of the launchable.
	 */
	TrajectoryMediator trajectoryMediator;
	
	public ThrowLaunchableHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates an new ThrowLaunchableHandler with a given game instance.
	 * @param game	The given game instance
	 */
	public ThrowLaunchableHandler(Game game) {
		super(game);
	}

	/**
	 * Creates a new ThrowLaunchableHandler with a given game instance.
	 * @param game		The game instance.
	 * @param listener	the	PropertyChangeListener for this handler.
	 */
	public ThrowLaunchableHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	/**
	 * Throws a launchable in the given direction.
	 * @param launchable	the launchable to be thrown
	 * @param direction		the direction in which the launchable is thrown.
	 */
	public void throwLaunchable(Launchable launchable, Direction direction){
		
	}

}
