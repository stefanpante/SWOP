/**
 * 
 */
package item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import square.Square;

/**
 * @author jonas
 *
 */
public class Teleport extends Item {
	
	
	private Teleport destination;
	
	public Teleport(){
		
	}
	
	public Teleport (Teleport desitination) throws IllegalArgumentException {
		setDestination(desitination);
	}

	/**
	 * @return the destination
	 */
	public Teleport getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Teleport destination) throws IllegalArgumentException {
		if(!isValidDestionation(destination))
			throw new IllegalArgumentException();
		this.destination = destination;
	}

	/**
	 * Checks whether the given teleport is a valid destination for this
	 * teleport.
	 * 
	 * @param 	destination
	 * 			The destination to check
	 * @return	True if and only if the square is nut null
	 * 			and not obstructed
	 */
	private boolean isValidDestionation(Teleport destination) {
		return destination != null;
	}
	
	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv) 
			throws IllegalStateException {
		plInv.addItem(this);		
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) 
			throws IllegalStateException {
		sqInv.addItem(this);		
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.take(this);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.removeItem(this);
	}
	
}
