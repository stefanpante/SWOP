package controller;

import event.AbstractGameEvent;
import event.action.ThrowLaunchableEvent;
import game.Game;

import item.launchable.LaunchableItem;

import java.beans.PropertyChangeListener;

import square.Direction;

/**
 * Handler for the throwing a launchable.
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class ThrowLaunchableHandler extends Handler {


	/**
	 * Constructs a new launchableHandler
	 * @param game		the game instance which will be used.
	 * @param listener	the PropertyChangeHandler used by this handler.
	 */
	public ThrowLaunchableHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	public void throwLaunchable(LaunchableItem launchableItem, Direction direction){
		fireChanges();
		AbstractGameEvent throwLaunchableEvent = new ThrowLaunchableEvent(getGame(), launchableItem, direction);
		throwLaunchableEvent.run();
		fireChanges();
	}

}
