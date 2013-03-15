package handlers;

import java.beans.PropertyChangeListener;

import player.Player;

import utils.Coordinate;

import game.Game;
import gui.ApplicationWindow;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler extends Handler{
	
	boolean confirmed = false;
	
	/**
	 * 
	
	 * @param game
	 * @param listener
	 */
	public EndTurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	public void resetConfirm(){
		confirmed = false;
	}
	
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
	 * 			If 
	 */
	public void endTurn() throws IllegalStateException{
		if(!isConfirmed()){
			firePropertyChange(GameHandler.END_TURN_PROPERTY, "Do you want to confirm ending your turn?");
		}else{
			int penalty = getGame().getCurrentPlayer().getPosition().getPenalty();
		
			getGame().getCurrentPlayer().endTurn(Player.MAX_ALLOWED_ACTIONS  + penalty);
			getGame().switchToNextPlayer();
	    	firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
			
			resetConfirm();
		}
		fireChanges();
	}

	/**
	 * @param b
	 */
	public void confirm(boolean b) {
		this.confirmed = b;
		endTurn();
	}

}
