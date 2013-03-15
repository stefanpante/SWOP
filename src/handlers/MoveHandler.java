package handlers;

import items.LightGrenade;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import player.Player;
import square.Direction;
import square.Square;
import utils.Coordinate;
import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class MoveHandler extends Handler {

	/**
	 * Creates a new MoveHandler with a given game and a given listener
	 * @param game		the game which this MoveHandler will use
	 * @param listener	the PropertyChangeListener which this MoveHandler will use.
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
		if(getGame().getCurrentPlayer().getRemainingActions() > 0)
			return true;

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
		startAction();
		if(!checkToProceed()){
			getGame().getCurrentPlayer().endTurn(); //TODO: depends on powerfailure
			getGame().switchToNextPlayer();
		}
		else if(getGame().getGrid().canMoveTo(getGame().getCurrentPlayer().getPosition(), direction)){
			// Gets the current Position of the player
			Square currentPosition = getGame().getCurrentPlayer().getPosition();
			if(currentPosition.getInventory().hasLightGrenade()){
				LightGrenade lg = currentPosition.getInventory().getLightGrenade();
				try{
					currentPosition.getInventory().activate(lg);
				} catch (Exception exc) {
					// Catched exception, if try to activate wornout item. -> ignore
				}
			}
			//Throws NoSuchElementException
			Square newPosition = getGame().getGrid().getNeighbor(currentPosition, direction); 
			getGame().getCurrentPlayer().move(newPosition);
			
			setRemainingActions(newPosition);
			getGame().getCurrentPlayer().getPosition().getInventory().wearOut();

			setPropertyChanges();
		}else{
			throw new IllegalStateException("You can't move to there! (" + direction+")");
		}
		endAction();
	}
	/**
	 * Sets the remaining actions of the current player
	 * @param newPosition
	 */
	private void setRemainingActions(Square newPosition){
		// Gets the result of the state for the new position
		if(newPosition.hasPenalty()){
			int penalty = newPosition.getPenalty();
			getGame().getCurrentPlayer().endTurn(Player.MAX_ALLOWED_ACTIONS + penalty);
			getGame().switchToNextPlayer();
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
		firePropertyChange(GameHandler.SQUARE_INVENTORY_PROPERTY, getSquareItems());
		firePropertyChange(GameHandler.PLAYER_INVENTORY_PROPERTY, getPlayerItems());
	}



}
