/**
 * 
 */
package event;

import controller.GameHandler;
import game.Game;

/**
 * @author jonas
 *
 */
public class UseItemEvent extends ActionEvent {

	/**
	 * @param game
	 * @param args
	 */
	public UseItemEvent(Game game, Object... args) {
		super(game, args);
	}
	
	@Override
	protected void beforeGameEvent() {
		super.beforeGameEvent();
	}

	@Override
	protected void duringGameEvent(){
		super.duringGameEvent();
	}
	
	@Override
	protected void afterGameEvent() {
		super.afterGameEvent();
	}
	
	
	


}
