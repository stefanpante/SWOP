package controller;

import command.AbstractGameCommand;
import command.action.PickUpCommand;
import game.Game;
import item.Item;

import java.beans.PropertyChangeListener;

/**
 * Handler/Controller for the pick up use case
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class PickUpHandler extends Handler {
	
	/**
	 * Constructs a new PickUpHandler with the given game and given listener.
	 * @param game		the game which this PickUpHandler will use.
	 * @param listener	the PropertyChangeListener which this PickUpHandler will use.
	 */
	public PickUpHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}



	/**
	 * The player pickups the item he wants if his inventory allows it,
	 * otherwise he will not receive the item and will lose an action.
	 * @param item	the item which will be picked up.
	 */
	public void pickUp(Item item) {
		fireChanges();
		AbstractGameCommand pickUpEvent = new PickUpCommand(getGame(), item);
		pickUpEvent.execute();
		firePropertyChange(Handler.MESSAGE_PROPERTY, "Picked up a "+ item);
		fireChanges();
	}
}
