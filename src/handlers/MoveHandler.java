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
	
	/**
	 * Basic constructor that initiates the moveHandler with the given game. 
	 * 
	 * @param game
	 */
	public MoveHandler(Game game) {
		super(game);
	}
	
	/**
	 * Checks the precondition for the move use case
	 * returns true if the preconditions are satisfied
	 * 
	 * @return 	true if the precondition is satisfied
	 * 			otherwise false.
	 */
	public boolean checkToProceed(){
		if(getGame().getCurrentPlayer().getRemainingActions() > 0){
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
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		//TODO: move to square where other player is should be invalid
		if(currentPosition.canMoveTo(direction)){
			Square newPosition = currentPosition.getNeighbor(direction);
			currentPosition.getUsedInventory().activateAllItems();
			getGame().getCurrentPlayer().incrementActions();
			getGame().getCurrentPlayer().move(newPosition);
		}
//		} catch(IllegalStateException e) {
//			System.err.println("Player cannot move the square.");
//		} catch(IllegalArgumentException e) {
//			System.err.println("Tried to move to a neighbor that is no square.");
//		}
		
		if(getGame().getCurrentPlayer().getPosition().getUsedInventory().hasActiveLightGrenade()){
			getGame().getCurrentPlayer().endTurn();
			getGame().switchToNextPlayer();
		}
	}
	
	/**
	 * Checks if the end of the move causes the current player to win.
	 * @return 	true if the move causes the player to win. 
	 */
	public boolean endAction(){
		Player nextPlayer = getGame().getNextPlayer();
		Player currentPlayer = getGame().getCurrentPlayer();
		
		if(nextPlayer.getStartPosition() == currentPlayer.getPosition()){
			return true;
		}
		
		return false;
	}

}
