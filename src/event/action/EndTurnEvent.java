package event.action;

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
		getGame().getCurrentPlayer().endTurn();
		getGame().switchToNextPlayer();
		
		for(Square square : getGame().getGrid().getAllSquares())
			square.getPower().decreaseTurn();
	}

	/**
	 * If a player ends his turn on a PowerFailed square he loses 1 action 
	 * on the next turn.
	 */
	@Override
	protected void afterGameEvent() {
		new LoseActionEvent(getGame(), 1).run();
	}

}
