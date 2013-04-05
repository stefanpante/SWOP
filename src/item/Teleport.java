package item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

/**
 * Implements a teleport with a certain destination.
 * 
 * @author jonas, Dieter, Vincent
 */
public class Teleport extends Item {
	
	/**
	 * The destination of this teleport.
	 */
	private Teleport destination;
	
	/**
	 * Zero argument constructor that makes a new Teleport with no destination.
	 */
	public Teleport(){
		
	}
	
	/**
	 * Constructor that gets an initial destination as argument.
	 */
	public Teleport (Teleport destination) throws IllegalArgumentException {
		setDestination(destination);
	}

	/**
	 * The destination of this teleport.
	 * 
	 * @return the destination
	 */
	public Teleport getDestination() {
		return destination;
	}

	/**
	 * Sets the destination of this Teleport to the given destination
	 * 	if that is a valid one.
	 * 
	 * @param 	destination 
	 * 			The destination to set.
	 * @throws	IllegalArgumentException
	 * 			If the given destination is not valid.
	 * 			| !isValidDestination(destination) || !canHaveAsDestination(destination)
	 */
	public void setDestination(Teleport destination) throws IllegalArgumentException {
		if(!isValidDestination(destination) || !canHaveAsDestination(destination))
			throw new IllegalArgumentException("The given destination is not a valid one for this Teleport");
		this.destination = destination;
	}

	/**
	 * Checks whether the given destination is a valid destination for this
	 * Teleport.
	 * 
	 * @param 	destination
	 * 			The destination to check
	 * @return	True if and only if the destination is not this teleport.
	 */
	private boolean canHaveAsDestination(Teleport destination) {
		return !destination.equals(this);
	}
	
	/**
	 * Checks whether the given teleport is a valid destination for all Teleports.
	 * 
	 * @param 	destination
	 * 			The destination to check
	 * @return	True if and only if the destination is not null.
	 */
	public static boolean isValidDestination(Teleport destination){
		return destination != null;
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory squareInventory) 
			throws IllegalStateException {
		squareInventory.addItem(this);		
	}
	
	@Override
	public void acceptRemoveSquareInventory(SquareInventory squareInventory)
			throws IllegalStateException {
		squareInventory.removeItem(this);
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory playerInventory)
			throws IllegalStateException {
		playerInventory.addItem(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory playerInventory)
			throws IllegalStateException {
		playerInventory.removeItem(this);
	}
	
}
