package item.launchable;

import item.Item;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ChargedIdentityDisc extends IdentityDisc {
	
	/**
	 * The maximum travel distance of an identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = Integer.MAX_VALUE;
	
	public ChargedIdentityDisc(){
		super();
		super.setRange(MAX_TRAVEL_DISTANCE);
	}
	


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
	
	@Override
	public boolean isCharged() {
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Charged Identity Disc";
	}
	
	public static boolean isChargedIdentityDisc(Object o){
		return (o instanceof ChargedIdentityDisc);
	}
	
	public boolean isSameType(Item o){
		return (o instanceof ChargedIdentityDisc);
	}
}
