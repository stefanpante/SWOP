package item.inventory;

import item.*;
import item.visitor.AddRemoveItemVisitor;

/**
 * A player inventory.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 */
public class PlayerInventory extends Inventory {
	
	/**
	 * The maximum size 
	 */
	public static final double PLAYER_INVENTORY_SIZE = 6;
	
	/**
	 * Creates a new Player Inventory. Same as a regular inventory, 
	 * except limited to six items.
	 */
	public PlayerInventory() {
		super(PLAYER_INVENTORY_SIZE);
	}

    @Override
    public boolean hasType(Item item) {
        return getType(item) != null;
    }

    @Override
    public Item getType(Item item) {
        for(Item it : getAllItems()){
            if(item.isSameType(it))
                return it;
        }
        return null;
    }

    /**
	 * Returns a string representation of this PlayerInventory.
	 */
	@Override
	public String toString() {
		return "Player " + super.toString();
	}



}
