package grid;

import items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
public class Square {
	
	/**
	 * List of all the items that are currently on the square.
	 */
	private List<Item> items = new ArrayList<Item>();
	
	/**
	 * Returns a list of all the items that are currently on the square.
	 * These may be active or inactive.
	 */
	public List<Item> getItems() {
		return items;
	}
	
	/**
	 * Checks if the square has a certain item.
	 * 
	 * @param item
	 */
	public boolean hasItem(Item item) {
		return items.contains(item);
	}
	
	/**
	 * Check if the square has an active item.
	 */
	public boolean hasActiveItem() {
		Iterator<Item> iterator = items.iterator();
		
		while(iterator.hasNext()) {
			Item item = iterator.next();
			if(item.isActive()) {
				return true;
			}
		}
		
		return false;
	}
	
}
