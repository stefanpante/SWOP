package item;

import move.MovableEffect;
import game.Player;
import item.inventory.*;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import move.Movable;
import notnullcheckweaver.NotNull;
import square.Direction;
import square.Square;

/**
 * Implementation of the item IdentityDisc
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
@NotNull
public class IdentityDisc extends Item implements MovableEffect, Movable {

	/**
	 * The maximum travel distance of an uncharged identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = 3;
	
	/**
	 * Constructor that makes an Identity Disc.
	 */

    private int range;

    public IdentityDisc() {
        this(MAX_TRAVEL_DISTANCE);
    }

    public IdentityDisc(int range){
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

	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.addIdentityDisc(this);
	}

	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException {
		sqInv.removeIdentityDisc(this);
	}

	public void acceptAddPlayerInventory(PlayerInventory plInv)	throws IllegalStateException {
		plInv.addIdentityDisc(this);
	}

	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException {
		plInv.removeIdentityDisc(this);
	}

	public boolean isCharged() {
		return false;
	}
	
	public static boolean isIdentityDisc(Object o){
		return (o instanceof IdentityDisc);
	}

    /**
     * Decrease the range by one.
     */
    public void decreaseRange(){
        setRange(getRange()-1);
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
    public void affect(Movable movable) {
        movable.getsAffectedBy(this);
    }

    public void affect(Player player) {
        // TODO: The player is only affected if the IdentityDisk hits him.
        // player.loseTurns(1,false);
    }

    public void affect(IdentityDisc identityDisc) {
    }

	@Override
	public void getsAffectedBy(MovableEffect effect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Square square) throws IllegalStateException {
		square.getInventory().addItem(this);
	}

	@Override
	public void resetRange() {
		this.range = MAX_TRAVEL_DISTANCE;
		
	}
}
