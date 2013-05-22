package item;

import item.inter.ItemContainer;
import item.inter.Movable;
import game.Player;
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


    public Flag() {
		// TODO Auto-generated constructor stub
	}


	@Override
    public boolean canAddTo(Square square) {
        return true;
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
	
	/**
	 * Sets the container of the this flag. If the container is equal to the flags'
	 * owner the item his container becomes the startSquare of the player and is added
	 * to the container.
	 */
	@Override
	public void setContainer(ItemContainer itemContainer){
		if(itemContainer == this.player){
			itemContainer.removeItem(this);
			player.getStartPosition().addItem(this);
		}
		else{
			getContainer().removeItem(this);
			setContainer(itemContainer);
		}
	}
}
