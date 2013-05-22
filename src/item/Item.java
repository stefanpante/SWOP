package item;

import game.Player;
import effect.Effect;
import item.inter.ItemContainer;
import item.inter.Movable;
import square.Square;

/**
 * Parent class for all sorts of items.
 * 
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
 **/
public abstract class Item implements Effect {

    /**
     * The container of this item
     */
    private ItemContainer container;

    /**
     * Set the container of this item
     *
     * @param container
     */
    public void setContainer(ItemContainer container){
        if(!isValidContainer(container))
            throw new IllegalArgumentException("The given Item Container is not valid");
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
     * @return
     */
    protected ItemContainer getContainer(){
        return this.container;
    }

    public abstract boolean canAddTo(Square square);

    /**
     * Check whether this item can be added to the given player
     *
     * @param   player
     *          The player to be checked
     * @return  True if and only if the player has enough space
     */
    public boolean canAddTo(Player player){
        return player.getAllItems().size() < player.MAX_ITEMS;
    }

    /**
     * Destory this item
     */
    public void destory(){
        container.removeItem(this);
        this.setContainer(null);
    }

    /**
	 * Notifies the item that it has been used.
	 * No specific operation is needed at this level. Inheriting item may
	 * determine if it needs to handle internal operations if it is beeing used.
	 */
	public void notifyUse() {
		
	}
	
	/**
	 * Notifies the item that it has been picked up.
	 * No specific operation is needed at this level. Inheriting item may
	 * determine if it needs to handle internal operations if it is beeing picked up.
	 */
	public void notifyPickUp() {
		
	}
	
	/**
	 * Returns a string representation of this object.
	 */
	@Override
	public String toString() {
		return "Item";
	}
	
	public abstract boolean isSameType(Item item);

    @Override
    public void affect(Movable movable) {
        movable.acceptEffect(this);
    }

}
