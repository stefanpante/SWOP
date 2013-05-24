package controller;

import command.AbstractGameCommand;
import command.action.IdentityDiscMoveCommand;
import game.Game;
import item.IdentityDisc;
import square.Square;
import util.Direction;

import java.beans.PropertyChangeListener;

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
	public void throwLaunchable(IdentityDisc disc, Direction direction) {
		fireChanges();
		Square startSquare = getGame().getCurrentPlayer().getPosition();
		AbstractGameCommand throwLaunchableEvent = new IdentityDiscMoveCommand(getGame(), disc, startSquare, direction);
		throwLaunchableEvent.execute();
		fireChanges();
	}

}
