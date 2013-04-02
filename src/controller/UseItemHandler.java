package controller;

import item.Item;

import java.beans.PropertyChangeListener;
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
	 * Checks the precondition for the use item case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 1 )
			return true;
		return false;
	}
	
	/**
	 * Uses the given item for the player. Exceptions are handled when
	 * the player uses an item that isn't in his inventory. (IllegalArgument)
	 * Or when the player cannot use the item on the square.
	 */
	public void useItem(Item item) {
		startAction();
		if(!checkToProceed()){
			if(!getGame().getCurrentPlayer().hasMoved()){
				String name = getGame().getCurrentPlayer().getName();
				throw new IllegalStateException(name +" hasn't moved in this turn " +
						"and has no actions left" + name + "has lost the game" );
			}else{
				getGame().getCurrentPlayer().endTurn(); //TODO: depends on powerfailure
				getGame().switchToNextPlayer();
		    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
			}
		}
		else{
			getGame().getCurrentPlayer().useItem(item);
			//getGame().getCurrentPlayer().getPosition().getInventory().activate(item);
			getGame().getCurrentPlayer().decrementActions();
			firePropertyChange(GameHandler.MESSAGE_PROPERTY, "Used a "+ item);
		}
		endAction();
	}
}