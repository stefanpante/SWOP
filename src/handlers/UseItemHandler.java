package handlers;

import items.Inventory;

import items.Item;

import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

/**
 * Handler/Controller for the use case in which 
 * an item is used
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class UseItemHandler extends Handler {

	private Game game;

	
	/** 
	 * Creates the handler for the usage of an item
	 * @param game
	 */
	public UseItemHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * Checks the precondition for the use item case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(game.getCurrentPlayer().getRemainingActions() > 1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Uses the given item for the player. Exceptions are handled when
	 * the player uses an item that isn't in his inventory. (IllegalArgument)
	 * Or when the player cannot use the item on the square.
	 */
	public void useItem(Item item) {
		try{
			game.getCurrentPlayer().useItem(item);
		} catch(IllegalStateException e) {
			System.err.println("Item is not in player his inventory:" + item);
		} catch(IllegalArgumentException e) {
			System.err.println("Item cannot be used on the square.");
		}
	}
	
	
	public Inventory showItems(){
		return game.getCurrentPlayer().getInventory();
	}


}
