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
	

	//TODO: need to set the state of an lightgrenade to dropped if used.
	@Override
	protected void duringGameEvent(){
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#beforeGameEvent()
	 */
	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

}
