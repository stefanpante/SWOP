package controller;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import event.AbstractGameEvent;
import event.action.MoveEvent;

import player.Player;
import square.Direction;
import util.Coordinate;
import game.Game;

/**
 * Controller/Handler which controls the player move use case
 * 
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
	 * Moves in the given direction if it's possible, if it isn't
	 * possible, the player doesn't move but does lose an action
	 * 
	 * @param 	direction	
	 * 			The direction in which the player wants to move.
	 * 
	 */
	public void move(Direction direction) throws IllegalStateException, IllegalArgumentException, NoSuchElementException {
		fireChanges();
		AbstractGameEvent moveEvent = new MoveEvent(getGame(), direction);
		moveEvent.run();
		setPropertyChanges();
		fireChanges();
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
