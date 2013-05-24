package controller;

import command.AbstractGameCommand;
import command.action.UseItemCommand;
import game.Game;
import item.Item;

import java.beans.PropertyChangeListener;

/**
 * Handler/Controller for the use case in which 
 * an item is used
 * w
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
	public void useItem(Item item) throws Exception {
		fireChanges();
		AbstractGameCommand useItemEvent = new UseItemCommand(getGame(), item);
		useItemEvent.execute();
		firePropertyChange(Handler.MESSAGE_PROPERTY, "Used a "+ item);
		fireChanges();
	}
}
