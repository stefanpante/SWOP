/**
 * 
 */
package event.effect;

import game.Game;

/**
 * @author jonas
 *
 */
public class LoseTurnEvent extends EffectEvent {
	
	private int turns;
	
	public LoseTurnEvent(Game game, int turns) {
		super(game);
		this.turns = turns;
	}

	@Override
	protected void beforeGameEvent() {
		// Nothing to do here
	}

	@Override
	protected void duringGameEvent() {
		// FIXME: There's no method for checking disabled
	}

	@Override
	protected void afterGameEvent() {
		// Nothing to do here
	}
	
	

}
