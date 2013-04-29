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
public abstract class AbstractGameCommand extends Observable implements Command {

	private Game game;
	
	public AbstractGameCommand(Game game){
		setGame(game);
	}
	
	private void setGame(Game game){
		this.game = game;
	}
	
	public Game getGame(){
		return this.game;
	}
	
	/**
	 * Run this Game Event
	 */
	public void execute() throws Exception {
		beforeGameCommand();
		duringGameCommand();
		afterGameCommand();
	}
	
	protected abstract void beforeGameCommand();
	protected abstract void duringGameCommand() throws Exception;
	protected abstract void afterGameCommand() throws Exception;

	
}
