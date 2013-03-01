package square;

import items.Inventory;
import items.Item;
import items.SquareInventory;

import java.util.ArrayList;
import java.util.HashMap;

import square.obstacles.Obstacle;

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
		this.neighbors = new HashMap<Direction, Square>();
	}
	
	/**
	 * Returns the neighbor in the given direction if there is one.
	 * 
	 * @param	direction
	 * @returns	Returns the neighbor if there is one for the given direction.
	 * @throws	IllegalArgumentException
	 * 			Throws exception if there is no neighbor in the given direction.
	 */
	public Square getNeighor(Direction direction) throws IllegalArgumentException {
		if(!hasNeigbor(direction))
			throw new IllegalArgumentException();
		
		return neighbors.get(direction);
	}
	
	/**
	 * The neighbor must be valid.
	 * 
	 * @pre		The square must be a valid neighbor in the given direction.	
	 * 			isValidNeighbor(direction, square)
	 * 
	 * @post	If the square is valid, the square is set as neighbor in the given direction.
	 * 
	 * @post	If the square is valid and does not have the current square as a neighbor, it is also set
	 * 			in the given square in the opposing direction.
	 * 
	 * @throws	IllegalArgumentException
	 * 			If the given square  is not a valid one according to the direction an exception is thrown.
	 * 
	 * @param direction
	 * @param square
	 */
	public void setNeigbor(Direction direction, Square square) throws IllegalArgumentException{
		if(canHaveAsNeighbor(direction, square))
			neighbors.put(direction,square);
		else
			throw new IllegalArgumentException();
		
		if(!square.hasNeigbor(direction.opposite()))
			square.setNeigbor(direction.opposite(), this);
	}

	/**
	 * Checks if there is a neighbor in the given direction.
	 * 
	 * @param direction
	 * @return
	 */
	public boolean hasNeigbor(Direction direction){
		return neighbors.containsKey(direction);
	}
	
	/**
	 * Checks if the given square is a neighbor in the given direction.
	 * 
	 * @param direction
	 * @param square
	 * @return
	 */
	public boolean hasNeighbor(Direction direction, Square square) {
		Square neighbor = neighbors.get(direction);
		
		if(neighbor == null)
			return false;
		
		if(neighbor.equals(square))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns whether the given square in the given direction is a valid 
	 * neighbor for this square. The square may not be the square itself.
	 * The given square may also not be null.
	 * 
	 * 
	 * The square that is being set as a neighbor must apply to the 
	 * following conditions:
	 * 	- The square has the current square as a neighbor in the opposing direction.
	 * 	- The square has no square as a neighbor in the opposing direction.
	 * 
	 * @param direction
	 * @param square
	 * @return
	 */
	public boolean canHaveAsNeighbor(Direction direction, Square square) {
		if(square == null)
			return false;
		
		if(this.equals(square))
			return false;
		
		if(hasNeigbor(direction))
			return false;
		
		if(square.hasNeighbor(direction.opposite(), this))
			return true;
		else if(!square.hasNeigbor(direction.opposite()))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns a list of all the items that are placed on a square 
	 */
	public ArrayList<Item> getUsedItems() {
		return usedItems;
	}
	
	/**
	 * Adds and uses the given item on this square.
	 * 
	 * @param 	item
	 * 			The item to be added or used
	 * @throws 	IllegalArgumentException
	 * 			If the given item cannot be used on this square
	 */
	public void addUsedItem(Item item) throws IllegalArgumentException {
		if(!canBeUsedHere(item))
			throw new IllegalArgumentException();
		usedItems.add(item);
	}
	
	/**
	 * Returns the inventory of this square.
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * This method is used to activate the usedItems on the square.
	 */
	public void activateUsedItems(){
		for(Item i: usedItems)
			i.activate();
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
	public void setObstacle(Obstacle obstacle) throws IllegalArgumentException {
		if (getObstacle() != null)
			throw new IllegalArgumentException("Obstacle already exists on this square.");
		
		this.obstacle = obstacle;
	};
	
	@Override
	public String toString() {
		return "Square [ " + this.getInventory() +" ]";
	}
	
	/**
	 * Returns whether this square is obstructed by an obstacle or not.
	 * 
	 * @return	True	if there is an obstacle which is not null.
	 * 			False	If there is no obstacle.
	 */
	public boolean isObstructed(){
			return obstacle != null;
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
	public boolean isConnectedTo(Square square) {
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
