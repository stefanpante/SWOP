package event.action;

import player.Player;
import square.Square;
import controller.GameHandler;
import event.effect.LoseActionEvent;
import game.Game;

/**
 * End Turn event
 * 
 * @author vincent
 */
public class EndTurnEvent extends ActionEvent {

	public EndTurnEvent(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameEvent() {
		
	}

	@Override
	protected void duringGameEvent() {
		Player currentPlayer = getGame().getCurrentPlayer();
		
		currentPlayer.loseActions(currentPlayer.getRemainingActions());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

}
