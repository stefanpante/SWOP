/**
 * 
 */
package event.effect;

import game.Game;

/**
 * @author Jonas Devlieghere
 *
 */
public class LoseActionEvent extends EffectEvent {

	int actions;
	
	/**
	 * @param game
	 */
	public LoseActionEvent(Game game, int actions) {
		super(game);
		this.actions = actions;
	}

	@Override
	protected void beforeGameEvent() {
		// Nothing to do here
	}

	@Override
	protected void duringGameEvent() {
		int remainingActions = getGame().getCurrentPlayer().getRemainingActions();
		int newRemainingActions = remainingActions - this.actions;
		//FIXME: getGame().getCurrentPlayer().setRemainingActions(newRemainingActions);
	}

	@Override
	protected void afterGameEvent() {
		// Nothing to do here
	}

}
