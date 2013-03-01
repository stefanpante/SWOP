package items;

import notnullcheckweaver.Nullable;

/**
 * This class descibes the inventory of a square. contains items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class SquareInventory extends Inventory {
	
	@Nullable
	private LightGrenade lightGrenade;
	
	/**
	 * Creates a square inventory with the given size. 
	 * 
	 * @param size
	 */
	public SquareInventory(int size) {
		super(size);
	}

	/**
	 * Creates a square inventory with unlimited size.
	 */
	public SquareInventory() {
		super();
	}
	
	/**
	 * Adds a LightGrenade to the inventory if possible.
	 * @param lg
	 */
	public void addItem(LightGrenade lg) throws IllegalStateException{
		addItem((Item)lg);
		if(lightGrenade != null){
			throw new IllegalStateException("You can only add one LightGrenade to the inventory");
		}
		lightGrenade = lg;
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
	 * Removes the only light grenade form the inventory.
	 * @param lg
	 * @throws IllegalStateException
	 */
	public void take(LightGrenade lg) throws IllegalStateException {
		take((Item)lg);
		lightGrenade = null;
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
	
	public boolean hasLightGrenade(){
		return lightGrenade!=null;
	}
}
