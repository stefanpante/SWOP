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
public class EndTurnEvent extends AbstractGameEvent {

	/**
	 * @param game
	 * @param args
	 */
	public EndTurnEvent(Game game) {
		super(game);
	}

	@Override
	protected void duringGameEvent() {
		EffectValue penaltyValue = getGame().getCurrentPlayer().getPosition().getEffectAfterAction();
		getGame().getCurrentPlayer().endTurn(penaltyValue);
	}

	@Override
	protected void afterGameEvent() {
		getGame().switchToNextPlayer();
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
