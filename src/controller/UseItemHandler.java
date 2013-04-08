package controller;

import item.Item;

import java.beans.PropertyChangeListener;

import event.AbstractGameEvent;
import event.action.MoveEvent;
import event.action.UseItemEvent;
import game.Game;

/**
 * Handler/Controller for the use case in which 
 * an item is used
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class UseItemHandler extends Handler {
	
	/**
	 * Constructs a new UseItemHandler with the given game and given listener.
	 * @param game		The game which this UseItemHandler will use.
	 * @param listener	The PropertyChangelistener for this UseItemHandler.
	 */
	public UseItemHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	/**
	 * Uses the given item for the player. Exceptions are handled when
	 * the player uses an item that isn't in his inventory. (IllegalArgument)
	 * Or when the player cannot use the item on the square.
	 */
	public void useItem(Item item) {
		fireChanges();
		AbstractGameEvent useItemEvent = new UseItemEvent(getGame(), item);
		useItemEvent.run();
		firePropertyChange(GameHandler.MESSAGE_PROPERTY, "Used a "+ item);
		fireChanges();
	}
}
