package event.effect;

import game.Game;
import player.Player;

public class LoseTurnEvent extends EffectEvent {
	
	private int amount;
	private boolean accumulating;

	public LoseTurnEvent(Game game, int amount, boolean accumulating) {
		super(game);
		this.amount = amount;
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void duringGameEvent() {
		Player player = getGame().getCurrentPlayer();
		player.loseTurns(this.amount, this.accumulating);
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
	}

}
