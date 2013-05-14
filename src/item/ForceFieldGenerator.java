package item;

import effect.Effect;
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
public class ForceFieldGenerator extends Item implements Activatable {

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

	@Override
	public Item copy() {
		return new ForceFieldGenerator();
	}

    @Override
    public Effect getEffect() {
        return null;
    }
}
