package handlers;

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
	

	/**
	 * 
	 * Checks the precondition for the pick up use case
	 * returns true if the preconditions are satisfied
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(game.getCurrentPlayer().getRemainingActions() > 0){
			return true;
		}
		
		return false;
	}
	
	public boolean hasMoved(){
		game.getCurrentPlayer().
	}
	
	public void endTurn(){
		game.getCurrentPlayer().incrementActions();
		game.switchPlayer();
		
	}

}
