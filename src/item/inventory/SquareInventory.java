package item.inventory;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;
import item.visitor.AddRemoveItemVisitor;

/**
 * This class describes the inventory of a square. 
 * 	With the constructor you have to state if this inventory can only have inactive Items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class SquareInventory extends Inventory implements AddRemoveItemVisitor {
	
	/**
	 * Holds the only possible Teleport in this SquareInventory.
	 */
	private Teleport teleport;
	/**
	 * Holds the only possible LightGrenade in this SquareInventory.
	 */
	private LightGrenade lightGrenade;

	/**
	 * Creates a square inventory with the given size. 
	 * 
	 * @param 	size 
	 * 			The size of this inventory.
	 * @param	onlyInactiveItems
	 * 			Boolean that states whether this inventory can only have inactive Items.
	 * @effect	super(size)
	 */
	public SquareInventory(int size) {
		super(size);
		teleport = null;	
		lightGrenade = null;
	}

	/**
	 * Creates a square inventory with unlimited size.
	 * 
	 * @param	onlyInactiveItems
	 * 			Boolean that states whether this inventory can only have inactive Items.
	 * @effect	super(size)
	 */
	public SquareInventory() {
		super();	
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
		super.addItem(item);
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
	public void take(Item item) throws IllegalStateException {
		if(!isValidItem(item))
			throw new IllegalStateException("This"
											+ item
											+ "can not be removed from this "
											+ this);
		item.acceptRemoveSquareInventory(this);
		super.take(item);
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
	 * Returns the string representation
	 */
	@Override
	public String toString() {
		String result = "Square ";
		return result + super.toString();
	}

	@Override
	public void removeItem(Item it) {
		//NO op.
	}

	@Override
	public void addChargedDisc(ChargedDisc chargedDisc) {
		//No specific operation needed for an IdenityDisc.		
	}

	@Override
	public void removeChargedDisc(ChargedDisc chargedDisc) {
		//No specific operation needed for an IdenityDisc.		
	}

	@Override
	public void addIdentityDisc(IdentityDisc identityDisc) {
		//No specific operation needed for an IdenityDisc.
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc) {
		//No specific operation needed for an IdenityDisc.
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade) {
		if(hasLightGrenade())
			throw new IllegalArgumentException("Can't add another LightGrenade to " + this);
		this.lightGrenade = lightGrenade;
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade) {
		this.lightGrenade = null;
	}

	@Override
	public void addTeleport(Teleport teleport) {
		if(hasLightGrenade())
			throw new IllegalArgumentException("Can't add another Teleport to " + this);
		this.teleport = teleport;
	}

	@Override
	public void removeTeleport(Teleport teleport) {
		this.teleport = null;
	} 
}