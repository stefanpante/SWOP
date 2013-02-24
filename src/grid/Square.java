package grid;

import items.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import player.Inventory;

import notnullcheckweaver.NotNull;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
@NotNull
public class Square {
	
	/**
	 * Inventory containing all items on the square
	 */
	private Inventory items;
	private ArrayList<Item> usedItems;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.usedItems = new ArrayList<Item>();
		//TODO: inventory for squares, hasn't got a size constraint
		this.items = new Inventory(10);
	}
	
	/**
	 * Returns a list of all the items that are currently on the square.
	 * These may be active or inactive.
	 */
	public Inventory getItems() {
		return items;
	}
	
	public void addUsedItem(Item item){
		usedItems.add(item);
		
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
	 * @return
	 */
	//TODO: Loze check?
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
