package item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import item.visitor.VisitableItem;

/**
 * Parent class for all sorts of items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 **/
public abstract class Item implements VisitableItem {
	
	/**
	 * Constructs a new item with an inactive state
	 */
	public Item(){

	}
	
	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item";
	}
}
