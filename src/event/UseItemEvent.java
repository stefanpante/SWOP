/**
 * 
 */
package event;

import game.Game;
import item.Item;
import controller.GameHandler;

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
	
	private Item getItem(){
		return (Item) getArgument(0);
	}
	
	@Override
	protected void beforeGameEvent() {
		super.beforeGameEvent();
	}

	@Override
	protected void duringGameEvent(){
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		getGame().getCurrentPlayer().decrementActions();
		super.afterGameEvent();
	}

}
