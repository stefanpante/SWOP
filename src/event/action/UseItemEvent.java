/**
 * 
 */
package event.action;

import game.Game;
import item.Item;

/**
 *@author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
	
	@Override
	protected void duringGameEvent(){
		// adds the item to the inventory of the Square where the player is positioned.
		getGame().getCurrentPlayer().getPosition().getInventory().addItem(item);
		// removes the the item from the players inventory and calls all appropiate methods in player.
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

}
