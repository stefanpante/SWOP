package item.launchable;

import effect.player.PlayerEffect;
import game.Player;
import item.Item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import notnullcheckweaver.NotNull;

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
		super(MAX_TRAVEL_DISTANCE);
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

	@Override
	public boolean isCharged() {
		return false;
	}
	
	public static boolean isIdentityDisc(Object o){
		return (o instanceof IdentityDisc);
	}
	
	@Override
	public boolean isSameType(Item o){
		return (o instanceof IdentityDisc);
	}
	
	@Override
	public String toString() {
		String result = super.toString() + " IdentityDisc";
		// Removed this, because it gives a weird message in the GUI
		//result = "going " + getTravelDirection() + "(" + getDistanceTraveled()+")";
		return result;
	}

    @Override
    public void affect(Player player) {
        player.loseTurns(1,false);
    }
}
