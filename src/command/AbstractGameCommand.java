/**
 * 
 */
package command;

import java.util.Observable;
import java.util.Observer;

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
	public AbstractGameCommand(Game game){
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
	public Game getGame(){
		return this.game;
	}
	
	/**
	 * Execite this command
	 */
	public void execute() throws Exception {
		beforeGameCommand();
		duringGameCommand();
		afterGameCommand();
	}

    /**
     * Operations and checks to be executed before the actual command
     */
	protected abstract void beforeGameCommand() throws  Exception;

    /**
     * Operations and checks to be executed during the actual command
     */
	protected abstract void duringGameCommand() throws Exception;

    /**
     * Operations and checks to be executed after the actual command
     */
	protected abstract void afterGameCommand() throws Exception;

	
}
