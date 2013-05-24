package item;

import effect.Effect;
import game.Player;
import item.inter.ItemContainer;
import square.Square;

import java.util.ArrayList;

/**
 * Parent class for all sorts of items.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 **/
public abstract class Item {

    /**
     * The container of this item
     */
    private ItemContainer container;

    /**
     * Set the container of this item
     *
     * @param   container
     *          The container to be set.
     */
    public void setContainer(ItemContainer container){
        if(!isValidContainer(container))
            throw new IllegalArgumentException("The given Square Container is not valid");
        if(this.container != null){
        	this.container.removeItem(this);
        }
        this.container = container;
    }

    private boolean isValidContainer(ItemContainer container) {
        return container != null;
    }

    /**
     * Return the container of this item
     * @return  The Item Container
     */
    protected ItemContainer getContainer(){
        return this.container;
    }

    /**
     * Returns wether this item can be added to the Square.
     *
     * @param   square
     *          The square to be checked
     * @return  True if and only if this item can be added to the square.
     */
    public abstract boolean canAddTo(Square square);

    /**
     * Check whether this item can be added to the given player
     *
     * @param   player
     *          The player to be checked
     * @return  True if and only if the player has enough space
     */
    public boolean canAddTo(Player player){
        return player.getAllItems().size() < Player.MAX_ITEMS;
    }

    /**
     * Destory this item
     */
    public void destroy(){
        container.removeItem(this);
        this.setContainer(null);
    }

	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item:";
	}

	public abstract boolean isSameType(Item item);

    public abstract ArrayList<Effect> getEffects();

}
