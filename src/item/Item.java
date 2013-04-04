package item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

/**
 * Parent class for all sorts of items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 **/
public class Item implements VisitableItem {
	
	/**
	 * Constructs a new item with an inactive state
	 */
	public Item(){

	}

	public void acceptPlayerInventory(PlayerInventory plInv) {
		//Nothing should be done here?
	}

	public void acceptSquareInventory(SquareInventory sqInv) {
		//Nothing should be done here?
	}
	
	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item";
	}
}
