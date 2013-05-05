package item.inventory;

import item.ChargedIdentityDisc;
import item.Flag;
import item.ForceFieldGenerator;
import item.IdentityDisc;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.visitor.AddRemoveItemVisitor;

/**
 * This class describes the inventory of a square. 
 * 	With the constructor you have to state if this inventory can only have inactive Items.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
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
		item.setInventory(this);
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
	 * Returns whether this SquareInventory has a LightGrenade.
	 * 			The item to check.
	 * @return	True if and only if there is already a LightGrenade in this inventory
	 */
	public boolean hasLightGrenade() {
        return getLightGrenade() != null;
    }

	/**
	 * Returns whether this SquareInventory
	 */
	public boolean hasTeleport(){
        return getTeleport() != null;
    }
	
	/**
	 * Returns the teleport if there is one, otherwise null.
	 */
	public Teleport getTeleport() {
		for (Item i: getAllItems()) {
			if (Teleport.isTeleport(i))
				return (Teleport) i;
		}
	
		return null;
	}
	
	/**
	 * Returns the force field generator if there is one.
	 */
	public ForceFieldGenerator getForceFieldGenerator() {
		for (Item i: getAllItems()) {
			if(ForceFieldGenerator.isForceFieldGenerator(i))
				return (ForceFieldGenerator) i;
		}
		
		return null;
	}
	
	/**
	 * Checks if the inventory has a force field generator.
	 */
	public boolean hasForceFieldGenerator() {
		for(Item item: getAllItems()){
			if (ForceFieldGenerator.isForceFieldGenerator(item))
				return true;
		}
		
		return false;
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
		for (Item i: getAllItems()) {
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
	public void removeChargedDisc(ChargedIdentityDisc chargedDisc) throws IllegalStateException{
		if(!getAllItems().contains(chargedDisc))
			throw new IllegalStateException("There are no charged identity discs to be removed.");

		super.removeItem(chargedDisc);
	}

    @Override
    public void addForceFieldGenerator(ForceFieldGenerator forceFieldGenerator) throws IllegalStateException{
        if (containsSameType(forceFieldGenerator))
            throw new IllegalStateException("Cannot add another ForcefieldGenerator to the inventory");

        super.addItem(forceFieldGenerator);
    }
    
    @Override
    public void removeForceFieldGenerator(ForceFieldGenerator forceFieldGenerator) throws IllegalStateException{
        if (!getAllItems().contains(forceFieldGenerator))
            throw new IllegalStateException("Cannot remove a ForceFieldGenerator that is not in the inventory");
        
        super.removeItem(forceFieldGenerator);
    }
    
	public LightGrenade getLightGrenade() {
		for (Item i: getAllItems()) {
			if (LightGrenade.isLightGrenade(i))
				return (LightGrenade) i;
		}

		return null;
	}

	public boolean hasIdentityDisc() {
		for (Item item: getAllItems()) {
			if (IdentityDisc.isIdentityDisc(item))
				return true;
		}
		
		return false;
	}

	@Override
	public void addFlag(Flag flag) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFlag(Flag flag) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}
	
}

