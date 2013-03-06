package board;

import items.Inventory;
import items.Item;
import items.LightGrenade;
import items.SquareInventory;

import java.util.ArrayList;
import java.util.HashMap;

import square.obstacles.Obstacle;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

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
	private SquareInventory inventory;
	
	/**
	 * All the items used on this square
	 */
	private ArrayList<Item> usedItems;
	
	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private Obstacle obstacle;
	
	/**
	 * 
	 */
	private LightGrenade usedLightGrenade;

	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.usedItems = new ArrayList<Item>();
		this.inventory = new SquareInventory();
	}
	

	
	/**
	 * Returns a list of all the items that are placed on a square.
	 */
	public ArrayList<Item> getUsedItems() {
		return usedItems;
	}
	
	/**
	 * Adds and uses the given item on this square.
	 * 
	 * @param 	item
	 * 			The item to be added or used
	 * @throws 	IllegalArgumentException
	 * 			If the given item cannot be used on this square
	 */
	public void addUsedItem(Item item) throws IllegalArgumentException {
		if(!canBeUsedHere(item))
			throw new IllegalArgumentException();
		usedItems.add(item);
	}
	
	/**
	 * Adds and uses the given item on this square.
	 * 
	 * @param 	lg
	 * 			The lightgrenade to be added or used
	 * @throws 	IllegalArgumentException
	 * 			If the given item cannot be used on this square
	 */
	public void addUsedItem(LightGrenade lg) throws IllegalArgumentException {
		addUsedItem((Item) lg);
		usedLightGrenade = lg;
	}
	
	/**
	 * Returns true if there is an active LightGrenade on this square.
	 * @return true if there is an active lightGrenade on this square.
	 */
	public boolean hasActiveLightGrenade(){
		if(usedLightGrenade == null)
			return false;
		return usedLightGrenade.isActive();
	}
	
	/**
	 * Returns the inventory of this square.
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * This method is used to activate the usedItems on the square.
	 */
	public void activateUsedItems(){
		for(Item i: usedItems)
			i.activate();
	}
	
	@Override
	public String toString() {
		return "Square";
	}
	
	/**
	 * Returns whether this square is obstructed by an obstacle or not.
	 * 
	 * @return	True	if there is an obstacle which is not null.
	 * 			False	If there is no obstacle.
	 */
	public boolean isObstructed(){
			return false;
	}

	
	/**
	* Checks if the item can be used in the square.
	*
	* @param 	item
	* @return 	False If the item is already used once in the square.
	*/
	public boolean canBeUsedHere(Item item) {
		if(!usedItems.contains(item))
			return false;
		return true;
	}
}
