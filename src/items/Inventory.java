package items;

import java.util.ArrayList;
import java.lang.*;
import java.nio.channels.IllegalSelectorException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * Inventory class is used to contain items 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
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
	private int maximumSize;
	
	/**
	 * A given size should imply the usage of an array,
	 * But we use an ArrayList for the convenience of the contains and add method
	 */
	private  ArrayList<Item> items;
	
	/**
	 * Creates a new instance of the Inventory class
	 * 
	 * @param	size	the size of the new inventory
	 * @effect setSize(size)
	 */
	@Raw
	public Inventory(int size) throws IllegalArgumentException {
		this.setMaximumSize(size);
		
		this.items = new ArrayList<Item>();
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
	 * @param maximumSize size parameter
	 * @return 	true if the size is larger or equal to zero
	 * 			false otherwise
	 */
	public boolean isValidMaximumSize(int maximumSize){
		return maximumSize >= 0;
	}
	
	/**
	 * Checks if the inventory is full.
	 * 
	 * @return	False	If the inventory is not full after adding the item.
	 * 			True	If the inventory is already at its maximum capacity.
	 */
	public boolean isFull() {
		return this.getSize() >= this.getMaximumSize();
	}
	
	/**
	 * Checks if the inventory is empty.
	 * 
	 * @return
	 */
	@Basic
	public boolean isEmpty(){
		return items.isEmpty();
	}
	
	/**
	 * The maximum size of this inventory of items.
	 * 
	 * @return	An integer representing the maximum size of this inventory.
	 */
	public int getMaximumSize(){
		return this.maximumSize;
	}
	
	/**
	 * Sets the size of the inventory and inits a new arraylist
	 * if a arraylist hasn't already been initialised.
	 * 
	 * @param 	maximumSize	
	 * 			the size of this inventory
	 * @throws 	IllegalArgumentException 
	 * 			if the given size is not valid
	 */
	public void setMaximumSize(int maximumSize) throws IllegalArgumentException{
		if(!isValidMaximumSize(maximumSize)) 
			throw new IllegalArgumentException("The given size is not valid!");
		else{
			this.maximumSize = maximumSize;
			if(this.items == null){
				
			}
			
		}
	}
	
	/**
	 * Returns the size of already occupied spaces in the inventory.
	 * 
	 * @return	An integer representing the spaces already occupied in this inventory.
	 */
	public int getSize(){
		return items.size();
	}
	
	/**
	 * Returns all the items.
	 */
	public ArrayList<Item> getAllItems(){
		return new ArrayList<Item>(items);
	}
	
	/**
	 * Inspector that returns the item at the given index.
	 * 
	 * @param 	index 
	 * 			The index of the item to return.
	 * @return	The item of which the index is given.
	 * @throws 	IllegalStateException
	 * 		   	Thrown if the given index is not valid for this inventory.
	 * 			| !canHaveAsItemIndex(index)
	 */
	public Item getItem(int index) throws IndexOutOfBoundsException{
		if(!canHaveAsItemIndex(index)){
			throw new IllegalSelectorException();
		}
		return items.get(index);
	}
	
	/**
	 * checks if the given item is in the inventory
	 * @param 	item 
	 * 			the item to be checked
	 * @return 	true if the item is contained in this inventory,
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
		if(!canHaveAsItem(item))
			throw new IllegalStateException("The inventory is full, cannot add another item");
		else
			items.add(item);
	}
		
	/**
	 * Returns whether the given index is a possible index for this inventory.
	 * 
	 * @param 	index
	 * 			The index to be checked.
	 * @return	True if and only if the given index is larger than zero,
	 * 			smaller than the maximum possible size and the amount of spaces used.
	 * 			| index > 0 && 
	 * 			| index <= this.getMaximumSize() && 
	 * 			| index <= this.getAmountOfUsedSpaces()
	 */
	public boolean canHaveAsItemIndex(int index){
		return index > 0 && index <= this.getMaximumSize() && index <= this.getSize();
	}
	
	/**
	 * Checks if a certain item can be added.
	 * 
	 * @param item
	 * @return	True	If the item is not already contained and inventory is not full.
	 * 			False	If the item is already contained or the inventory is full.
	 */
	public boolean canHaveAsItem(Item item) {
		if(isFull())
			return false;
		
		if(items.contains(item))
			return false;
		
		return true;
	}
	
	/**
	 * Removes a given item from the inventory
	 * 
	 * @param 	item the item to be removed
	 * @throws 	IllegalStateException
	 * 		  	Thrown when the item cannot be removed, because it is not inside the inventory
	 */
	public Item take(Item item) throws IllegalStateException{
		if(!this.hasItem(item)) 
			throw new IllegalStateException("Item cannot be removed, because it is not in this inventory");
		else 
			items.remove(item);
		
		return item;
	}
	
	/**
	 * Returns a string representation of this Inventory object,
	 * can be used so that a player can chose the item he wants
	 */
	@Override
	public String toString(){
		String description = "";
		if(maximumSize == 0) return "This inventory is empty";
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
