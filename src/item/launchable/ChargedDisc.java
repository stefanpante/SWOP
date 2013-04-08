package item.launchable;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ChargedDisc extends IdentityDisc {
	
	/**
	 * The maximum travel distance of an identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = Integer.MAX_VALUE;

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.addChargedDisc(this);		
	}
	
	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.removeChargedDisc(this);
	}
	
	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.addChargedDisc(this);		
	}
	
	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)	throws IllegalStateException {
		plInv.removeChargedDisc(this);
	}
}
