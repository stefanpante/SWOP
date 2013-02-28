package items;

public class SquareInventory extends Inventory {

	public SquareInventory(int size) {
		super(size);
	}

	public SquareInventory() {
		super();
	}
	
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
	 * Returns whether the given item is a valid item for all SquareInventory objects.
	 * @param item
	 * @return
	 */
	public static boolean isValidItem(Item item) {
		if(!Inventory.isValidItem(item))
			return false;
		return true;
	}
	
	@Override
	public boolean canHaveAsItem(Item item){
		if(!super.canHaveAsItem(item)){
			return false;
		}
		if
	}

}
