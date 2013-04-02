/**
 * 
 */
package event;

import game.Game;

/**
 * @author jonas
 *
 */
public abstract class GameEvent {

	private Game game;
	private Object[] args;
	
	public GameEvent(Game game, Object[] args){
		setGame(game);
		setArgs(args);
	}
	
	private void setGame(Game game){
		this.game = game;
	}
	
	private void setArgs(Object[] args){
		this.args = args;
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public Object getArgument(int id){
		return args[id];
	}
	
	public void run() {
		beforeGameEvent();
		duringGameEvent();
		afterGameEvent();
	}
	
	protected abstract void beforeGameEvent();
	protected abstract void duringGameEvent();
	protected abstract void afterGameEvent();
	
}
