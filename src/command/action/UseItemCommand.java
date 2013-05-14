/**
 * 
 */
package command.action;

import game.Game;
import item.Item;

/**
 *@author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public class UseItemCommand extends ActionCommand {
	
	private Item item;

    /**
     * Create a new UseItem Command.
     *
     * @param   game
     *          The game.
     * @param   item
     *          The item to be used.
     */
	public UseItemCommand(Game game, Item item) {
		super(game);
		this.item = item;
	}
	
	private Item getItem(){
		return this.item;
	}
	
	/**
	 * Checks if the preconditions are met for using an item.
	 */
	@Override
	protected void beforeGameCommand() {
		if(!getGame().getCurrentPlayer().getInventory().hasItem(getItem())){
			throw new IllegalStateException("Player can't throw a Launchable that isn't in his inventory!");
		}
	}
	
	/**
	 * Performs the actual action.
	 */
	@Override
	protected void duringGameCommand(){
		// adds the item to the inventory of the Square where the player is positioned.
		getGame().getCurrentPlayer().getPosition().getInventory().addItem(item);
		// removes the the item from the players inventory and calls all appropiate methods in player.
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameCommand() {

	}

}
