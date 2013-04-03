/**
 * 
 */
package event;

import game.Game;

/**
 * @author Jonas Devlieghere
 *
 */
public abstract class AbstractGameEvent {

	private Game game;
	
	public AbstractGameEvent(Game game){
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
	public void run() {
		beforeGameEvent();
		duringGameEvent();
		afterGameEvent();
	}
	
	protected abstract void beforeGameEvent();
	protected abstract void duringGameEvent();
	protected abstract void afterGameEvent();

	
}
