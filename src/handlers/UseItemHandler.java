package handlers;

import items.Inventory;

import items.Item;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;
import gui.ApplicationWindow;

/**
 * Handler/Controller for the use case in which 
 * an item is used
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class UseItemHandler extends Handler {
	
	
	/**
	 * @param game
	 * @param window
	 */
	public UseItemHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}

	/**
	 * Checks the precondition for the use item case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 1){
			return true; 
		}
		else return false;
	}
	
	/**
	 * Uses the given item for the player. Exceptions are handled when
	 * the player uses an item that isn't in his inventory. (IllegalArgument)
	 * Or when the player cannot use the item on the square.
	 */
	public void useItem(Item item) {
		try{
			getGame().getCurrentPlayer().useItem(item);
		} catch(IllegalStateException e) {
			System.err.println("Item is not in player his inventory:" + item);
		} catch(IllegalArgumentException e) {
			System.err.println("Item cannot be used on the square.");
		}
	}
	
	
	public Inventory showItems(){
		return getGame().getCurrentPlayer().getInventory();
	}


}
