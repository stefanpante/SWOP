package items.inventory;

/**
 * A player inventory.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PlayerInventory extends Inventory {
	
	/**
	 * The maximum size 
	 */
	public static int PLAYER_INVENTORY_SIZE = 6;

	/**
	 * Creates a new Player Inventory. Same as a regular inventory, 
	 * except limited to six items.
	 */
	public PlayerInventory() {
		super(PLAYER_INVENTORY_SIZE);
	}
	
	/**
	 * Returns a string representation of this PlayerInventory.
	 */
	@Override
	public String toString() {
		return "Player " + super.toString();
	}
}
