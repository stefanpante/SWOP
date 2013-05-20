package item;

import effect.Effect;
import game.Player;
import item.inventory.PlayerInventory;
import move.Movable;
import square.Square;

/**
 * Represents a flag item. Belongs to a certain player.
 * @author Stefan
 *
 */
public class Flag extends Item {

	private Player player;
	
	
	public Flag(){
		
	}
	public Flag(Player player){
		this.player = player;
	}


    @Override
    public boolean canAddTo(Square square) {
        return false;
    }

    @Override
    public boolean canAddTo(PlayerInventory playerInventory) {
        return false;
    }

    @Override
	public boolean isSameType(Item item) {
		return (item instanceof Flag);
	}
	
	public Player getPlayer(){
		return this.player;
	}
	@Override
	public Effect getEffect() {
		// TODO Auto-generated method stub
		return null;
	}

}
