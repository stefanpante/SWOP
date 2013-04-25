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
 * This class describes the inventory of a square. 
 * 	With the constructor you have to state if this inventory can only have inactive Items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class SquareInventory extends Inventory implements AddRemoveItemVisitor {
	

	private ArrayList<Integer> launchableHashes;
	
	/**
	 * Holds the only possible Teleport in this SquareInventory.
	 */
	private Teleport teleport;
	/**
	 * Holds the only possible LightGrenade in this SquareInventory.
	 */
	private LightGrenade lightGrenade;
	
	/**
	 * Creates a square inventory with unlimited size.
	 * 
	 * @param	onlyInactiveItems
	 * 			Boolean that states whether this inventory can only have inactive Items.
	 * @effect	super(size)
	 */
	public SquareInventory() {
		super();
		this.teleport = null;	
		this.lightGrenade = null;
		this.launchableHashes = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a given item to this SquareInventory if possible. 
	 * 
	 * @param 	item
	 * 			The item to add.
	 * @throws	IllegalStateException
	 * 			If the given item is not a valid item 
	 * 			for any SquareInventory or for this one.
	 * 			| !isValidItem(item) || !canHaveAsItem(item)
	 * @effect	| super.addItem(item)
	 * @effect	| item.acceptAddSquareInventory(this)
	 */
	@Override
	public void addItem(Item item) throws IllegalStateException {
		if(!isValidItem(item) || !canHaveAsItem(item))
			throw new IllegalStateException("This "
											+ item
											+ " is not a valid item for this "
											+ this);
		item.acceptAddSquareInventory(this);
	}	
	
	/**
	 * Takes a given from this SquareInventory if possible. 
	 * 
	 * @param 	item
	 * 			The item to remove.
	 * @throws	IllegalStateException
	 * 			If the given item is not a valid item 
	 * 			for any SquareInventory or for this one.
	 * 			| !isValidItem(item)
	 * @effect	| super.addItem(item)
	 * @effect	| item.acceptRemoveSquareInventory(this)
	 */
	@Override
	public void removeItem(Item item) throws IllegalStateException {
		if(!isValidItem(item))
			throw new IllegalStateException("This"
											+ item
											+ "can not be removed from this "
											+ this);
		
		item.acceptRemoveSquareInventory(this);	
	}
	
	/**
	 * Returns whether the given item can be used as an item of this UsedItemInventory.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if the conditions of superclass Inventory still hold and 
	 * 			this inventory doesn't already have a LightGrenade.
	 * 			| super.canHaveAsItem(item) &&
	 * 			| ! hasLightGrenade()
	 */
	@Override
	public boolean canHaveAsItem(Item item){
		if(!super.canHaveAsItem(item)){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns whether this SquareInventory has a LightGrenade.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if there is already a LightGrenade in this inventory
	 */
	public boolean hasLightGrenade(){
		if(getLightGrenade() == null)
			return false;
		return true;
	}
	
	/**
	 * Returns whether this SquareInventory
	 * @return
	 */
	public boolean hasTeleport(){
		if(getTeleport() == null)
			return false;
		return true;
	}
	
	/**
	 * Returns the only light grenade in this inventory.
	 * 
	 * @return	The LightGrenade in this inventory if there is none returns null.
	 */
	public LightGrenade getLightGrenade(){
		return this.lightGrenade;
	}
	
	/**
	 * Returns the only Teleport in this inventory.
	 * @return
	 */
	public Teleport getTeleport(){
		return this.teleport;
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
	 * Returns the string representation
	 */
	@Override
	public String toString() {
		String result = "Square ";
		return result + super.toString();
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		if(hasLightGrenade())
			throw new IllegalStateException("Can't add another LightGrenade to " + this);
		
		super.addItem(lightGrenade);
		this.lightGrenade = lightGrenade;
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		if(this.lightGrenade == null)
			throw new IllegalStateException("Cannot remove the lightGrenade because there is none.");
		
		if(!this.lightGrenade.equals(lightGrenade))
			throw new IllegalStateException("Could not remove the LightGrenade because it did not match the LightGrenade that is in place.");
		
		super.removeItem(lightGrenade);
		this.lightGrenade = null;
	}

	@Override
	public void addTeleport(Teleport teleport) throws IllegalStateException {
		if(hasTeleport())
			throw new IllegalStateException("Can't add another Teleport to " + this);
		super.addItem(teleport);
		this.teleport = teleport;
	}

	@Override
	public void removeTeleport(Teleport teleport) throws IllegalStateException {
		if(this.teleport == null)
			throw new IllegalStateException("Cannot remove the teleport, because there is none.");
		
		if(!this.teleport.equals(teleport))
			throw new IllegalStateException("The given teleport to be removed is not the same one as the square's teleport.");
		
		super.removeItem(teleport);
		this.teleport = null;
	}
	
	@Override
	public void addIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		super.addItem(identityDisc);
		launchableHashes.add(identityDisc.hashCode());
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		if(!hasLaunchable())
			throw new IllegalStateException("There are no identity discs to be removed.");
		
		super.removeItem(identityDisc);
		launchableHashes.remove(new Integer(identityDisc.hashCode()));
	}

	@Override
	public void addChargedDisc(ChargedIdentityDisc chargedDisc)	throws IllegalStateException {
		super.addItem(chargedDisc);
		launchableHashes.add(chargedDisc.hashCode());
	} 
	
	@Override
	public void removeChargedDisc(ChargedIdentityDisc chargedDisc) {
		if(!hasLaunchable())
			throw new IllegalStateException("There are no charged identity discs to be removed.");
		
		super.removeItem(chargedDisc);
		launchableHashes.remove(new Integer(chargedDisc.hashCode()));
	}
}
