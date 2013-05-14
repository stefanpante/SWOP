package item;

import effect.Effect;
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
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 *
 */
@NotNull
public class IdentityDisc extends Item implements Movable {

	/**
	 * The maximum travel distance of an uncharged identity disc.
	 */
	public static int MAX_TRAVEL_DISTANCE = 3;
	
	/**
	 * Constructor that makes an Identity Disc.
	 */

    private int range;
    
    private boolean justTeleported;
    
    /**
     * The position of the movable.
     */
    private Square position;

    public IdentityDisc() {
        this(MAX_TRAVEL_DISTANCE);
    }

    public IdentityDisc(int range){
        this.range = range;
        this.setJustTeleported(false);
    }


    /**
     * The maximum range of the item when launched.
     *
     * @return the range of the identitydisc
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
     * @param range  the range for the identity disc.
     */
    public boolean isValidRange(int range){
        return (range >= 0);
    }

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
        return travelDirection != null && !travelDirection.isDiagonal();
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
		return super.toString() + " IdentityDisc";
	}

	@Override
	public void getsAffectedBy(MovableEffect effect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Square square) throws IllegalStateException {
		this.getInventory().removeItem(this);
		square.getInventory().addItem(this);
		this.position = square;
		square.affect(this);
    	this.setJustTeleported(false);
	}
	
	public void setPosition(Square square){
		this.getInventory().removeItem(this);
		square.getInventory().addItem(this);
		this.position = square;
	}

	@Override
	public void resetRange() {
		this.range = MAX_TRAVEL_DISTANCE;
	}

	@Override
	public Square getPosition() {
		return position;
	}

	@Override
	public boolean justTeleported() {
		return justTeleported;
	}

	@Override
	public void setJustTeleported(boolean b) {
		justTeleported = b;
	}

	@Override
	public Item copy() {
		return new IdentityDisc();
	}

    @Override
    public Effect getEffect() {
        return null;
    }
}
