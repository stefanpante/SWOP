/**
 * 
 */
package event.action;

import item.Item;
import game.Game;

/**
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
		getGame().getCurrentPlayer().getPosition().getInventory().removeItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
