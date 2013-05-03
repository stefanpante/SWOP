package item.inventory;

import move.Movable;
import move.MovableEffect;
import game.Player;
import item.IdentityDisc;
import item.Item;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * Inventory class is used to contain items 
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
public abstract class Inventory implements MovableEffect {

	/**
	 * The size of the inventory, should not be smaller than zero
	 */
	private final double maxSize;
	
	/**
	 * A given size should imply the usage of an array,
	 * But we use an ArrayList for the convenience of the contains and add method
	 */
	private ArrayList<Item> items;



    /**
	 * Creates a new instance of the Inventory class with given size.
	 * 
	 * @param	size	
	 * 			The size of the new inventory.
	 * @effect	setSize(size)
	 * @throws	IllegalArgumentException
	 * 			When the given size is invalid.
	 */
	@Raw
	public Inventory(double size) throws IllegalArgumentException {
		if(!isValidMaximumSize(size)) 
			throw new IllegalArgumentException("The given size is not valid!");
		
		this.maxSize = size;
		this.items = new ArrayList<Item>();
	}

	/**
	 * Creates a new instance of the Inventory class with unlimited size.
	 * 
	 * @effect 	Inventory(Double.POSITIVE_INFINITY)
	 */	
	public Inventory(){
		this(Double.POSITIVE_INFINITY);
	}

	/**
	 * Checks if the given size is valid for this inventory.
	 * 
	 * @param 	size 
	 * 			The size to check.
	 * @return 	True 	if the size is larger or equal to zero.
	 * 			False 	otherwise.
	 */
	public static boolean isValidMaximumSize(double size){
		return size >= 0;
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
	public double getMaximumSize(){
		return this.maxSize;
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
	 * 			of the inventory or the item is already in the
	 * 			inventory
	 */
	public void addItem(Item item) throws IllegalStateException{
		if(!canHaveAsItem(item))
			throw new IllegalStateException("The inventory is full or already contains the item.");
		else
			item.setInventory(this);
			items.add(item);
			
	}

	/**
	 * Returns whether the given item is a valid item for all Inventory objects.
	 * 
	 * @param	item
	 * 			The item to check.
	 * @return	Returns true if and only if the item is not null.
	 */
	public static boolean isValidItem(Item item){
        return item != null;
    }

	/**
	 * Checks if a certain item can be added.
	 * 
	 * @param item      the item to be checked.
	 * @return	True	If the item is not already contained and inventory is not full.
	 * 			False	If the item is already contained or the inventory is full.
	 */
	public boolean canHaveAsItem(Item item) {
        return !isFull() && !hasItem(item);
    }

	/**
	 * Removes a given item from the inventory
	 * 
	 * @param 	item the item to be removed
	 * @throws 	IllegalStateException
	 * 		  	Thrown when the item cannot be removed, because it is not inside the inventory
	 */
	public void removeItem(Item item) throws IllegalStateException{
		if(!this.hasItem(item)) 
			throw new IllegalStateException("Item cannot be removed, because it is not in this inventory");
		else 
			items.remove(item);
	}

	/**
	 * Returns a string representation of this Inventory object,
	 * can be used so that a player can chose the item he wants
	 */
	@Override
	public String toString(){
		String description = "";
		if(maxSize == 0){
			return "Inventory (empty)";
		} else {
			description += "Inventory ("+ getSize() +") containing: "; 
			int i = 0;
			ArrayList<Item> list= new ArrayList<Item>(items);
			while(i < list.size()){
				// the system.getProperty is used to get a system independent newline,
				// is different on windows vs Unix systems.
				description +=  i + ". " + list.get(i).toString() + System.getProperty("line.separator");
				i++;
			}
		}
		return description;
	}
	
	public ArrayList<IdentityDisc> getIdentityDiscs(){
		ArrayList<IdentityDisc> discs = new ArrayList<IdentityDisc>();
		for(Item item: getAllItems()){
			if(IdentityDisc.isIdentityDisc(item)){
				discs.add((IdentityDisc) item);
			}
		}
		
		return discs;
	}


    @Override
    public void affect(Movable movable) {
        for(Item item : getAllItems()){
            item.affect(movable);
        }
    }

    @Override
    public void affect(Player player) {
        for(Item item : getAllItems()){
            item.affect(player);
        }    }

    @Override
    public void affect(IdentityDisc identityDisc) {
    	for(Item item : getAllItems()){
            item.affect(identityDisc);
        }
    }

}
