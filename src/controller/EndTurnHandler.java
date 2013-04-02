package controller;

import java.beans.PropertyChangeListener;

import player.Player;


import game.Game;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler extends Handler{
	
	boolean confirmed = false;
	
	/**
	 *  Creates a new EndTurnhandler with a given game and listener.
	 * @param game		the game which this handler uses.
	 * @param listener	the listener for this handler.
	 */
	public EndTurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	/**
	 * resets the confirm ( used because the player needs to confirm he wants to end his turn
	 */
	public void resetConfirm(){
		confirmed = false;
	}
	
	/**
	 * Returns whether the player has confirmed.
	 */
	public boolean isConfirmed(){
		return confirmed;
	}
	
	
	/**
	 * 
	 * Checks the precondition for the end turn use case
	 * returns true if the preconditions are satisfied.
	 * 
	 * @return 	true if the precondition is satisfied
	 * 			false otherwise.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 0)
			return true;
		return false;
	}

	/**
	 * Check to see if the player hasMoved before ending the turn.
	 * Can be used to warn the player that he needs to move.
	 * @return
	 */
	public boolean hasMoved(){
		return getGame().getCurrentPlayer().hasMoved();
	}
	
	
	
	/**
	 * Ends the turn of the current player and 
	 * sets up the game for the turn of the next player
	 * 
	 * @throws	IllegalStateException
	 */
	public void endTurn() throws IllegalStateException{
		startAction();
		if(!isConfirmed()){
			firePropertyChange(GameHandler.END_TURN_PROPERTY, "Do you want to confirm ending your turn?");
		}else{
			int penalty = getGame().getCurrentPlayer().getPosition().getPenalty();
		
			System.out.println(penalty);
			getGame().getCurrentPlayer().endTurn(Player.MAX_ALLOWED_ACTIONS  + penalty);
			getGame().switchToNextPlayer();
	    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
			
			resetConfirm();
		}
		endAction();
	}

	/**
	 * sets the confirm.
	 * @param 	bool 
	 * 			A boolean which sets the confirm.
	 * @effect	endTurn()
	 * @post	bool = isConfirmed()
	 */
	public void confirm(boolean bool) {
		this.confirmed = bool;
		endTurn();
	}

}