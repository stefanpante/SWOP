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
 * A player inventory.
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 */
public class PlayerInventory extends Inventory implements AddRemoveItemVisitor{
	
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
	public void addItem(Item item) throws IllegalStateException{
		item.acceptAddPlayerInventory(this);
	}
	
	@Override
	public void removeItem(Item item) throws IllegalStateException{
		item.acceptRemovePlayerInventory(this);
	}
	
	/**
	 * Returns a string representation of this PlayerInventory.
	 */
	@Override
	public String toString() {
		return "Player " + super.toString();
	}
	
	@Override
	public void addChargedDisc(ChargedIdentityDisc chargedDisc) throws IllegalStateException {
		super.addItem(chargedDisc);
	}

	@Override
	public void removeChargedDisc(ChargedIdentityDisc chargedDisc) throws IllegalStateException {
		super.removeItem(chargedDisc);
	}

	@Override
	public void addIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		super.addItem(identityDisc);
	}

	@Override
	public void removeIdentityDisc(IdentityDisc identityDisc) throws IllegalStateException {
		super.removeItem(identityDisc);
	}

	@Override
	public void addLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		super.addItem(lightGrenade);
	}

	@Override
	public void removeLightGrenade(LightGrenade lightGrenade) throws IllegalStateException {
		super.removeItem(lightGrenade);
	}

    @Override
    public void addForceFieldGenerator(ForceFieldGenerator forceFieldGenerator) throws IllegalStateException{
        super.addItem(forceFieldGenerator);
    }
    
    @Override
    public void removeForceFieldGenerator(ForceFieldGenerator forceFieldGenerator) throws IllegalStateException{
        super.removeItem(forceFieldGenerator);
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
	public void addFlag(Flag flag) throws IllegalStateException {
		if(this.containsSameType(flag)){
			throw new IllegalStateException("A player cannot carry more than one flags.");
		}
		
		super.addItem(flag);
	}

	@Override
	public void removeFlag(Flag flag) throws IllegalStateException {
		super.removeItem(flag);
		
	}
}
