package player;

import items.Item;
import grid.Square;

/**
 * The actions that a player can use.
 * 
 * @author vincentreniers
 */
public interface IActions {
	
	/**
	 * Player moves to a square.
	 * 
	 * @param square
	 */
	public void move(Square square);
	
	/**
	 * Player picks up an item from its current square.
	 * 
	 * @param item
	 */
	public void pickUp(Item item);
	
	/**
	 * Player uses an item from his inventory.
	 * 
	 * @param item
	 */
	public void useItem(Item item);
	
	/**
	 * Player ends its turn.
	 */
	public void endTurn();

}
