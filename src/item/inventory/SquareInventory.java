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


	/**
	 * Creates a square inventory with unlimited size.
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

	public Teleport getTeleport() {
		for(Item i: getAllItems())
			if(Teleport.isTeleport(i)){
				return (Teleport) i;
			}
		return null;
	}

	/**
	 * Returns the string representation
	 */
	@Override
	public String toString() {
		String result = "Square ";
		return result + super.toString();
	}

	public boolean containsSameType(Item item){
		for(Item i: getAllItems()){
			if(item.isSameType(i))
				return true;
		}

		return false;
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		if(containsSameType(lightGrenade))
			throw new IllegalStateException("Can't add another LightGrenade to " + this);

		super.addItem(lightGrenade);
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		if(!getAllItems().contains(lightGrenade))
			throw new IllegalStateException("Cannot remove the lightGrenade because there is none.");

		super.removeItem(lightGrenade);
	}


	@Override
	public void addTeleport(Teleport teleport) throws IllegalStateException {
		if(hasTeleport())
			throw new IllegalStateException("Can't add another Teleport to " + this);
		super.addItem(teleport);
	}

	@Override
	public void removeTeleport(Teleport teleport) throws IllegalStateException {
		throw new IllegalStateException("A teleporter cannot be removed from the Square inventory");
	}

	@Override
	public void addIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		super.addItem(identityDisc);
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		if(!getAllItems().contains(identityDisc))
			throw new IllegalStateException("There are no identity discs to be removed.");

		super.removeItem(identityDisc);
	}

	@Override
	public void addChargedDisc(ChargedIdentityDisc chargedDisc)	throws IllegalStateException {
		super.addItem(chargedDisc);
	} 

	@Override
	public void removeChargedDisc(ChargedIdentityDisc chargedDisc) {
		if(!getAllItems().contains(chargedDisc))
			throw new IllegalStateException("There are no charged identity discs to be removed.");

		super.removeItem(chargedDisc);
	}

	public LightGrenade getLightGrenade() {
		for(Item i: getAllItems()){
			if(LightGrenade.isLightGrenade(i)){
				return (LightGrenade) i;
			}
		}

		return null;
	}
}

