package item.launchable;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;
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
	public static int MAX_TRAVEL_DISTANCE = 4;

	/**
	 * The currentTravelDistance of this IdentityDisc object.
	 */
	private int currentTravelDistance;
	
	/**
	 * The travelDirection of this IdentityDisc object.
	 */
	@Nullable
	private Direction travelDirection;
	
	/**
	 * Constructor that makes an Identity Disc.
	 */
	public IdentityDisc() {
		
	}
	
	/**
	 * Returns the value of the currentTravelDistance of this IdentityDisc as an int.
	 *
	 * @return 	An object of the int class.
	 * 			| int
	 */
	public int getDistanceTraveled() {
		return currentTravelDistance;
	};

	/**
	 * Sets the value of the currentTravelDistance of IdentityDisc if the given value is valid. 
	 * 
	 * @param 	currentTravelDistance
	 *			The currentTravelDistance to set.
	 * @post 	The given value is the current value of the currentTravelDistance of this IdentityDisc.
	 * 			| currentTravelDistance == new.getTravelDistance()
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid currentTravelDistance.
	 *			| !isValidDistanceTraveled(currentTravelDistance)
	 */
	public void setDistanceTraveled(int currentTravelDistance)	throws IllegalArgumentException {
		if (!isValidDistanceTraveled(currentTravelDistance)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ currentTravelDistance
							+ ") is not a valid agrument of the field currentTravelDistance from the class IdentityDisc");
		}
		
		this.currentTravelDistance = currentTravelDistance;
	};
	
	/**
	 * Increments the traveled distance by one.
	 * 
	 * @effect	setDistanceTraveled(getDistanceTraveled() +1)
	 */
	public void incrementDistanceTraveled() throws IllegalArgumentException {
		setDistanceTraveled(getDistanceTraveled() +1);
	}
	
	/**
	 * Check whether the given currentTravelDistance is a valid currentTravelDistance for all the objects of IdentityDisc.
	 * 
	 * @param 	distance
	 *			The currentTravelDistance to check.
	 * @return	True	If the value is not less than 0 and less than the maximum allowed distance for this disc.
	 * 			False	If the value is less than 0 or greater than the maximum allowed distance.
	 */
	public boolean isValidDistanceTraveled(int distance) {
		if(distance < 0)
			return false;
		
		if(distance > getRange())
			return false;
		
		return true;
	}
	
	/**
	 * Returns the value of the travelDirection of this IdentityDisc as an Direction.
	 *
	 * @return 	An object of the Direction class.
	 * 			| Direction
	 */
	public Direction getTravelDirection() {
		return travelDirection;
	};

	/**
	 * Sets the value of the travelDirection of IdentityDisc if the given value is valid. 
	 * 
	 * @param 	travelDirection
	 *			The travelDirection to set.
	 * @post 	The given value is the current value of the travelDirection of this IdentityDisc.
	 * 			| travelDirection == new.getTravelDirection()
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid travelDirection.
	 *			| !isValidTravelDirection(travelDirection)
	 */
	public void setTravelDirection(Direction travelDirection)
			throws IllegalArgumentException {
		if (!isValidTravelDirection(travelDirection)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ travelDirection
							+ ") is not a valid agrument of the field travelDirection from the class IdentityDisc");
		}
		this.travelDirection = travelDirection;
	};

	/**
	 * Check whether the given travelDirection is a valid travelDirection for all the objects of IdentityDisc.
	 * @param 	travelDirection
	 *			The travelDirection to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidTravelDirection(Direction travelDirection) {
		if (travelDirection.isDiagonal()) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String result = super.toString() + " IdentityDisc";
		result = "going " + getTravelDirection() + "(" + getDistanceTraveled()+")";
		return result;
	}

	@Override
	public int getRange() {
		return MAX_TRAVEL_DISTANCE;
	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.addIdentityDisc(this);
		super.acceptAddSquareInventory(sqInv);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.removeIdentityDisc(this);
		super.acceptRemoveSquareInventory(sqInv);
	}

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)	throws IllegalStateException {
		plInv.addIdentityDisc(this);
		super.acceptAddPlayerInventory(plInv);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.removeIdentityDisc(this);
		super.acceptRemovePlayerInventory(plInv);
	}
}
