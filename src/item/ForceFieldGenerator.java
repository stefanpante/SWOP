package item;

import game.Player;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import item.launchable.IdentityDisc;
import move.Movable;

import java.util.Observable;
import java.util.Observer;

/**
 * The ForceFieldGenerator generates a line segment between
 * another force field generator if it is within an appropriate distance
 * 
 * The Generator holds a reference to the line segment.
 * This line segment switches on and off.
 * 
 * @author vincentreniers
 */
public class ForceFieldGenerator extends Item implements Observer{
    
	/**
	 * If the generator has been dropped this is true,
	 * otherwise false.
	 */
	private boolean isUsed = false;
	
	/**
	 * If the item is used, the state is changed to used.
	 */
	@Override
	public void notifyUse() {
		this.isUsed = true;
	}
	
	/**
	 * Returns true if the item has been used,
	 * false if the item has not been dropped yet.
	 */
	public boolean isUsed(){
		return this.isUsed;
	}

	@Override
	public boolean isSameType(Item item) {
		if(item instanceof ForceFieldGenerator)
			return true;
		return false;
	}
	
	/**
	 * Checks if the item given is a force field generator.
	 */
	public static boolean isForceFieldGenerator(Item item){
		if(item instanceof ForceFieldGenerator)
			return true;
		return false;
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.addForceFieldGenerator(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.removeForceFieldGenerator(this);
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.addForceFieldGenerator(this);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)	throws IllegalStateException {
		sqInv.removeForceFieldGenerator(this);
	}

    @Override
    public void affect(Movable movable) {
        movable.getsAffectedBy(this);
    }

    @Override
    public void affect(Player player) {
        // TODO: Implement the effect of a player entering this force field
    }

    @Override
    public void affect(IdentityDisc identityDisc) {
    	
    }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
    
}
