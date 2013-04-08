package item;

import item.LightGrenadeState;
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
	
	LightGrenadeState currentState = LightGrenadeState.INACTIVE; 
	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False when the item is inactive.
	 */
	public boolean isActive() {
		return this.currentState == LightGrenadeState.ACTIVE;
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
		if(isActive())
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