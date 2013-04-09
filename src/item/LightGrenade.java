package item;

import item.LightGrenadeState;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class LightGrenade extends Item{
	
	LightGrenadeState currentState = LightGrenadeState.INACTIVE; 
	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False otherwise
	 */
	public boolean isActive() {
		return this.currentState == LightGrenadeState.ACTIVE;
	}
	
	/**
	 * Returns whether the LightGrenade was dropped on the square by a player
	 * 
	 * @return	true when the item is dropped
	 * 			false otherwise
	 */
	public boolean isDropped() {
		return (this.currentState == LightGrenadeState.DROPPED);
	}
	
	/**
	 * Returns whether the LightGrenade was worn out. This means it has already exploded.
	 * 
	 * @return	True	If the item is worn out.
	 * 			False	If the item is not worn out.
	 */
	public boolean isWornOut() {
		return (this.currentState == LightGrenadeState.WORN);
	}
	
	/**
	 * Returns the state of the item.
	 * 
	 * @return the state of the item.
	 */
	public LightGrenadeState getState(){
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
		if(!isDropped())
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the active state.");
		
		this.currentState = LightGrenadeState.ACTIVE;
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
		this.currentState = LightGrenadeState.WORN;
	}
	
	/**
	 * Drops the item.
	 * 
	 * @throws	IllegalStateException
	 * 			If the item is active or worn out it cannot be dropped.
	 */
	public void drop() throws IllegalStateException {
		if(isActive())
			throw new IllegalStateException("Cannot drop a LightGrenade which is active.");
		
		if(isWornOut())
			throw new IllegalStateException("Cannot drop a LightGrenade which is worn out.");
		
		System.out.println("LightGrenade has been dropped.");
		this.currentState = LightGrenadeState.DROPPED;
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
		this.currentState = LightGrenadeState.INACTIVE;
	}
	
	/**
	 * If the item is used this means it has been dropped on a square.
	 * 
	 * @throws	IllegalStateException
	 * 			When the item is in a condition in which it cannot be used.
	 */
	@Override
	public void notifyUse() throws IllegalStateException {
		this.drop();
	}

	
	/**
	 * Returns the string representation of this LightGrenade
	 */
	@Override
	public String toString() {
		return super.toString() + " LightGrenade";
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) 
			throws IllegalStateException {
		sqInv.addLightGrenade(this);		
	}
	
	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.removeLightGrenade(this);
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.addLightGrenade(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.removeLightGrenade(this);
	}
}