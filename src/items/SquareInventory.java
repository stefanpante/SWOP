package items;

import java.util.ArrayList;
import java.util.Iterator;

import be.kuleuven.cs.som.annotate.Basic;

import notnullcheckweaver.Nullable;

/**
 * This class describes the inventory of a square. 
 * 	With the constructor you have to state if this inventory can only have inactive Items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 * TODO: Implement the visitor pattern when there are more items then only a LightGrenade.
 */
public class SquareInventory extends Inventory {

	/**
	 * Creates a square inventory with the given size. 
	 * 
	 * @param 	size 
	 * 			The size of this inventory.
	 * @param	onlyInactiveItems
	 * 			Boolean that states whether this inventory can only have inactive Items.
	 * @effect	super(size)
	 */
	public SquareInventory(int size) {
		super(size);
	}

	/**
	 * Creates a square inventory with unlimited size.
	 * 
	 * @param	onlyInactiveItems
	 * 			Boolean that states whether this inventory can only have inactive Items.
	 * @effect	super(size)
	 */
	public SquareInventory() {
		super();
	}
	
	/**
	 * Adds a given item to this UsedItemInventory if possible. 
	 * 
	 * @param 	item
	 * 			The item to add.
	 * @throws	IllegalStateException
	 * 			If the given item is not a valid item 
	 * 			for any UsedItemInventory or for this one.
	 * 			| !isValidItem(item) || !canHaveAsItem(item)
	 * @effect	| super.addItem(item)
	 */
	@Override
	public void addItem(Item item) throws IllegalStateException {
		if(!isValidItem(item) || !canHaveAsItem(item))
			throw new IllegalStateException("This "
											+ item
											+ " is not a valid item for this "
											+ this);
		super.addItem(item);
	}	
	
	/**
	 * Returns if the inventory contains an active item
	 * @return
	 */
	public boolean hasActiveItem(){
		boolean result = false;
		for(Item item: super.getAllItems()){
			if(item.isActive()){
				result = true;
				break;
			}
		}
		
		return result;
		
	}
	/**
	 * Returns whether the given item can be used as an item of this UsedItemInventory.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if the conditions of superclass Inventory still hold and 
	 * 			this inventory doesn't already have a LightGrenade.
	 * 			| super.canHaveAsItem(item) &&
	 * 			| ! hasLightGrenade()
	 */
	@Override
	public boolean canHaveAsItem(Item item){
		if(!super.canHaveAsItem(item)){
			return false;
		}
		if(item instanceof LightGrenade && hasLightGrenade()){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns whether this UsedItemInventory has a LightGrenade.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if there is already a LightGrenade in this inventory
	 */
	public boolean hasLightGrenade(){
		if(getLightGrenade() == null)
			return false;
		return true;
	}
	
	/**
	 * Returns the only light grenade in this inventory.
	 * 
	 * @return	The LightGrenade in this inventory if there is none returns null.
	 */
	public LightGrenade getLightGrenade(){
		for(Item it : getAllItems()){
			if(it instanceof LightGrenade)
				 return (LightGrenade) it;
		}
		return null;
	}
	
	
	/**
	 * Returns whether this inventory has an active LightGrenade.
	 * 
	 * @return 	True if and only if this inventory has an active LightGrenade.
	 * 			| getLightGrenade().isActive()
	 */
	public boolean hasActiveLightGrenade(){
		LightGrenade lg = getLightGrenade();
		if(lg == null)
			return false;
		return getLightGrenade().isActive();
	}
	
	/**
	 * Active the given item. 
	 * 
	 * @param item
	 * @throws IllegalStateException
	 */
	public void activate(Item item)throws IllegalStateException{
		if(!this.hasItem(item)){
			throw new IllegalStateException("The "+ item + " is not in " + this);
		} else {
			item.activate();
		}
	}
	
	public void wearOut(){
		for(Item item: super.getAllItems()){
			if(item.isActive()){
				item.wearOut();
			}
		}
	}
	
	
//	/**
//	 * This method will activate all items in inactive state in this inventory.
//	 * 
//	 * @throws 	IllegalStateException
//	 * 			If this inventory cannot have active items.
//	 * 			| isOnlyInactiveItems()
//	 */
//	public void activateAllItems() throws IllegalStateException{
//		for (Item it : getAllItems()) {
//			if(it.getState() == ItemState.INACTIVE){
//				it.activate();
//			}
//		}
//	}
//	
	/**
	 * Returns all the items in this inventory that have a certain state. 
	 * 
	 * @param 	state
	 * 			The state which all returned items must have.
	 * @return	All items of this inventory that have a certain state.
	 */
	public ArrayList<Item> getItems(ItemState state){
		ArrayList<Item> result = new ArrayList<Item>();
		for (Item it : getAllItems()) {
			if(it.getState() == state){
				result.add(it);
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "Square ";
		return result + super.toString();
	}
}
