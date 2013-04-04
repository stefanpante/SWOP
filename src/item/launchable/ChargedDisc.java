package item.launchable;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ChargedDisc extends IdentityDisc {
	
	/**
	 * The maximum travel distance of an uncharged identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE_UNCHARGED = Integer.MAX_VALUE;
	
	/**
	 * Returns whether the given value is a valid traveled distance for this IdentityDisc.
	 * 
	 * @param 	currentTravelDistance
	 * 			The value to check.
	 * @return	False	If the given distance is negative.
	 * 			True 	otherwise.
	 */
	public boolean canHaveAsDistanceTraveled(int currentTravelDistance) {
		if(currentTravelDistance < 0) 
			return false;
		else
			return true;
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) 
			throws IllegalStateException {
		sqInv.addItem(this);		
	}
	
	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.removeItem(this);
	}
}
