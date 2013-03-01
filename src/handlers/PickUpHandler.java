package handlers;

import items.Inventory;
import items.Item;

import java.io.IOException;
import java.io.InputStreamReader;

import game.Game;

/**
 * Handler/Controller for the pick up use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class PickUpHandler extends Handler {

	private Game game;
	
	public PickUpHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * Checks the precondition for the pick up use case
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
	 * The player pickups the item he wants if his inventory allows it,
	 * otherwise he will not receive the item and will lose an action.
	 * @param item
	 */
	public void pickUp(Item item){
		try{
			game.getCurrentPlayer().getPosition().getInventory().take(item);
			if(!game.getCurrentPlayer().getInventory().isFull()){
				game.getCurrentPlayer().pickUp(item);
				game.getCurrentPlayer().incrementActions();
			}
		} catch(IllegalStateException e) {
			System.err.println("The item cannot be removed because it is not in the square inventory.");
		} catch(IllegalArgumentException e) {
			System.err.println("Picking item resulted in overfull player inventory.");
		}
	}
	
	public Inventory showItems(){
		return game.getCurrentPlayer().getPosition().getInventory();
	}

}
