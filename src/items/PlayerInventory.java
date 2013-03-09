package items;

/**
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class PlayerInventory extends Inventory {
	public static int PLAYER_INVENTORY_SIZE = 6;

	public PlayerInventory() {
		super(PLAYER_INVENTORY_SIZE);
	}
	
	@Override
	public String toString() {
		return "Player " + super.toString();
	}

}
