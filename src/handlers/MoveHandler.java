package handlers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import player.Player;
import square.Direction;
import square.Square;
import square.state.StateResult;
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
	//TODO: endturnhandler oproepen?
	public void move(Direction direction) throws IllegalStateException, IllegalArgumentException, NoSuchElementException{
		
		Square currentPosition = getGame().getCurrentPlayer().getPosition();		
		Square newPosition = getGame().getGrid().getNeighbor(currentPosition, direction); //Throws NoSuchElementException
	
		// cannot move to square were other player is positioned
		ArrayList<Player> otherPlayers = getGame().getOtherPlayers();
		for(Player p: otherPlayers){
			if(p.getPosition().equals(newPosition))
				throw new IllegalStateException("Cannot move to square were other player is positioned.");
		}
		
		getGame().getCurrentPlayer().move(newPosition);
		currentPosition.getInventory().activateAllItems();
		
		StateResult stateResult = newPosition.getState().resultOnMove();
		int currentRemainingActions = getGame().getCurrentPlayer().getRemainingActions();
		if(newPosition.getInventory().hasActiveLightGrenade()){
			stateResult = newPosition.getState().resultOnMoveLG();
		}
		if(stateResult.hasToEndTurn()){
			getGame().getCurrentPlayer().endTurn();
			int remaining = currentRemainingActions + 3 - stateResult.getLostActions();
			getGame().getCurrentPlayer().setRemainingActions(remaining);
			getGame().switchToNextPlayer();
		}
		else{
			getGame().getCurrentPlayer().setRemainingActions(currentRemainingActions -1);
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
