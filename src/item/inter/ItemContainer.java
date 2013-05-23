package item.inter;

import item.Item;

import java.util.ArrayList;

public interface ItemContainer {

    /**
     * Adds the given item to the ItemContainer
     *
     * @param   item
     *          The item which will be added.
     */
    public void addItem(Item item);

    /**
     * Removes the given item from the Item
     * @param   item
     *          The item which will be removed.
     */
    public void removeItem(Item item);

    /**
     * Returns whether the given item is a member of the ItemContainer.
     *
     * @param   item
     *          The item which will be checked.
     * @return  True if the given item is an element of the ItemContainer.
     */
    public boolean hasItem(Item item);

    /**
     * Returns whether the container has an item of the same type as the given item.
     *
     * @param   item
     *          The item of which the type will be used.
     * @return
     */
    public boolean hasType(Item item);

    /**
     * Returns the first item of the same type as the given item in the container.
     *
     * @param   item
     *          The item of which the type will be used.
     */
    public ArrayList<Item> filterItemsByType(Item item);

    /**
     * Returns all the items in the container as a list.
     * @return
     */
    public ArrayList<Item> getAllItems();

}