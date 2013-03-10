package handlers;

import java.util.Iterator;

import square.Square;

import game.Game;

/**
 * The Controller/Handler to end the turn of a player
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class EndTurnHandler {

	private Game game;
	
	public EndTurnHandler(Game game) {
		this.game = game;
	}
	

	// TODO: warning to check if all turns are used 
	
	/**
	 * 
	 * Checks the precondition for the pick up use case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(game.getCurrentPlayer().getRemainingActions() > 0)
			return true;
		return false;
	}

	public boolean hasMoved(){
		return game.getCurrentPlayer().hasMoved();
	}
	
	public void endTurn(){
		game.getCurrentPlayer().endTurn();
		game.switchToNextPlayer();
		
		Iterator<Square> iterator = game.getAllSquares().iterator();
		
		while(iterator.hasNext()) {
			Square square = iterator.next();
			square.getState().nextTurn(square);
		}
	}

}
