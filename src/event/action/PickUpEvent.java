/**
 * 
 */
package event.action;

import item.Item;
import game.Game;

/**
 * @author jonas
 *
 */
public class PickUpEvent extends ActionEvent {
	
	private Item item;

	/**
	 * @param game
	 * @param args
	 */
	public PickUpEvent(Game game, Item item) {
		super(game);
		this.item = item;
	}
	
	private Item getItem(){
		return this.item;
	}
	
	@Override
	public void duringGameEvent(){
		getGame().getCurrentPlayer().pickUp(getItem());
		getGame().getCurrentPlayer().getPosition().getInventory().take(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
