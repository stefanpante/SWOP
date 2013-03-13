package items;

/**
 * Parent class for all sorts of items.
 * 
 * @author vincentreniers
 */
public class Item {
	
	long code;
	


	public Item(){
		setCode(System.nanoTime());
	}
	
	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	private void setCode(long code) {
		this.code = code;
	}

	/**
	 * This flag indicates if the item is active or inactive.
	 */
	private ItemState currentState = ItemState.INACTIVE;
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item) obj;
		if (getCode() != other.getCode()) {
			return false;
		}
		return true;
	}
	
	

	
}
