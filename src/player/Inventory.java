package player;

import java.util.ArrayList;

import items.Item;

/**
 * Inventory class
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
public class Inventory {
	
	public static Inventory EMPTY = new Inventory(0);
	/**
	 * The size of the inventory;
	 */
	private int size;
	/**
	 * A given size should imply the usage of an array,
	 * But we use an ArrayList for the convenience of the contains and add method
	 */
	private  ArrayList<Item> items;
	
	/**
	 * 
	 */
	public Inventory(int size){
		this.size = size;
		this.items = new ArrayList<Item>(size);
		
	}
	
	public boolean hasItem(Item item){
		return items.contains(item);
	}
	
	public void addItem(Item item) throws IllegalStateException{
		if(items.size() + 1 > size){
			throw new IllegalStateException("The inventory is full, cannot add another item");
		}
		else items.add(item);
	}

}
