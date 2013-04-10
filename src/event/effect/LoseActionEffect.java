package event.effect;

import player.Player;
import game.Game;

public class LoseActionEffect extends Effect {
	
	private int amount;

	public LoseActionEffect(Game game, int amount) {
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
		
	}

}