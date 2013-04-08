package event.effect;

import game.Game;
import player.Player;

//TODO: should be also applicable to other players.
public class LoseTurnEvent extends EffectEvent {
	
	private int amount;
	private boolean accumulating;
	private Player player;

	public LoseTurnEvent(Game game, Player player, int amount, boolean accumulating) {
		super(game);
		this.player = player;
		this.amount = amount;
	}

	private int getAmount(){
		return this.amount;
	}
	
	private boolean isAccumulated(){
		return this.accumulating;
	}
	
	private Player getPlayer(){
		return this.player;
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
