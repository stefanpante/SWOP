package item;

import item.inventory.*;
import move.MovableEffect;
import item.visitor.VisitableItem;

/**
 * Parent class for all sorts of items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 **/
public abstract class Item implements VisitableItem, MovableEffect, ItemPrototype {

    private Inventory inventory;

    public void setInventory(Inventory inventory2){
        this.inventory = inventory2;
    }
    
    public Inventory getInventory(){
    	return this.inventory;
    }

    public void destroy(){
        inventory.removeItem(this);
        this.inventory = null;
    }



    /**
	 * Notifies the item that it has been used.
	 * No specific operation is needed at this level. Inheriting item may
	 * determine if it needs to handle internal operations if it is beeing used.
	 */
	public void notifyUse() {
		
	}
	
	/**
	 * Notifies the item that it has been picked up.
	 * No specific operation is needed at this level. Inheriting item may
	 * determine if it needs to handle internal operations if it is beeing picked up.
	 */
	public void notifyPickUp() {
		
	}
	
	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item";
	}
	
	public abstract boolean isSameType(Item item);

}
