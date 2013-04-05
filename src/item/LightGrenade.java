package item;

import item.ItemState;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import effect.Effect;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LightGrenade extends Item implements Effect{
	
	ItemState currentState = ItemState.INACTIVE; 
	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False when the item is inactive.
	 */
	public boolean isActive() {
		return this.currentState == ItemState.ACTIVE;
	}
	
	/**
	 * Returns the state of the item.
	 * 
	 * @return the state of the item.
	 */
	public ItemState getState(){
		return this.currentState;
	}
	
	/**
	 * Activates the item.
	 * 
	 * @throws 	IllegalStateException
	 * 			thrown if the current state isn't inactive. An item can only go to
	 * 			an active state from an inactive one.
	 */
	public void activate() throws IllegalStateException {
		if(isActive())
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the active state.");
		this.currentState = ItemState.ACTIVE;
	}
	
	/**
	 * Wears the item out.
	 * 
	 * @throws 	IllegalStateException
	 * 			Can only wear an item out when the current state is active.
	 * 			Otherwise, an IllegalStateException is thrown
	 */		
	public void wearOut() throws IllegalStateException {
		if(!isActive())
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the used state.");
		this.currentState = ItemState.WORN;
	}
	
	/**
	 * Deactivates the item
	 * 
	 * @throws 	IllegalStateException
	 * 		   	Can only deactivate an item if the current state is active.
	 * 			Otherwise, an IllegalStateException is thrown
	 */
	public void deactivate() throws IllegalStateException{
		if(!isActive())
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the inactive state.");
		this.currentState = ItemState.INACTIVE;
	}
	
	/**
	 * returns if this object has a penalty.
	 */
	public boolean hasEffect() {
		return true;
	}
	
	/**
	 * Returns the string representation of this LightGrenade
	 */
	@Override
	public String toString() {
		return super.toString() + " LightGrenade";
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory squareInventory) 
			throws IllegalStateException {
		squareInventory.addLightGrenade(this);		
	}
	
	@Override
	public void acceptRemoveSquareInventory(SquareInventory squareInventory)
			throws IllegalStateException {
		squareInventory.removeLightGrenade(this);
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory playerInventory)
			throws IllegalStateException {
		playerInventory.addLightGrenade(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory playerInventory)
			throws IllegalStateException {
		playerInventory.removeLightGrenade(this);
	}
}