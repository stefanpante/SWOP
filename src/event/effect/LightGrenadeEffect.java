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
public class LightGrenadeEffect extends Effect {
	
	public static final int TURNS_LOST = 1;

	/**
	 * @param game
	 */
	public LightGrenadeEffect(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub

	}


	@Override
	protected void duringGameEvent() {
		Player currentPlayer = getGame().getCurrentPlayer();
		currentPlayer.loseTurns(TURNS_LOST, false);
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub

	}

}
