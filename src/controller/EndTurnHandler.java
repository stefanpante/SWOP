package controller;

import command.AbstractGameCommand;
import command.action.EndTurnCommand;
import game.Game;

import java.beans.PropertyChangeListener;

/**
 * The Controller/Handler to end the turn of a player
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class EndTurnHandler extends Handler {
	
	private boolean confirmed = false;
	
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
    void resetConfirm(){
		confirmed = false;
	}
	
	/**
	 * Returns whether the player has confirmed.
	 */
    boolean isConfirmed(){
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
        return getGame().getCurrentPlayer().getRemainingActions() > 0;
    }

	/**
	 * Check to see if the player hasMoved before ending the turn.
	 * Can be used to warn the player that he needs to move.
     *
	 * @return  True if and only if the Player has moved.
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
	public void endTurn() {
		fireChanges();
		
		if(!isConfirmed())
			firePropertyChange(Handler.END_TURN_PROPERTY, "Do you want to confirm ending your turn?");
		else{
			AbstractGameCommand endTurnCommand = new EndTurnCommand(getGame());
			endTurnCommand.execute();
			
	    	firePropertyChange(Handler.CURRENT_PLAYER_PROPERTY, getGame().getCurrentPlayer().getName());
			resetConfirm();
		}
		
		fireChanges();
	}

	/**
	 * sets the confirm.
	 * @param 	bool 
	 * 			A boolean which sets the confirm.
	 * @post	bool = isConfirmed()
	 */
	public void confirm(boolean bool) {
		this.confirmed = bool;
	}

}
