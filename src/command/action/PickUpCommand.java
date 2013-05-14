/**
 * 
 */
package command.action;

import item.Item;
import game.Game;

/**
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public class PickUpCommand extends ActionCommand {
	
	private Item item;

	/**
	 * Command to pick up an item
	 * @param game	the game.
	 * @param item	the Item which will be picked up
	 */
	public PickUpCommand(Game game, Item item) {
		super(game);
		this.item = item;
	}
	
	private Item getItem(){
		return this.item;
	}
	
	/**
	 * Performs the actual pickup of the item.
	 */
	@Override
	protected void duringGameCommand(){
		getGame().getCurrentPlayer().pickUp(getItem());
		getGame().getCurrentPlayer().getPosition().getInventory().removeItem(getItem());
	}
	
	@Override
	protected void afterGameCommand() {
		
	}

	/**
	 * Checks if preconditions are met.
	 */
	@Override
	protected void beforeGameCommand() {
		
	}

}
