package event.effect;

import player.Player;
import game.Game;

public class LoseActionEvent extends EffectEvent {
	
	private int amount;

	public LoseActionEvent(Game game, int amount) {
		super(game);
		this.amount = amount;
	}

	@Override
	protected void beforeGameEvent() {

	}

	@Override
	protected void duringGameEvent() {
		Player player = getGame().getCurrentPlayer();
		player.loseActions(this.amount);
	}

	@Override
	protected void afterGameEvent() {
		// TODO Auto-generated method stub
	}

}
