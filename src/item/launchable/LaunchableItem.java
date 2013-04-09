package item.launchable;

import item.Item;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

/**
 * This is a special type of items which can be launched by the player.
 * For example an IdentityDisc.
 * 
 * @author vincentreniers
 */
public abstract class LaunchableItem extends Item {
	
	/**
	 * The max imum range of the item when launched.
	 * 
	 * @return
	 */
	abstract public int getRange();
	
	
	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)	throws IllegalStateException {
		plInv.addLaunchable(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.removeLaunchable(this);
	}
	
	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv)	throws IllegalStateException {
		sqInv.addLaunchable(this);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.removeLaunchable(this);
	}
	
	@Override
	public String toString() {
		return super.toString() + "Launchable ";
	}
}
