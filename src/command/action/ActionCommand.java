/**
 *
 */
package command.action;

import command.AbstractGameCommand;
import game.Game;

/**
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public abstract class ActionCommand extends AbstractGameCommand {

    /**
     * Constructs a new ActionCommand
     *
     * @param game the game on which the ActionCommand uses.
     */
    ActionCommand(Game game) {
        super(game);
    }

    /**
     * Executes the actual action.
     */
    @Override
    public void execute() {
        beforeActionCommand();
        beforeGameCommand();
        duringGameCommand();
        afterGameCommand();
        afterActionCommand();
    }

    /**
     * Checks if preconditions are met for the action.
     */
    private void beforeActionCommand() {
        if (!getGame().isActive())
            throw new IllegalStateException("The game is over.");
        if (getGame().getCurrentPlayer().getRemainingActions() <= 0)
            throw new IllegalStateException("The current player has no remaining action left.");
    }

    /**
     * Checks if the post conditions are met for this action.
     */
    private void afterActionCommand() {
        //Currently nothing is needed here.
    }


}
