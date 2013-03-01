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
	 * 
	 */
	public void useItem(Item item) {
		game.getCurrentPlayer().useItem(item);
	}
	
	//TODO: if the player doesn't have any items in his inventory?
	public void showItems(){
		Inventory inventory = game.getCurrentPlayer().getInventory();
		GuiHandler.updateListModel(inventory);
	}

}
