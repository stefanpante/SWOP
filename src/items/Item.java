package items;

/**
 * Parent class for all sorts of items.
 * 
 * @author vincentreniers
 */
public class Item {

	/**
	 * This flag indicates if the item is active or inactive.
	 */
	private ItemState currentState = ItemState.INACTIVE;

	public Item(){
	}

	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False when the item is inactive.
	 */
	public boolean isActive() {
		return this.currentState == ItemState.ACTIVE;
	}
	
	public ItemState getState(){
		return this.currentState;
	}
	
	/**
	 * Activates the item.
	 */
	public void activate() throws IllegalStateException {
		if(this.currentState != ItemState.INACTIVE)
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the active state.");
		this.currentState = ItemState.ACTIVE;
	}
	
	public void wearOut() throws IllegalStateException {
		if(this.currentState != ItemState.ACTIVE)
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the used state.");
		this.currentState = ItemState.WORN;
	}
	
	public void deactivate() throws IllegalStateException{
		if(this.currentState != ItemState.ACTIVE)
			throw new IllegalStateException("Cannot go from state " + this.currentState + " to the inactive state.");
		this.currentState = ItemState.INACTIVE;
	}
	@Override
	public String toString() {
		return "Item";
	}

	
	

	
}
