package item;

/**
 * User: jonas
 * Date: 03/05/13
 * Time: 13:40
 */
public interface Activatable {


    /**
     * Returns whether the item was dropped on the square by a player
     *
     * @return	true if and only if the item is dropped
     *
     */
    public boolean isDropped();

    /**
     * Returns whether the item is active or inactive.
     *
     * @return	True if and only if when the item is active
     */
    public boolean isActive();

    /**
     * Activates the item.
     *
     * @throws 	IllegalStateException
     * 			thrown if the current state isn't inactive. An item can only go to
     * 			an active state from an inactive one.
     */
    public void drop() throws IllegalStateException;

    /**
     * Drops the item.
     *
     * @throws	IllegalStateException
     * 			If the item is active or worn out it cannot be dropped.
     */
    public void activate() throws IllegalStateException;
}
