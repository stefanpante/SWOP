package item;

import command.effect.DropFlagCommand;
import game.Player;
import effect.Effect;
import item.inter.Movable;
import notnullcheckweaver.NotNull;
import util.Direction;
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
    
    private boolean active;
    
    /**
     * The position of the movable.
     */
    private Square position;

    public IdentityDisc() {
        this(MAX_TRAVEL_DISTANCE);
    }

    public IdentityDisc(int range){
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
    public boolean canAddTo(Square square) {
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
		return super.toString() + " IdentityDisc";
	}

	@Override
	public void move(Square square) throws IllegalStateException {
        if(!position.getNeighbors().containsValue(square))
            throw new IllegalStateException("Cannot move to non neighboring square");
        position.acceptMove(this);
        setPosition(square);
	}
	
	public void setPosition(Square square){
        getContainer().removeItem(this);
        square.addItem(this);
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
    public void acceptStandOnEffect(Effect effect) {
        effect.onStandOnEffect(this);
     }
    
    @Override
    public void acceptMoveToEffect(Effect effect){
    	effect.onMoveToEffect(this);
    }

	@Override
	public void onMoveToEffect(Movable movable) {
		movable.acceptMoveToEffect(this);
		
	}

	@Override
	public void onStandOnEffect(Movable movable) {
		movable.acceptStandOnEffect(this);
	}

	@Override
	public void onStandOnEffect(Player player) {
		if(isActive()){
			//FIXME: Not sure about the false.
			player.loseTurns(1, false);
		}
		this.deactivate();
		
		
	}
}
