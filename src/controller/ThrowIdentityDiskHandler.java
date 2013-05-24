package controller;

import game.Game;

import item.IdentityDisc;

import java.beans.PropertyChangeListener;

import command.AbstractGameCommand;
import command.action.IdentityDiscMoveCommand;

import util.Direction;
import square.Square;

/**
 * Handler for the throwing a launchable.
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public class ThrowIdentityDiskHandler extends Handler {


	/**
	 * Constructs a new launchableHandler
	 * @param game		the game instance which will be used.
	 * @param listener	the PropertyChangeHandler used by this handler.
	 */
	public ThrowIdentityDiskHandler(Game game, PropertyChangeListener listener) {
		super(game, listener);
	}
	
	/**
	 * Start the command to throw an identityDisc.
	 * @param disc		the disc to be thrown.
	 * @param direction	the direction in which the disc is thrown.
	 * @throws Exception
	 */
	public void throwLaunchable(IdentityDisc disc, Direction direction) throws Exception {
		fireChanges();
		Square startSquare = getGame().getCurrentPlayer().getPosition();
		AbstractGameCommand throwLaunchableEvent = new IdentityDiscMoveCommand(getGame(), disc, startSquare, direction);
		throwLaunchableEvent.execute();
		fireChanges();
	}

}
