/**
 * 
 */
package event.effect;

import game.Game;

/**
 * @author jonas
 *
 */
public class LightGrenadeEvent extends EffectEvent {
	
	public static final int ACTIONS_LOST = 3;

	/**
	 * @param game
	 */
	public LightGrenadeEvent(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void duringGameEvent() {
		LoseActionEvent loseActionEvent = new LoseActionEvent(getGame(), ACTIONS_LOST);
		loseActionEvent.run();
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub

	}

}
