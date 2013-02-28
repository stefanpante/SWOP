package items;

/**
 * Parent class for all sorts of items.
 * 
 * @author vincentreniers
 */
public class Item implements InventoryElement {

	/**
	 * This flag indicates if the item is active or inactive.
	 */
	private boolean active = false;
	
	/**
	 * Returns whether the item is active or inactive.
	 * 
	 * @return	True when the item is active
	 * 			False when the item is inactive.
	 */
	public boolean isActive() {
		return this.active;
	}
	
	/**
	 * activates the item.
	 */
	public void activate(){
		this.active = true;
	}
	
	@Override
	public String toString() {
		return "Item";
	}

	public void accept(InventoryElementVisitor visitor) {
		visitor.visit(this);
	}
}
