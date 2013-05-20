package item;

import effect.Effect;
import effect.LightGrenadeEffect;
import item.inventory.PlayerInventory;
import square.Square;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
public class LightGrenade extends Item implements Activatable {
	
	LightGrenadeState currentState = LightGrenadeState.INACTIVE; 
	

    @Override
	public boolean isActive() {
		return this.currentState == LightGrenadeState.ACTIVE;
	}

    @Override
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
	
    @Override
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
	
    @Override
	public void drop() throws IllegalStateException {
		if(isActive())
			throw new IllegalStateException("Cannot drop a LightGrenade which is active.");
		
		if(isWornOut())
			throw new IllegalStateException("Cannot drop a LightGrenade which is worn out.");
		
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

    public boolean canAddTo(Square square){
        return (!square.hasItem(this) && !square.hasType(this));
    }

    public boolean canAddTo(PlayerInventory playerInventory){
        return true;
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

	
	/*
	 * Returns whether the given object is a lightgrenade
	 */
	public static boolean isLightGrenade(Object o){
		return (o instanceof LightGrenade);
	}
	
	/**
	 * returns whether the given object is of the same type.
	 * @param o the item to be checked
	 */
	@Override
	public boolean isSameType(Item o){
		return (o instanceof LightGrenade);
	}

    public Effect getEffect(){
        return new LightGrenadeEffect(this);
    }

    /**
	 * The state of the LightGrenade.
	 */
	private enum LightGrenadeState {
		ACTIVE,
		INACTIVE,
		DROPPED,
		WORN;
	}

}