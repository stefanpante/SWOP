package grid.core;

import items.Inventory;
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
/*TODO: Discuss: one inventory for items that can be picked up.
 * Another inventory for "used" items that are placed there.
 */
@NotNull
public class Square {
	
	/**
	 * Inventory containing all items on the square
	 */
	private Inventory inventory;
	
	/**
	 * All the items used on this square
	 */
	private ArrayList<Item> usedItems;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.usedItems = new ArrayList<Item>();
		this.inventory = new Inventory();
	}
	
	/**
	 * Returns a list of all the items that are currently on the square.
	 * These may be active or inactive.
	 */
	//TODO:replace with two seperate ones?? Makes no sense in the same inventory...
	public Inventory getItems() {
		return inventory;
	}
	
	public void addUsedItem(Item item){
		usedItems.add(item);
	}
	
	/**
	 * Adds the given item to the inventory of this square.
	 * 
	 * @param 	item
	 * 			The item that will be added to the inventory.
	 */
	public void addItemToInventory(Item item){
		if(!isValidInventoryItem(item) || !canHaveAsInventoryItem(item)){
			throw new IllegalArgumentException("The item"
												+item
												+"can not be added to the inventory of this "
												+this);
		}
		inventory.addItem(item);
	}
	
	
	
	/**
	 * Returns the inventory of this square.
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	
	
	/**
	 * used to activate the usedItems on the square
	 */
	public void activateUsedItems(){
		for(Item i: usedItems)
			i.activate();
	}
	
	/**
	 * Returns whether the given item is a valid item for all squares.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if the item is not an active item.
	 * 			| !item.isActive()
	 */
	//TODO: I think it is safe to assume that we never want an active item to be placed in the inventory.
	public static boolean isValidInventoryItem(Item item) {
		return !item.isActive();
	}

	/**
	 * Returns whether the given can be added to the current square.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return  True if there are 
	 */
	public boolean canHaveAsInventoryItem(Item item) {
		//TODO: add specific implementation
		return true;
	}
	
	@Override
	public String toString() {
		return "Square [ " + this.getInventory() +" ]";
	}
}
