package square;

import items.Inventory;

import items.Item;
import items.LightGrenade;
import items.SquareInventory;

import java.util.ArrayList;
import java.util.HashMap;

import square.obstacles.Obstacle;
import square.state.RegularState;
import square.state.State;

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
	
	/**
	 * Inventory containing all items on the square, not the ones that are used.
	 */
	private SquareInventory pickUpInventory;
	
	/**
	 *  Contains all items which were used on this square.
	 */
	private SquareInventory usedInventory;
	
	/**
	 * State of the current square. May be a power failure.
	 */
	private State state;
	
	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private Obstacle obstacle;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.pickUpInventory = new SquareInventory(false);
		this.usedInventory = new SquareInventory(true);
		this.state = new RegularState();
	}
	
	/**
	 * Returns the state of the square.
	 */
	public State getState() {
		return this.state;
	}
	
	/**
	 * State state
	 * 
	 * @param regularState
	 */
	public void setState(State state) {
		this.state = state;
	}

	
	/**
	 * Return the inventory of used items on this Square
	 * @return	the inventory of used items on this Square
	 */
	public SquareInventory getUsedInventory() {
		return usedInventory;
	}
	
	/**
	 * Returns the inventory of which items can be picked up
	 * @return the inventory of  items which can be picked up on this Square
	 */
	public SquareInventory getPickUpInventory(){
		return pickUpInventory;
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
		s += "Obstacle: " + obstacle;
		s += "Inv: " + this.getPickUpInventory();
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
}
