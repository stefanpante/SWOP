/**
 * 
 */
package command;

import game.Game;

/**
 * @author Jonas Devlieghere
 *
 */
public abstract class AbstractGameCommand implements Command {

    /**
     * The game of this command
     */
	private Game game;

    /**
     * Construct a Game Command
     * @param   game
     *          The game of this command
     */
    protected AbstractGameCommand(Game game){
		setGame(game);
	}

    /**
     * Set the game of this command
     *
     * @param   game
     *          The game to be set
     */
	private void setGame(Game game){
		this.game = game;
	}

    /**
     * Return the game of this command
     *
     * @return  The game
     */
    protected Game getGame(){
		return this.game;
	}
	
	/**
	 * Execute this command
	 */
	public void execute() {
		beforeGameCommand();
		duringGameCommand();
		afterGameCommand();
	}

	/**
	 * Checks if preconditions are met.
	 */
	protected abstract void beforeGameCommand();

    /**
     * Operations and checks to be executed during the actual command
     */
	protected abstract void duringGameCommand();

    /**
     * Checks if postConditions are met
     */
	protected abstract void afterGameCommand();

	
}
