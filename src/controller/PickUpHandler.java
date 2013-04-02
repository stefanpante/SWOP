package controller;

import item.Item;

import java.beans.PropertyChangeListener;

import effect.EffectValue;

import player.Player;
import game.Game;

/**
 * Handler/Controller for the pick up use case
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
	 * Checks the precondition for the pick up use case.
	 * 
	 * @return 	True	If the precondition is satisfied
	 * 			False	Otherwise.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 0 )
			return true;
		
		return false;
	}
	
	/**
	 * The player pickups the item he wants if his inventory allows it,
	 * otherwise he will not receive the item and will lose an action.
	 * @param item	the item which will be picked up.
	 */
	public void pickUp(Item item){
		startAction();
		if(!checkToProceed()){
			if(!getGame().getCurrentPlayer().hasMoved()){
				String name = getGame().getCurrentPlayer().getName();
				throw new IllegalStateException(name +" hasn't moved in this turn " +
						"and has no actions left" + name + "has lost the game" );
			}else{
				firePropertyChange(GameHandler.MESSAGE_PROPERTY, "Could not proceed: Move is over.");
				EffectValue penaltyValue = getGame().getCurrentPlayer().getPosition().getEffectDuringAction();
				getGame().getCurrentPlayer().endTurn(penaltyValue); //TODO: depends on powerfailure
				getGame().switchToNextPlayer();
		    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
			}
		}
		else{
			getGame().getCurrentPlayer().pickUp(item);
			getGame().getCurrentPlayer().getPosition().getInventory().take(item);
			
			firePropertyChange(GameHandler.MESSAGE_PROPERTY, "Picked up a "+ item);
		}
		endAction();
	}
}
