/**
 * 
 */
package command.action;

import item.Item;
import game.Game;

/**
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class PickUpCommand extends ActionCommand {
	
	private Item item;

	/**
	 * @param game
	 * @param args
	 */
	public PickUpCommand(Game game, Item item) {
		super(game);
		this.item = item;
	}
	
	private Item getItem(){
		return this.item;
	}
	
	@Override
	protected void duringGameCommand(){
		getGame().getCurrentPlayer().pickUp(getItem());
		getGame().getCurrentPlayer().getPosition().getInventory().removeItem(getItem());
	}
	
	@Override
	protected void afterGameCommand() {
		
	}

	@Override
	protected void beforeGameCommand() {
		// TODO Auto-generated method stub
		
	}

}
