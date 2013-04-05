package item.launchable;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;
import square.Direction;

/**
 * Implementatoin of the item IdentityDisc
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
@NotNull
public class IdentityDisc extends LaunchableItem {

	/**
	 * The maximum travel distance of an uncharged identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE_UNCHARGED = 4;

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
	 * The maxRangePenalty of this IdentityDisc object.
	 */
	private int maxRangePenalty;
	
	/**
	 * One argument constructor that makes an Identity Disc.
	 * 
	 * @effect	setMaxRangePenalty(0)
	 */
	public IdentityDisc() {
		setMaxRangePenalty(0);
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
	public void setDistanceTraveled(int currentTravelDistance)	
			throws IllegalArgumentException {
		if (!isValidDistanceTraveled(currentTravelDistance) || !canHaveAsDistanceTraveled(currentTravelDistance)) {
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
	 * Returns whether the given value is a valid traveled distance for this IdentityDisc.
	 * 
	 * @param 	currentTravelDistance
	 * 			The value to check.
	 * @return	False	When this disc is charged and the given value is 
	 * 					greater than the maximum plus the current penalty.
	 * 			True 	otherwise.
	 */
	public boolean canHaveAsDistanceTraveled(int currentTravelDistance) {
		return currentTravelDistance <= MAX_TRAVEL_DISTANCE_UNCHARGED + getMaxRangePenalty();		
	}

	/**
	 * Check whether the given currentTravelDistance is a valid currentTravelDistance for all the objects of IdentityDisc.
	 * @param 	currentTravelDistance
	 *			The currentTravelDistance to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidDistanceTraveled(int currentTravelDistance) {
		if (currentTravelDistance < 0) {
			return false;
		}
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

	/**
	 * Returns the value of the maxRangePenalty of this IdentityDisc as an int.
	 *
	 * @return 	An object of the int class.
	 * 			| int
	 */
	public int getMaxRangePenalty() {
		return maxRangePenalty;
	};

	/**
	 * Sets the value of the maxRangePenalty of IdentityDisc if the given value is valid. 
	 * 
	 * @param 	maxRangePenalty
	 *			The maxRangePenalty to set.
	 * @post 	The given value is the current value of the maxRangePenalty of this IdentityDisc.
	 * 			| maxRangePenalty == new.getMaxRangePenalty()
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid maxRangePenalty.
	 *			| !isValidMaxRangePenalty(maxRangePenalty)
	 */
	public void setMaxRangePenalty(int maxRangePenalty)
			throws IllegalArgumentException {
		if (!isValidMaxRangePenalty(maxRangePenalty)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ maxRangePenalty
							+ ") is not a valid agrument of the field maxRangePenalty from the class IdentityDisc");
		}
		this.maxRangePenalty = maxRangePenalty;
	};

	/**
	 * Check whether the given maxRangePenalty is a valid maxRangePenalty for all the objects of IdentityDisc.
	 * @param 	maxRangePenalty
	 *			The maxRangePenalty to check.
	 * @return	True if and only if the given value is an integer smaller or equal to zero.
	 * 			|	maxRangePenalty <= 0
	 */
	public static boolean isValidMaxRangePenalty(int maxRangePenalty) {
		if (maxRangePenalty > 0) {
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
		return MAX_TRAVEL_DISTANCE_UNCHARGED;
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

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.addItem(this);
	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.removeItem(this);
	}
}
