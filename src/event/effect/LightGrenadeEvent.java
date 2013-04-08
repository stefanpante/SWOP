/**
 * 
 */
package event.effect;

import game.Game;
import player.Player;

/**
 * @author jonas
 *
 */
public class LightGrenadeEvent extends EffectEvent {
	
	public static final int TURNS_LOST = 1;

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
		Player currentPlayer = getGame().getCurrentPlayer();
		currentPlayer.loseTurns(TURNS_LOST);
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub

	}

}
