package item;

import effect.Effect;
import game.Player;
import item.inventory.PlayerInventory;
import square.Square;

/**
 * Represents a flag item. Belongs to a certain player.
 * @author Stefan
 *
 */
public class Flag extends Item {

	/**
	 * The owner of the flag
	 */
	private Player player;
	
	/**
	 * Constructs a new flag.
	 * @param player	the player to which the flag belongs.
	 */
	public Flag(Player player){
		this.player = player;
	}


    @Override
    public boolean canAddTo(Square square) {
        return false;
    }

    @Override
	public boolean isSameType(Item item) {
		return (item instanceof Flag);
	}
	
    /**
     * Returns the player to which this flags belongs.
     */
	public Player getPlayer(){
		return this.player;
	}


	@Override
	public void affect(Player player) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void affect(IdentityDisc identityDisc) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}
}
