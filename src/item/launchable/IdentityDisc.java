package item.launchable;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import notnullcheckweaver.NotNull;
import square.Direction;

/**
 * Implementation of the item IdentityDisc
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
@NotNull
public class IdentityDisc extends LaunchableItem {

	/**
	 * The maximum travel distance of an uncharged identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = 3;
	
	/**
	 * Constructor that makes an Identity Disc.
	 */
	public IdentityDisc() {
		
	}
	
	@Override
	public String toString() {
		String result = super.toString() + " IdentityDisc";
		// Removed this, because it gives a weird message in the GUI
		//result = "going " + getTravelDirection() + "(" + getDistanceTraveled()+")";
		return result;
	}

	@Override
	public int getRange() {
		return MAX_TRAVEL_DISTANCE;
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.addIdentityDisc(this);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.removeIdentityDisc(this);
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)	throws IllegalStateException {
		plInv.addIdentityDisc(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.removeIdentityDisc(this);
	}
}
