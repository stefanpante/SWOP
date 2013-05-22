package item;

import effect.event.DropFlagEvent;
import item.inter.Movable;
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
	public void onMoveToEffect(Movable movable) {
		movable.acceptStandOnEffect(this);
		
	}

	@Override
	public void onMoveToEffect(Player player) {
        DropFlagEvent dropFlag = new DropFlagEvent();
        try {
            dropFlag.execute();
        } catch (Exception e) {
               //  Nothing to do
        }
        if(this.isActive()){
			player.loseActions(3);
		}
		
		this.destroy();
		
	}

	@Override
	public void onMoveToEffect(IdentityDisc identityDisc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(Movable movable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(IdentityDisc identityDisc) {
		// TODO Auto-generated method stub
		
	}


}