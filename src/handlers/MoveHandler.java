package handlers;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import player.Player;
import square.Direction;
import square.Square;
import square.state.StateResult;
import utils.Coordinate;
import game.Game;
import gui.ApplicationWindow;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler extends Handler {

	/**
	 * @param game
	 * @param window
	 */
	public MoveHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
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
	public void move(Direction direction) throws IllegalStateException, IllegalArgumentException, NoSuchElementException {
		// Gets the current Position of the player
		Square currentPosition = getGame().getCurrentPlayer().getPosition();
		
		//Throws NoSuchElementException
		Square newPosition = getGame().getGrid().getNeighbor(currentPosition, direction); 
		// cannot move to square were other player is positioned
		
		getGame().getCurrentPlayer().move(newPosition);
		currentPosition.getInventory().activateAllItems();
		
		setRemainingActions(newPosition);
		setPropertyChanges();

	}
	
	/**
	 * Sets the remaining actions of the current player
	 * @param newPosition
	 */
	private void setRemainingActions(Square newPosition){
		StateResult stateResult = newPosition.getState().resultOnMove();
		int currentRemainingActions = getGame().getCurrentPlayer().getRemainingActions() -1;
		if(newPosition.getInventory().hasActiveLightGrenade()){
			stateResult = newPosition.getState().resultOnMoveLG();
		}
		if(stateResult.hasToEndTurn()){
			getGame().getCurrentPlayer().endTurn();
			int remaining = Player.MAX_ALLOWED_ACTIONS - stateResult.getLostActions();
			getGame().getCurrentPlayer().setRemainingActions(remaining);
			getGame().switchToNextPlayer();
		}else{
			getGame().getCurrentPlayer().setRemainingActions(currentRemainingActions -1);
		}
	}
	
	/**
	 * firesPropertyChanges for the GUI
	 */
	private void setPropertyChanges(){
		ArrayList<Coordinate> players = new ArrayList<Coordinate>();
    	for(Player player : getGame().getPlayers()){
    		players.add(getGame().getGrid().getCoordinate(player.getPosition()));
    	}
    	firePropertyChange(GameHandler.PLAYERS_PROPERTY, players);
    	firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getGame().getCurrentPlayer().getPosition().getInventory().getAllItems());
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
