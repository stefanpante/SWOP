package square;

import grid.obstacles.Obstacle;
import items.Inventory;
import items.Item;

import java.util.ArrayList;
import java.util.HashMap;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
/*TODO: Discuss: one inventory for items that can be picked up.
 * Another inventory for "used" items that are placed there.
 */
@NotNull
public class Square {
	
	/*
	 * Neighbors of this square
	 */
	private HashMap<Direction, Square> neighbors;
	
	/**
	 * Inventory containing all items on the square
	 */
	private Inventory inventory;
	
	/**
	 * All the items used on this square
	 */
	private ArrayList<Item> usedItems;
	
	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private Obstacle obstacle;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.usedItems = new ArrayList<Item>();
		this.inventory = new Inventory();
	}
	
	// TODO: Own exception?
	public Square getNeighor(Direction direction) throws IllegalArgumentException {
		if(!hasNeigbor(direction))
			throw new IllegalArgumentException();
		return neighbors.get(direction);
	}
	
	public void setNeigbor(Direction direction, Square square){
		neighbors.put(direction,square);
	}

	public boolean hasNeigbor(Direction direction){
		return neighbors.containsKey(direction);
	}
	
	/**
	 * Returns a list of all the items that are placed on a square 
	 */
	public ArrayList<Item> getUsedItems() {
		return usedItems;
	}
	
	public void addUsedItem(Item item){
		usedItems.add(item);
	}
	
	/**
	 * Adds the given item to the inventory of this square.
	 * 
	 * @param 	item
	 * 			The item that will be added to the inventory.
	 */
	public void addItemToInventory(Item item){
		if(!isValidInventoryItem(item) || !canHaveAsInventoryItem(item)){
			throw new IllegalArgumentException("The item"
												+item
												+"can not be added to the inventory of this "
												+this);
		}
		inventory.addItem(item);
	}
	
	
	
	/**
	 * Returns the inventory of this square.
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	
	
	/**
	 * used to activate the usedItems on the square
	 */
	public void activateUsedItems(){
		for(Item i: usedItems)
			i.activate();
	}
	
	/**
	 * Returns whether the given item is a valid item for all squares.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return	True if and only if the item is not an active item.
	 * 			| !item.isActive()
	 */
	//TODO: I think it is safe to assume that we never want an active item to be placed in the inventory.
	public static boolean isValidInventoryItem(Item item) {
		return !item.isActive();
	}

	/**
	 * Returns whether the given can be added to the current square.
	 * 
	 * @param 	item
	 * 			The item to check.
	 * @return  True if there are 
	 */
	public boolean canHaveAsInventoryItem(Item item) {
		//TODO: add specific implementation
		return true;
	}

	


	/**
	 * Returns the value of the obstacle of this Square as an Obstacle.
	 *
	 * @return 	An object of the Obstacle class.
	 * 			| Obstacle
	 */
	public Obstacle getObstacle() {
		return obstacle;
	};

	/**
	 * Sets the value of the obstacle of Square if the given value is valid. 
	 * 
	 * @param 	obstacle
	 *			The obstacle to set.
	 * @post 	The given value is the current value of the obstacle of this Square.
	 * @throws 	IllegalArgumentException
	 *			If the given argument is not a valid obstacle.
	 *			| !isValidObstacle(obstacle)
	 */
	@Nullable
	public void setObstacle(Obstacle obstacle)
			throws IllegalArgumentException {
		if (!isValidObstacle(obstacle)) {
			throw new IllegalArgumentException(
					"The argument ("
							+ obstacle
							+ ") is not a valid agrument of the field obstacle from the class Square");
		}
		this.obstacle = obstacle;
	};

	/**
	 * Check whether the given obstacle is a valid obstacle for all the objects of Square.
	 * @param 	obstacle
	 *			The obstacle to check.
	 * @return	True if and only if the given value is not null, has the correct type, ...
	 */
	public static boolean isValidObstacle(Obstacle obstacle) {
		if (!(obstacle instanceof Obstacle)) {
			return false;
		}
		//TODO: specific constraints for this field.
		return true;
	}
	@Override
	public String toString() {
		return "Square [ " + this.getInventory() +" ]";
	}
	
	/**
	 * Returns whether this square is obstructed by an obstacle or not.
	 * 
	 * @return
	 */
	public boolean isObstructed(){
			return obstacle == null;
	}

	/**
	 * Check if the current square is connected to the given square
	 * 
	 * @param 	square
	 * 			The square to check wether it's connected to this
	 * 			square.
	 * @return	True if and only if the given square is connected to
	 * 			this square.
	 */
	public boolean connectedTo(Square square) {
		for(Square neighborSquare : getNeighbors().values())
			if(square == neighborSquare)
				return true;
		return false;
	}

	/**
	 * @return
	 */
	private HashMap<Direction, Square> getNeighbors() {
		return new HashMap<Direction, Square>(this.neighbors);
	}

	/**
	 * @param item
	 * @return
	 */
	public boolean canBeUsedHere(Item item) {
		return true;
	}
}
