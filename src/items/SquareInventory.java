package items;

/**
 * This class descibes the inventory of a square. contains items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class SquareInventory extends Inventory {

	/**
	 *  
	 * @param size
	 */
	public SquareInventory(int size) {
		super(size);
	}

	/**
	 * 
	 */
	public SquareInventory() {
		super();
	}
	
	/**
	 * 
	 */
	@Override
	public void addItem(Item item) throws IllegalStateException {
		if(!isValidItem(item) || !canHaveAsItem(item))
			throw new IllegalStateException("This "
											+ item
											+ " is not a valid item for this "
											+ this);
		super.addItem(item);
	}
	
	/**
	 * Returns whether the given item can be added to the inventory 
	 * of any square ( checks whether the item is active).
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if the item is not an active item.
	 * 			| !item.isActive()
	 */
	public static boolean isValidItem(Item item) {
		if(!Inventory.isValidItem(item))
			return false;
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean canHaveAsItem(Item item){
		if(!super.canHaveAsItem(item)){
			return false;
		}
		return true;
	}
}
