package item.inventory;

import java.util.ArrayList;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;
import item.launchable.LaunchableItem;
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

	private ArrayList<Integer> launchableHashes;
	
	/**
	 * Creates a new Player Inventory. Same as a regular inventory, 
	 * except limited to six items.
	 */
	public PlayerInventory() {
		super(PLAYER_INVENTORY_SIZE);
		launchableHashes = new ArrayList<Integer>();
	}
	
	@Override
	public void addItem(Item item) throws IllegalStateException{
		item.acceptAddPlayerInventory(this);
	}
	
	@Override
	public void removeItem(Item item) throws IllegalStateException{
		item.acceptRemovePlayerInventory(this);
	}
	
	/**
	 * Returns all the Launchable items of this inventory.
	 * 
	 * @return An ArrayList with all the launchables.
	 */
	public ArrayList<LaunchableItem> getLaunchables(){
		ArrayList<LaunchableItem> result = new ArrayList<LaunchableItem>();
		for(Integer i : launchableHashes){
			result.add((LaunchableItem)this.getItem(i));
		}
		return result;
	}
	
	/**
	 * Returns whether or not this inventory has a launchable.
	 * @return
	 */
	public boolean hasLaunchable(){
		return launchableHashes.size()>0;
	}
	
	/**
	 * Returns a string representation of this PlayerInventory.
	 */
	@Override
	public String toString() {
		return "Player " + super.toString();
	}
	
	@Override
	public void addChargedDisc(ChargedIdentityDisc chargedDisc)
			throws IllegalStateException {
		super.addItem(chargedDisc);
	}

	@Override
	public void removeChargedDisc(ChargedIdentityDisc chargedDisc)
			throws IllegalStateException {
		super.removeItem(chargedDisc);
	}

	@Override
	public void addIdentityDisc(IdentityDisc identityDisc)
			throws IllegalStateException {
		super.addItem(identityDisc);
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc)
			throws IllegalStateException {
		super.removeItem(identityDisc);
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade)
			throws IllegalStateException {
		super.addItem(lightGrenade);
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade)
			throws IllegalStateException {
		super.removeItem(lightGrenade);
	}

	@Override
	public void addTeleport(Teleport teleport) throws IllegalStateException {
		throw new IllegalStateException("A Teleport can not be added to a players Inventory");
	}

	@Override
	public void removeTeleport(Teleport teleport) throws IllegalStateException {
		throw new IllegalStateException("A Teleport can not be in a players Inventory");
	}

	@Override
	public void addLaunchable(LaunchableItem launchable){
		launchableHashes.add(launchable.hashCode());
	}

	@Override
	public void removeLaunchable(LaunchableItem launchable){
		launchableHashes.remove(new Integer(launchable.hashCode()));
	}
}
