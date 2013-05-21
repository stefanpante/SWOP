package item.inter;

import item.Item;

import java.util.ArrayList;

public interface ItemContainer {

    public void addItem(Item item);

    public void removeItem(Item item);

    public boolean hasItem(Item item);

    public boolean hasType(Item item);

    public Item getType(Item item);

    public ArrayList<Item> getAllItems();

}