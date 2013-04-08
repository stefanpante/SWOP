/**
 * 
 */
package event.effect;

import player.Player;
import game.Game;

/**
 * @author jonas
 *
 */
public class PowerFailureEvent extends EffectEvent {
	
	public static final int TURNS_LOST = 1;
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
		Player currentPlayer = getGame().getCurrentPlayer();
		currentPlayer.loseTurns(TURNS_LOST);
	}

	@Override
	protected void afterGameEvent() {
		// Nothing to do here
	}

}
