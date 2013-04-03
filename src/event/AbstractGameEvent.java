/**
 * 
 */
package event;

import java.util.Observable;
import java.util.Observer;

import game.Game;

/**
 * @author Jonas Devlieghere
 *
 */
public abstract class AbstractGameEvent extends Observable {

	private Game game;
	public static Observer OBSERVER;
	
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
