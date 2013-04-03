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
	public UseItemEvent(Game game, Item item) {
		super(game, item);
	}
	
	private Item getItem(){
		return (Item) getArgument(0);
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
