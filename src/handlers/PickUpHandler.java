package handlers;

import items.Inventory;

import items.Item;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import square.Square;
import utils.Coordinate;

import game.Game;

/**
 * Handler/Controller for the pick up use case
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PickUpHandler extends Handler {
	
	/**
	 * @param game
	 * @param window
	 */
	public PickUpHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}


	/**
	 * Checks the precondition for the pick up use case.
	 * 
	 * @return 	True	If the precondition is satisfied
	 * 			False	Otherwise.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 1 )
			return true;
		
		return false;
	}
	
	/**
	 * The player pickups the item he wants if his inventory allows it,
	 * otherwise he will not receive the item and will lose an action.
	 * @param item
	 */
	public void pickUp(Item item){
		if(!checkToProceed()){
			if(!getGame().getCurrentPlayer().hasMoved()){
			String name = getGame().getCurrentPlayer().getName();
			throw new IllegalStateException(name +" hasn't moved in this turn " +
					"and has no actions left" + name + "has lost the game" );
			}
			else{
				getGame().getCurrentPlayer().endTurn(); //TODO: depends on powerfailure
				getGame().switchToNextPlayer();
			}
		}
		else{
			getGame().getCurrentPlayer().pickUp(item);
			getGame().getCurrentPlayer().getPosition().getInventory().take(item);
			getGame().getCurrentPlayer().decrementActions();
			endAction();
		}
	}
	
	/**
	 * Shows all the items that are on the current player's position.
	 * 
	 * @return	Inventory	The square inventory of the current player's position.
	 */
	public Inventory showItems(){
		return getGame().getCurrentPlayer().getPosition().getInventory();
	}

}
