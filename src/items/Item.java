package items;

/**
 * Parent class for all sorts of items.
 * 
 * @author vincentreniers
 */
public class Item {

	/**
	 * This flag indicates of the item is active or inactive.
	 * An active item cannot be picked up and for example and
	 * may cos action when entering the square.
	 */
	private boolean active = false;
	
	/**
	 * Is the item active or inactive.
	 * 
	 * @return
	 * 	True when the item is active
	 * 	False when the item is inactive.
	 */
	public boolean isActive() {
		return this.active;
	}
}
