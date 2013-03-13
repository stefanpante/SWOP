package handlers;

import java.beans.PropertyChangeListener;

import player.Player;

import square.state.StateResult;
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
	boolean doEndTurn = false;
	
	/**
	 * @param game
	 * @param window
	 */
	public EndTurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	/**
	 * This method sets the confirmed flag on true and 
	 * 	sets the doEndTurn on the given boolean
	 * 
	 * @param 	doEndTurn
	 * 			True 	if you really want to end your turn.
	 * 			False	if you don't want to end your turn.
	 * @effect	endTurn()
	 * 			If the given value is true.
	 */
	public void confirm(boolean doEndTurn){
		confirmed = true;
		this.doEndTurn  = doEndTurn;
		endTurn();
	}
	
	public void resetConfirm(){
		confirmed = false;
		doEndTurn = false;
	}
	
	public boolean isConfirmed(){
		return confirmed;
	}
	
	public boolean getDoEndTurn(){
		return doEndTurn;
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
			throw new IllegalStateException("Do you want to confirm ending your turn?");
		}
		if(getDoEndTurn()){
			StateResult stateresult = getGame().getCurrentPlayer().getPosition().getState().resultOnStart();
			int lostActions = stateresult.getLostActions();
			
			getGame().getCurrentPlayer().endTurn(lostActions);
			getGame().switchToNextPlayer();
			getGame().updateStates();
			getGame().powerFailureSquares();
			
			firePropertyChange(GameHandler.CURRENT_PLAYER_PROPERTY, getGame().getGrid().getCoordinate(getGame().getCurrentPlayer().getPosition()));
    		firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getGame().getCurrentPlayer().getPosition().getInventory());
			resetConfirm();
		}
		endAction();
	}

}
