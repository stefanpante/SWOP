
package command.effect;

import game.Game;
import game.Player;

public class LoseTurnEffect extends Effect {
	
	private int amount;
	private boolean accumulating;
	private Player player;

	/**
	 * This event has an effect on a player's turns.
	 * 
	 * @param	game
	 * @param	player
	 * 			The player which will lose turns.
	 * @param 	amount
	 * 			Amount of turns lost.
	 * @param	accumulating
	 * 			True if remaining actions still need to be taken into account.
	 */
	public LoseTurnEffect(Game game, Player player, int amount, boolean accumulating) {
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
	protected void beforeGameCommand() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void duringGameCommand() {
		player.loseTurns(this.amount, this.accumulating);
	}

	@Override
	protected void afterGameCommand() {
		// TODO Auto-generated method stub
	}

}

