package item.inventory;

import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.launchable.ChargedDisc;
import item.launchable.IdentityDisc;
import item.visitor.AddRemoveItemVisitor;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * Inventory class is used to contain items 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
public abstract class Inventory implements AddRemoveItemVisitor{

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
	 * Creates a new instance of the Inventory class with given size.
	 * 
	 * @param	size	
	 * 			The size of the new inventory.
	 * @effect	setSize(size)
	 */
	@Raw
	public Inventory(int size) throws IllegalArgumentException {
		this.setMaximumSize(size);
		this.items = new ArrayList<Item>();
	}

	/**
	 * Creates a new instance of the Inventory class with unlimited size.
	 * 
	 * @effect 	Inventory(Integer.MAX_VALUE)
	 */	
	public Inventory(){
		this(Integer.MAX_VALUE);
	}

	/**
	 * Checks if the given size is valid for this inventory.
	 * 
	 * @param 	maximumSize 
	 * 			The size to check.
	 * @return 	True 	if the size is larger or equal to zero.
	 * 			False 	otherwise.
	 */
	public static boolean isValidMaximumSize(int maximumSize){
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
	 * @return 	True 	if the inventory is empty,
	 * 			False 	otherwise.
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
	@Basic
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
		if(!isValidMaximumSize(maximumSize) || maximumSize < 0) 
			throw new IllegalArgumentException("The given size is not valid!");
		else
			this.maximumSize = maximumSize; 
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
	 * @throws 	IndexOutOfBoundsException
	 * 		   	Thrown if the given index is not valid for this inventory.
	 * 			| !canHaveAsItemIndex(index)
	 */
	public Item getItem(int index) throws IndexOutOfBoundsException{
		if(!canHaveAsItemIndex(index)){
			throw new IndexOutOfBoundsException();
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
	 * @param 	item	
	 * 			the item to be added to the inventory
	 * @throws 	IllegalStateException
	 * 			thrown when adding the item would exceed the size
	 * 			of the inventory
	 */
	public void addItem(Item item) throws IllegalStateException{
		if(!canHaveAsItem(item))
			throw new IllegalStateException("The inventory is full or already contains the item.");
		else
			items.add(item);
	}

	/**
	 * Returns whether the given index is a possible index for this inventory.
	 * 
	 * @param 	index
	 * 			The index to be checked.
	 * @return	True if and only if the given index is larger than or equal to zero,
	 * 			smaller than the maximum possible size and the amount of spaces used.
	 * 			| index > 0 && 
	 * 			| index <= this.getMaximumSize() && 
	 * 			| index <= this.getsize() - 1
	 */
	public boolean canHaveAsItemIndex(int index){
		return index >= 0  && index <= this.getSize() - 1;
	}


	/**
	 * Returns whether the given item is a valid item for all Inventory objects.
	 * 
	 * @param	item
	 * 			The item to check.
	 * @return	Returns true if and only if the item is not null.
	 */
	public static boolean isValidItem(Item item){
		if(item == null)
			return false;
		return true;
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
		if(hasItem(item))
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
	public void take(Item item) throws IllegalStateException{
		if(!this.hasItem(item)) 
			throw new IllegalStateException("Item cannot be removed, because it is not in this inventory");
		else 
			items.remove(item);
	}
	
	@Override
	public void addItem(ChargedDisc chargedDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeItem(ChargedDisc chargedDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void addItem(IdentityDisc identityDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeItem(IdentityDisc identityDisc)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void addItem(LightGrenade lightGrenade)
			throws IllegalStateException {
		//No specific operation needed yet.
	}

	@Override
	public void removeItem(LightGrenade lightGrenade)
			throws IllegalStateException {
		//No specific operation needed yet.
	}
	
	@Override
	public void addItem(Teleport teleport) throws IllegalStateException {
		// No specific operation needed yet.
	}

	@Override
	public void removeItem(Teleport teleport) throws IllegalStateException {
		//No specific operation needed yet.
	}

	/**
	 * Returns a string representation of this Inventory object,
	 * can be used so that a player can chose the item he wants
	 */
	@Override
	public String toString(){
		String description = "";
		if(maximumSize == 0){
			return "Inventory (empty)";
		} else {
			description += "Inventory ("+ getSize() +") containing: "; 
			int i = 0;
			while(i < items.size()){
				// the system.getProperty is used to get a system independent newline,
				// is different on windows vs Unix systems.
				description +=  i + ". " + items.get(i).toString() + System.getProperty("line.separator");
				i++;
			}
		}
		return description;
	}
	
}
