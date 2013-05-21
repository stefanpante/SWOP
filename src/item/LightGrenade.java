package item;

import command.effect.DropFlagCommand;
import game.Player;
import square.Square;

/**
 * This class extends Item and represents a LightGrenade object.
 *  
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 *
 */
public class LightGrenade extends Item{

    public static final int LOST_ACTIONS = 3;
    private boolean activated = false;

    /**
     * Returns whether the grenade has been activated.
     */
	public boolean isActive() {
        return activated;
	}

	public void activate() throws IllegalStateException {
        activated = true;
	}

    public boolean canAddTo(Square square){
        return (!square.hasItem(this) && !square.hasType(this));
    }

    @Override
    public void notifyUse(){
        activate();
    }

	/**
	 * Returns the string representation of this LightGrenade
	 */
	@Override
	public String toString() {
		return super.toString() + " LightGrenade";
	}

	/*
	 * Returns whether the given object is a lightgrenade
	 */
	public static boolean isLightGrenade(Object o){
		return (o instanceof LightGrenade);
	}
	
	/**
	 * returns whether the given object is of the same type.
	 * @param o the item to be checked
	 */
	@Override
	public boolean isSameType(Item o){
		return (o instanceof LightGrenade);
	}


    @Override
    public void affect(Player player) {
        if(isActive()){
            // Decrement actions
            player.loseActions(LOST_ACTIONS);
            // Handle the flag
            DropFlagCommand dropFlagCommand = new DropFlagCommand(player);
            try {
                dropFlagCommand.execute();
            } catch (Exception ignored){
                //If there is no flag to drop nothing special to do.
            }
            this.destory();
        }
    }

    @Override
    public void affect(IdentityDisc identityDisc){
        if(isActive()){
            identityDisc.decreaseRange();
            this.destory();
        }
    }
}