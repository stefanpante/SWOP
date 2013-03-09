	package handlers;

import player.Player;
import square.Direction;
import square.Square;
import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler extends Handler {


	private Game game;
	
	/**
	 * Basic constructor that initiates the moveHandler with the given game. 
	 * 
	 * @param game
	 */
	public MoveHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * Checks the precondition for the move use case
	 * returns true if the preconditions are satisfied
	 * 
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(game.getCurrentPlayer().getRemainingActions() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Moves in the given direction if it's possible, if it isn't
	 * possible, the player doesn't move but does lose an action
	 * 
	 * @param 	direction	
	 * 			The direction in which the player wants to move.
	 * 
	 */
	public void move(Direction direction) throws IllegalStateException, IllegalArgumentException{
		Square currentPosition = game.getCurrentPlayer().getPosition();

		if(currentPosition.canMoveTo(direction)){
			Square newPosition = currentPosition.getNeighbor(direction);
			currentPosition.activateUsedItems();
			game.getCurrentPlayer().incrementActions();
			game.getCurrentPlayer().move(newPosition);
		}
//		} catch(IllegalStateException e) {
//			System.err.println("Player cannot move the square.");
//		} catch(IllegalArgumentException e) {
//			System.err.println("Tried to move to a neighbor that is no square.");
//		}
		
		if(game.getCurrentPlayer().getPosition().hasActiveLightGrenade()){
			game.getCurrentPlayer().endTurn();
			game.switchPlayer();
		}
	}
	
	/**
	 * Checks if the end of the move causes the current player to win.
	 * @return 	true if the move causes the player to win. 
	 */
	public boolean endAction(){
		Player otherPlayer = game.getOtherPlayer();
		Player currentPlayer = game.getCurrentPlayer();
		
		if(otherPlayer.getStartPosition() == currentPlayer.getPosition()){
			return true;
		}
		
		return false;
	}

}
