/**
 * 
 */
package event;

import effect.EffectValue;
import game.Game;

/**
 * @author jonas
 *
 */
public class EndTurnEvent extends GameEvent {

	/**
	 * @param game
	 * @param args
	 */
	public EndTurnEvent(Game game) {
		super(game);
	}

	@Override
	protected void duringGameEvent() {
		EffectValue penaltyValue = getGame().getCurrentPlayer().getPosition().getEffectDuringAction();
		getGame().getCurrentPlayer().endTurn(penaltyValue);
	}

	@Override
	protected void afterGameEvent() {
		getGame().switchToNextPlayer();
	}

}
