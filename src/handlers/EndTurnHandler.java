package handlers;

import java.util.Iterator;

import square.Square;

import game.Game;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler extends Handler{
	
	public EndTurnHandler(Game game) {
		super(game);
	}
	
	/**
	 * 
	 * Checks the precondition for the pick up use case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
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
		getGame().getCurrentPlayer().endTurn();
		getGame().switchToNextPlayer();
		
		Iterator<Square> iterator = getGame().getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.getState().nextTurn(square);
		}
	}

}
