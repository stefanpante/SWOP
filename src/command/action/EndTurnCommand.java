package command.action;

import game.Game;
import game.Player;

/**
 * End Turn event
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class EndTurnCommand extends ActionCommand {

	public EndTurnCommand(Game game) {
		super(game);
	}

	@Override
	protected void beforeGameCommand() {
		
	}

	@Override
	protected void duringGameCommand() {
		Player currentPlayer = getGame().getCurrentPlayer();
		
		currentPlayer.loseActions(currentPlayer.getRemainingActions());
	}
	
	@Override
	protected void afterGameCommand() {
		
	}

}
