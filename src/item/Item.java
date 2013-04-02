package item;

import item.state.ActiveState;
import item.state.ItemState;
import item.state.InactiveState;
import item.state.WornState;

/**
 * Parent class for all sorts of items.
 * 
 * TODO: decide whether to implement the State pattern for items
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 **/
public class Item {

	/**
	 * This flag indicates if the item is active or inactive.
	 */
	private ItemState currentState;

	/**
	 * Constructs a new item with an inactive state
	 */
	public Item(){
		currentState = new InactiveState();
	}

	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False when the item is inactive.
	 */
	public boolean isActive() {
		return this.currentState instanceof ActiveState;
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
		this.currentState = new ActiveState();
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
		this.currentState = new WornState();
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
		this.currentState = new InactiveState();
	}
	
	
	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item [" + getState() +"]";
	}
}
