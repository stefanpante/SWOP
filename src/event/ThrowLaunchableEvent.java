/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */
public class ThrowLaunchableEvent extends ActionEvent {

	/**
	 * @param game
	 * @param args
	 */
	public ThrowLaunchableEvent(Game game, Object... args) {
		super(game, args);
	}

}
