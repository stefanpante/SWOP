package square;

import items.Inventory;
import items.Item;
import items.LightGrenade;
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
	private SquareInventory inventory;
	
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
	 * 
	 */
	private LightGrenade usedLightGrenade;

	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.usedItems = new ArrayList<Item>();
		this.inventory = new SquareInventory();
		this.neighbors = new HashMap<Direction, Square>();
	}
	
	/**
	 * Returns the neighbor in the given direction if there is one.
	 * 
	 * @param	direction
	 * @return	Returns the neighbor if there is one for the given direction.
	 * @throws	IllegalArgumentException
	 * 			Throws exception if there is no neighbor in the given direction.
	 */
	public Square getNeighbor(Direction direction) throws IllegalArgumentException {
		if(!hasNeighbor(direction))
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
	public void setNeighbor(Direction direction, Square square) throws IllegalArgumentException{
		if(canHaveAsNeighbor(direction, square))
			neighbors.put(direction,square);
		else
			throw new IllegalArgumentException();
		
		if(!square.hasNeighbor(direction.opposite()))
			square.setNeighbor(direction.opposite(), this);
	}

	/**
	 * Checks if there is a neighbor in the given direction.
	 * 
	 * @param 	direction the direction of the neighbour
	 * @return 	True if this square has a neighbor in the given direction
	 * 			otherwise False.
	 */
	public boolean hasNeighbor(Direction direction){
		return neighbors.containsKey(direction);
	}
	
	/**
	 * Checks if the given square is a neighbor in the given direction.
	 * 
	 * @param 	direction the direction in which the square should be a neighbor
	 * @param 	square the square that should be the neighbor in the given direction
	 * @return 	true if the square is the neighbor in the given direction
	 * 			otherwise false
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
	 * @param direction checks whether the square could be a neighbor in the given direction
	 * @param square  	the square to be checked
	 * @return			True if the square could be a neighbor in the given direction
	 */
	public boolean canHaveAsNeighbor(Direction direction, Square square) {
		if(square == null)
			return false;
		
		if(this.equals(square))
			return false;
		
		if(hasNeighbor(direction))
			return false;
		
		if(square.hasNeighbor(direction.opposite(), this))
			return true;
		else if(!square.hasNeighbor(direction.opposite()))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns a list of all the items that are placed on a square.
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
	 * Adds and uses the given item on this square.
	 * 
	 * @param 	lg
	 * 			The lightgrenade to be added or used
	 * @throws 	IllegalArgumentException
	 * 			If the given item cannot be used on this square
	 */
	public void addUsedItem(LightGrenade lg) throws IllegalArgumentException {
		addUsedItem((Item) lg);
		usedLightGrenade = lg;
	}
	
	/**
	 * Returns true if there is an active LightGrenade on this square.
	 * @return true if there is an active lightGrenade on this square.
	 */
	public boolean hasActiveLightGrenade(){
		if(usedLightGrenade == null)
			return false;
		return usedLightGrenade.isActive();
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
		String s = "Square [ ";
		for(Direction direction : Direction.values()){
			if(hasNeighbor(direction))
				s += direction + " ";
		}
		s += " ]";
		return s;
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
	 * @return A HashMap with neighbours with there direction as the key
	 * 			and the Square as value.
	 */
	private HashMap<Direction, Square> getNeighbors() {
		return new HashMap<Direction, Square>(this.neighbors);
	}
	
	/**
	 * Returns the neighbours of this square as a list.
	 * @return Returns the neighbours of this square as a list.
	 */
	public ArrayList<Square> getNeighborsAsList() {
		return new ArrayList<Square>(neighbors.values());
	}

	/**
	* Checks if the item can be used in the square.
	*
	* @param 	item
	* @return 	False If the item is already used once in the square.
	*/
	public boolean canBeUsedHere(Item item) {
		if(!usedItems.contains(item))
			return false;
		return true;
	}
	
	/**
	 * Checks whether from the current square it is possible to move to a
	 * neighboring square in the given direction. 
	 * 
	 * @param 	direction
	 * 			The direction of the neighbor that will be checked.
	 * @return	true if it is possible to move in the given direction
	 */
	public boolean canMoveTo(Direction direction){
		Square direcionSquare;
		try {
			direcionSquare = getNeighbor(direction);
		} catch (Exception e) {
			return false;
		}
		if(direcionSquare.isObstructed())
			return false;
		if(direction.isDiagonal()){
			Square s1 = null;
			Square s2 = null;
			ArrayList<Direction> dirs = direction.neighborDirections();
			try{
				s1 = getNeighbor(dirs.get(0)); 
				s2 = getNeighbor(dirs.get(1));
			} catch (Exception exp){
				//This should never happen.
				assert(false);
			}
			if(s1 != null && s2 != null){
				if(s1.isObstructed() && s2.isObstructed()){
					if(s1.getObstacle().equals(s2.getObstacle())){
						return false;
					}
				}
			}
		}
		return true;
	}
}
