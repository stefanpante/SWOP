/**
 * 
 */
package event;

import item.Item;
import game.Game;

/**
 * @author jonas
 *
 */
public class PickUpEvent extends ActionEvent {

	/**
	 * @param game
	 * @param args
	 */
	public PickUpEvent(Game game, Item item) {
		super(game, item);
	}
	
	private Item getItem(){
		return (Item) getArgument(0);
	}
	
	@Override
	public void duringGameEvent(){
		getGame().getCurrentPlayer().pickUp(getItem());
		getGame().getCurrentPlayer().getPosition().getInventory().take(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		getGame().getCurrentPlayer().decrementActions();
		super.afterGameEvent();
	}

}
