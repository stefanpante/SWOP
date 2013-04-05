package item.inventory;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;
import item.visitor.AddRemoveItemVisitor;

/**
 * A player inventory.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PlayerInventory extends Inventory implements AddRemoveItemVisitor{
	
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
	
	@Override
	public void addItem(Item item) throws IllegalStateException{
		item.acceptAddPlayerInventory(this);
		super.addItem(item);
	}
	
	/**
	 * Returns a string representation of this PlayerInventory.
	 */
	@Override
	public String toString() {
		return "Player " + super.toString();
	}
	
	@Override
	public void addChargedDisc(ChargedDisc chargedDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeChargedDisc(ChargedDisc chargedDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void addIdentityDisc(IdentityDisc identityDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void addTeleport(Teleport teleport) throws IllegalStateException {
		throw new IllegalStateException("A Teleport can not be added to a players Inventory");
	}

	@Override
	public void removeTeleport(Teleport teleport) throws IllegalStateException {
		//No specific operation needed yet.
	}
}
