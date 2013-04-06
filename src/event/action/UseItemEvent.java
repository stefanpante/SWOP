/**
 * 
 */
package event.action;

import java.util.Observer;

import game.Game;
import item.Item;
import controller.GameHandler;

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
	protected void duringGameEvent(){
		getGame().getCurrentPlayer().useItem(getItem());
	}
	
	@Override
	protected void afterGameEvent() {
		getGame().getCurrentPlayer().decrementActions();
	}

	/* (non-Javadoc)
	 * @see event.AbstractGameEvent#beforeGameEvent()
	 */
	@Override
	protected void beforeGameEvent() {
		// TODO Auto-generated method stub
		
	}

}