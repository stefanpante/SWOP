package controller;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import move.Movable;

import command.AbstractGameCommand;
import command.action.MoveCommand;


import square.Direction;
import square.Square;
import util.Coordinate;
import game.Game;
import game.Player;

/**
 * Controller/Handler which controls the player move use case
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class MoveHandler extends Handler {

	/**
	 * Creates a new MoveHandler with a given game and a given listener
	 * 
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
	public void move(Direction direction) throws Exception {
		fireChanges();
		
		Game game = getGame();
		Player currentPlayer = game.getCurrentPlayer();
		Square startSquare = currentPlayer.getPosition();

		AbstractGameCommand moveEvent = new MoveCommand(game, currentPlayer, startSquare, direction);
		moveEvent.execute();
		fireChanges();
	}

}
