package item;

import game.Player;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
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
public class ForceFieldGenerator extends Item implements Activatable, Observer{

    private boolean dropped;
    private boolean active;

	/**
	 * If the item is used, the state is changed to used.
	 */
	@Override
	public void notifyUse() {
		this.drop();
	}

	@Override
	public boolean isSameType(Item item) {
        return item instanceof ForceFieldGenerator;
    }
	
	/**
	 * Checks if the item given is a force field generator.
	 */
	public static boolean isForceFieldGenerator(Item item){
        return item instanceof ForceFieldGenerator;
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

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void drop() throws IllegalStateException {
        this.dropped = true;
    }

    @Override
    public void activate() throws IllegalStateException {
        this.active = true;
    }
}
