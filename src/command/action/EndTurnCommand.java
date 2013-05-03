package command.action;

import game.Game;
import game.Player;

/**
 * End Turn event
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class EndTurnCommand extends ActionCommand {

    /**
     * Create a new EndTurnCommand
     *
     * @param   game
     *          The game
     */
	public EndTurnCommand(Game game) {
		super(game);
	}


	@Override
	protected void beforeGameCommand() {
		
	}

	/**
	 * Ensures consistency for the player, lets the player perform empty actions.
	 */
	@Override
	protected void duringGameCommand() {
		Player currentPlayer = getGame().getCurrentPlayer();
		currentPlayer.performEmptyActions();
		currentPlayer.loseActions(currentPlayer.getRemainingActions());
	}
	
	@Override
	protected void afterGameCommand() {
		
	}

}
