/**
 * 
 */
package event.action;

import game.Game;
import item.Item;

/**
 * @author jonas
 *
 */
public class UseItemEvent extends ActionEvent {
	
	private Item item;

	/**
	 * @param game
	 * @param args
	 */
	public UseItemEvent(Game game, Item item) {
		super(game);
		this.item = item;
	}
	
	private Item getItem(){
		return this.item;
	}
	
	@Override
	protected void beforeGameEvent() {
		
	}
	
	@Override
	protected void duringGameEvent(){
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

}
