/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */
public class GameEvent extends AbstractGameEvent {
	
	
	public GameEvent(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {
		if(!getGame().isActive())
			throw new IllegalStateException("The game is over.");
	}

	@Override
	protected void duringGameEvent() {
		
	}

	@Override
	protected void afterGameEvent() {

	}
}
