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
		if(!getGame().getCurrentPlayer().getInventory().hasItem(getItem())){
			throw new IllegalStateException("Player can't throw a Launchable that isn't in his inventory!");
		}
	}

	//TODO: need to set the state of an lightgrenade to dropped if used.
	@Override
	protected void duringGameEvent(){
		// removes the the item from the players inventory and calls all appropiate methods in player.
		getGame().getCurrentPlayer().useItem(getItem());
		// adds the item to the inventory of the Square where the player is positioned.
		getGame().getCurrentPlayer().getPosition().getInventory().addItem(item);
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

}
