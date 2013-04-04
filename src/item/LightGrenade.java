package item;

import item.ItemState;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import effect.Effect;
import effect.EffectValue;

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

	/**
	 * No special effect is required before reaching a LightGrenade.
	 */
	@Override
	public EffectValue getEffectBeforeAction() {
		return new EffectValue();
	}

	/**
	 * Once the action is completed, the effect is already inflicted.
	 */
	@Override
	public EffectValue getEffectAfterAction() {
		return new EffectValue(0, -3);
	}
	
	@Override
	public void acceptPlayerInventory(PlayerInventory plInv) {
		plInv.addItem(this);		
	}

	@Override
	public void acceptSquareInventory(SquareInventory sqInv) {
		sqInv.addItem(this);		
	}
}
