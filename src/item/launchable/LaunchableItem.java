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
	
	private int range;
	
	/**
	 * 
	 */
	
	public LaunchableItem(int range){
		this.range = range;
	}
	/**
	 * The maximum range of the item when launched.
	 * 
	 * @return
	 */
	public int getRange(){
		return this.range;
	}
	
	/**
	 * Sets the range for this Launchable Item
	 * @param range	the range
	 * @throws IllegalArgumentException
	 * 		   thrown when the range is smaller than zero.
	 */
	public void setRange(int range) throws IllegalArgumentException{
		if(!isValidRange(range)){
			throw new IllegalArgumentException("Range should never be negative");
		}
		
		this.range = range;
	}
	
	/**
	 * Checks whether the range is larger than zero.
	 * @param range
	 * @return
	 */
	public boolean isValidRange(int range){
		return (range >= 0);
	}
	
	public abstract boolean isCharged();	

	
	//TODO: Moet dit niet abstract zijn? Als er nieuwe launchable items bijkomen
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
