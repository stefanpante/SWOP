package item;

import item.inter.Movable;
import command.effect.DropFlagCommand;
import game.Player;
import square.Square;

/**
 * Implements a teleport with a certain destination.
 * @author jonas, Dieter
 *
 */
public class Teleport extends Item {

    /**
     * The destination of this teleport.
     */
    private Square destination;

    /**
     * Zero argument constructor that makes a new Teleport with no destination.
     */
    public Teleport(){

    }

    /**
     * The destination of this teleport.
     *
     * @return the destination
     */
    public Square getDestination() {
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
    public void setDestination(Square destination) throws IllegalArgumentException {
        if(!canHaveAsDestination(destination))
            throw new IllegalArgumentException("The given destination is not a valid one for this Teleport");
        this.destination = destination;
    }

    /**
     * Checks whether the given destination is a valid destination for this
     * Teleport. First the destination must not be null, secondly
     * the inventory of the destination must hold a teleport.
     *
     * The destination square may not hold the teleport itself as a teleport.
     *
     * @param 	destination
     * 			The destination to check
     * @return	True if and only if the destination is not this teleport.
     * 			And if the given destination is not null.
     */
    public boolean canHaveAsDestination(Square destination) {
        if(!isValidDestination(destination))
            return false;
        if(!destination.hasType(this))
            return false;

        Teleport destinationTeleport = (Teleport) destination.getType(this);

        return !destinationTeleport.equals(this);
    }

    /**
     * Checks whether the given teleport is a valid destination for all Teleports.
     *
     * @param 	destination
     * 			The destination to check
     * @return	True if and only if the destination is not null.
     */
    public static boolean isValidDestination(Square destination){
        return destination != null;
    }

    /**
     * Check whether this teleport can be used
     *
     * @return  True if and only if thos teleport's location
     *          is set and not obstructed.
     */
    public boolean canTeleport(){
        return getDestination() != null && !getDestination().isObstructed();
    }

        public boolean canAddTo(Square square){
            return (!square.hasItem(this) && !square.hasType(this));
        }


    @Override
    public String toString() {
        return super.toString() + "Teleport";
    }

    public static boolean isTeleport(Object o){
        return (o instanceof Teleport);
    }

    @Override
    public boolean isSameType(Item o){
        return (o instanceof Teleport);
    }

	@Override
	public void onMoveToEffect(Movable movable) {
		movable.acceptMoveToEffect(this);
		
	}

	@Override
	public void onMoveToEffect(Player player) {
		//FIXME: not sure if this sufficient		
		player.setPosition(getDestination());
		
	}

	@Override
	public void onMoveToEffect(IdentityDisc identityDisc) {
		//FIXME: not sure if this sufficient	
		identityDisc.setPosition(getDestination());
		
	}

	@Override
	public void onStandOnEffect(Movable movable) {
		movable.acceptStandOnEffect(this);
		
	}

	@Override
	public void onStandOnEffect(Player player) {
		// nothing to do here.
		
	}

	@Override
	public void onStandOnEffect(IdentityDisc identityDisc) {
		// nothing to do here;
		
	}
}
