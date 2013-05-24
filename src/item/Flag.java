package item;

import effect.Effect;
import effect.imp.EmptyEffect;
import game.Player;
import item.inter.ItemContainer;
import square.Square;

import java.util.ArrayList;

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
	public boolean canAddTo(Player player){
		return  (!player.hasType(new Flag())) && player.getAllItems().size() < Player.MAX_ITEMS;
		
	}

    @Override
	public boolean isSameType(Item item) {
		return (item instanceof Flag);
	}

    @Override
    public ArrayList<Effect> getEffects() {
        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(new EmptyEffect());
        return effects;
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
			returnToStartPosition();
		}
		else{
            if(getContainer() != null)
			    getContainer().removeItem(this);
			super.setContainer(itemContainer);
		}
	}
	
	
	public void returnToStartPosition(){
		getContainer().removeItem(this);
		player.getStartPosition().addItem(this);
	}

    @Override
    public String toString() {
        return super.toString() + "Flag";
    }
}
