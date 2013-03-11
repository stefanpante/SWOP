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
	
	public PickUpHandler(Game game) {
		super(game);
	}
	
	/**
	 * Checks the precondition for the pick up use case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 1){
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
			getGame().getCurrentPlayer().getPosition().getInventory().take(item);
			if(!getGame().getCurrentPlayer().getInventory().isFull()){
				getGame().getCurrentPlayer().pickUp(item);
				getGame().getCurrentPlayer().incrementActions();
			}
		} catch(IllegalStateException e) {
			System.err.println("The item cannot be removed because it is not in the square inventory.");
		} catch(IllegalArgumentException e) {
			System.err.println("Picking item resulted in overfull player inventory.");
		}
	}
	
	public Inventory showItems(){
		return getGame().getCurrentPlayer().getPosition().getInventory();
	}

}
