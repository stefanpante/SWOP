/**
 * 
 */
package event.effect;

import game.Game;

/**
 * @author jonas
 *
 */
public class PowerFailureEvent extends EffectEvent {

	public static final int ACTIONS_LOST = 1;
	
	/**
	 * @param game
	 */
	public PowerFailureEvent(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
 

	@Override
	protected void beforeGameEvent() {
		// Nothing to do here
	}


	@Override
	protected void duringGameEvent() {
		LoseActionEvent loseActionEvent = new LoseActionEvent(getGame(), ACTIONS_LOST);
		loseActionEvent.run();
	}

	@Override
	protected void afterGameEvent() {
		// Nothing to do here
	}

}
