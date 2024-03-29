package item;

import effect.Effect;
import effect.imp.EmptyEffect;
import item.inter.Movable;
import notnullcheckweaver.NotNull;
import org.jetbrains.annotations.Nullable;
import square.Square;
import util.Direction;

import java.util.ArrayList;

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
	public static final int MAX_TRAVEL_DISTANCE = 3;
	
	/**
	 * Constructor that makes an Identity Disc.
	 */

    private int range;
    
    private boolean justTeleported;
    
    private boolean active;
    
    /**
     * The position of the movable.
     */
    private Square currentPosition;
    private Square previousPosition;

    public IdentityDisc() {
        this(MAX_TRAVEL_DISTANCE);
    }

    private IdentityDisc(int range){
        this.range = range;
        this.active = false;
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
     * Sets the range for this Launchable Square
     * @param range	the range
     * @throws IllegalArgumentException
     * 		   thrown when the range is smaller than zero.
     */
    void setRange(int range) throws IllegalArgumentException{
        if(isNotValidRange(range)){
            throw new IllegalArgumentException("Range should never be negative");
        }

        this.range = range;
    }

    /**
     * Checks whether the range is larger than zero.
     * @param range  the range for the identity disc.
     */
    boolean isNotValidRange(int range){
        return (range < 0);
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
    public static boolean isValidTravelDirection(@Nullable Direction travelDirection) {
        return travelDirection != null && !travelDirection.isDiagonal();
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
    public boolean canAddTo(@NotNull Square square) {
        return (!square.hasItem(this));
    }

    public boolean isActive(){
    	return this.active;
    }
    
    public void deactivate(){
    	this.active = false;
    }
    
    public void activate(){
    	this.active = true;
    }

    @Override
	public String toString() {
		return super.toString() + "IdentityDisc";
	}

    void setPreviousPosition(Square position){
        this.previousPosition = position;
    }

	@Override
	public void move(Square square) throws IllegalStateException {
        if(!currentPosition.getNeighbors().containsValue(square))
            throw new IllegalStateException("Cannot move to non neighboring square");
        setPosition(square);
        try{
            currentPosition.addItem(this);
            this.currentPosition.affect(this);
        }catch (Exception e){
            throw e;
        }
	}

    @Override
    public void setPosition(Square square) {
            setPreviousPosition(getPosition());
            this.currentPosition = square;
     }

    @Override
    public void resetPosition(Square square) {
        this.currentPosition = square;
//        currentPosition.addSquareEffect(getPlayerEffect());
    }



	@Override
	public void resetRange() {
		this.range = MAX_TRAVEL_DISTANCE;
	}

	@Override
	public Square getPosition() {
		return currentPosition;
	}

    @Override
    public Square getPreviousPosition() {
        return previousPosition;
    }

    @NotNull
    @Override
    public ArrayList<Effect> getEffects() {
        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(new EmptyEffect());
        return effects;
    }

}
