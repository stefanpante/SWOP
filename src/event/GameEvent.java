/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */

//TODO this class may be removed.
public class GameEvent extends AbstractGameEvent {
	
	
	public GameEvent(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {

	}

	@Override
	protected void duringGameEvent() {
		
	}

	@Override
	protected void afterGameEvent() {

	}
}
