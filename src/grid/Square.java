package grid;

import items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import notnullcheckweaver.NotNull;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
@NotNull
public class Square {
	
	/**
	 * List of all the items that are currently on the square.
	 */
	private List<Item> items = new ArrayList<Item>();
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		
	}
	
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
	
	/**
	 * If the given <item> is a valid item it is added to the current square.
	 * 
	 * @param 	item 	
	 * 			The item to add.
	 * @post 	If the given item is a valid item and this square also can have the item as one of its items
	 * 			then the square has the given item as one of it's items.
	 * 			| if(isValidItem(item) && canHaveAsItem(item)) 
	 * 			|	this.hasItem(item)
	 */
	public void addItem(Item item){
		if(!isValidItem(item) || !canHaveAsItem(item)){
			throw new IllegalArgumentException("The given item is not a valid item");
		}
		items.add(item);
	}

	/**
	 * Returns whether the given item is a valid item for all squares.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return
	 */
	public static boolean isValidItem(Item item) {
		if(!(item instanceof Item)){
			return false;
		}
		return true;
	}

	/**
	 * Returns whether the given can be added to the current square.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return 
	 */
	public boolean canHaveAsItem(Item item) {
		//TODO: add specific implementation
		return true;
	}
}
