package command.effect;

import game.Game;
import game.Player;

public class LoseActionEffect extends Effect {
	
	private int amount;

	public LoseActionEffect(Game game, int amount) {
		super(game);
		this.amount = amount;
	}

	@Override
	protected void beforeGameCommand() {

	}

	@Override
	protected void duringGameCommand() {
		Player player = getGame().getCurrentPlayer();
		player.loseActions(this.amount);
	}

	@Override
	protected void afterGameCommand() {
		
	}

}
