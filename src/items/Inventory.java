package items;

import java.util.ArrayList;
import java.lang.*;

import be.kuleuven.cs.som.annotate.Raw;


/**
 * Inventory class is used to contain items 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 *
 */
public class Inventory {
	
	/**
	 * Static field which declares an empty inventory,
	 * added for performance, to prevent unnecessary instancing
	 */
	public static Inventory EMPTY = new Inventory(0);
	
	/**
	 * The size of the inventory, should not be smaller than zero
	 */
	private int size;
	
	/**
	 * A given size should imply the usage of an array,
	 * But we use an ArrayList for the convenience of the contains and add method
	 */
	private  ArrayList<Item> items;
	
	/**
	 * Creates a new instance of the Inventory class
	 * @param	size	the size of the new inventory
	 * @effect setSize(size)
	 */
	@Raw
	public Inventory(int size){
		this.setSize(size);	
	}
	
	/**
	 * Creates a new instance of the Inventory class
	 * @effect 	setSize(Integer.MAX_VALUE)
	 */	
	public Inventory(){
		this(Integer.MAX_VALUE);
	}
	
	/**
	 * Checks if the given size is valid for this inventory
	 * @param size size parameter
	 * @return 	true if the size is larger or equal to zero
	 * 			false otherwise
	 */
	public boolean isValidSize(int size){
		return size >= 0;
	}
	
	/**
	 * Sets the size of the inventory  and inits a new arraylist
	 * if a arraylist hasnt already been initialised
	 * 
	 * @param size	the size of this inventory
	 * @throws IllegalArgumentException if the given size is not valid
	 */
	public void setSize(int size) throws IllegalArgumentException{
		if(!isValidSize(size)) throw new IllegalArgumentException("The given size is not valid!");
		else{
			this.size = size;
			if(this.items == null){
				this.items = new ArrayList<Item>();
			}
			
		}
	}
	
	/**
	 * checks if the given item is in the inventory
	 * @param item the item to be checked
	 * @return true if the item is contained in this inventory,
	 * 				otherwise false
	 */
	public boolean hasItem(Item item){
		return items.contains(item);
	}
	
	/**
	 * Adds an item to the inventory if the size limit allows it
	 * @param item	the item to be added to the inventory
	 * @throws IllegalStateException
	 * 			thrown when adding the item would exceed the size
	 * 			of the inventory
	 */
	public void addItem(Item item) throws IllegalStateException{
		if(items.size() + 1 > size){
			throw new IllegalStateException("The inventory is full, cannot add another item");
		}
		else items.add(item);
	}
	
	/**
	 * Gets an item based on its index and removes it from the inventory
	 * @param index the index of the item to return 
	 * @return the item of which the index is given
	 * @throws IndexOutOfBoundsException
	 * 		   thrown if the given index is not valid for this inventory
	 */
	public Item getItem(int index) throws IndexOutOfBoundsException{
		if(index >= items.size()) throw new IndexOutOfBoundsException();
		Item item = items.get(index);
		items.remove(index);
		
		return item;
		
	}
	
	/**
	 * Removes a given item from the inventory
	 * @param item the item to be removed
	 * @throws IllegalStateException
	 * 		   thrown when the item cannot be removed, because it is not inside the inventory
	 */
	public void removeItem(Item item) throws IllegalStateException{
		if(!this.hasItem(item)) 
			throw new IllegalStateException("Item cannot be removed, because it is not in this inventory");
		else items.remove(item);
	}
	
	/**
	 * Returns a string representation of this Inventory object,
	 * can be used so that a player can chose the item he wants
	 */
	@Override
	public String toString(){
		String description = "";
		if(size == 0) return "This inventory is empty";
		else{
			int i = 0;
			while(i < items.size());
			// the system.getProperty is used to get a system independent newline,
			// is different on windows vs Unix systems.
			description +=  i + ". " + items.get(i).toString() + System.getProperty("line.seperator");
			i++;
		}
		
		return description;
		
		
	}

}
