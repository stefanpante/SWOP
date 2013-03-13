package handlers;

import java.beans.PropertyChangeListener;

import square.state.StateResult;

import game.Game;
import gui.ApplicationWindow;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler extends Handler{

	/**
	 * @param game
	 * @param window
	 */
	public EndTurnHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
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
	 */
	public void endTurn(){
		StateResult stateresult = getGame().getCurrentPlayer().getPosition().getState().resultOnStart();
		int lostActions = stateresult.getLostActions();
		
		getGame().getCurrentPlayer().endTurn(lostActions);
		getGame().switchToNextPlayer();
		getGame().updateStates();
		getGame().powerFailureSquares();
//		ApplicationWindow.MODEL.setCurrentPlayer(getGame().getCurrentPlayer());
	}

}
