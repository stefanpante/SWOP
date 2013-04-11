package item.launchable;

import square.Direction;
import item.Item;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

/**
 * This is a special type of items which can be launched by the player.
 * For example an IdentityDisc.
 * 
 * @author Dieter
 */
public abstract class LaunchableItem extends Item {
	
	/**
	 * The max imum range of the item when launched.
	 * 
	 * @return
	 */
	abstract public int getRange();	
	
	abstract public boolean isCharged();	

	
	/**
	 * Check whether the given travelDirection is a valid travelDirection 
	 * 	for all the Launchable objects.
	 * 
	 * @param   travelDirection
	 *      	The travelDirection to check.
	 * @return  True if and only if the given value is not null 
	 * 			and doesn't represent a diagonal direction
	 */
	public static boolean isValidTravelDirection(Direction travelDirection) {
		if(travelDirection == null){
			return false;
		}
		if (travelDirection.isDiagonal()) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "Launchable ";
	}
}
