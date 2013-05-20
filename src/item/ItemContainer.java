package item;

/**
 * User: jonas
 * Date: 20/05/13
 * Time: 13:53
 */
public interface ItemContainer {

    public void addItem(Item item);

    public void removeItem(Item item);

    public boolean hasItem(Item item);

    public boolean hasType(Item item);

    public Item getType(Item item);


}
