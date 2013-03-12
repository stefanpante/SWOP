package square;

import items.SquareInventory;

import square.obstacles.IObstacle;
import square.state.RegularState;
import square.state.State;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
@NotNull
public class Square {
		
	/**
	 *  Contains all items which were used on this square.
	 */
	private SquareInventory inventory;
	
	/**
	 * State of the current square. May be a power failure.
	 */
	private State state;
	
	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private IObstacle obstacle;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.inventory = new SquareInventory();
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
	public SquareInventory getInventory() {
		return inventory;
	}
	
	/**
	 * Returns the value of the obstacle of this Square as an Obstacle.
	 *
	 * @return 	An object of the Obstacle class.
	 * 			| Obstacle
	 */
	public IObstacle getObstacle() {
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
	public void setObstacle(IObstacle obstacle) throws IllegalArgumentException {
		if (getObstacle() != null)
			throw new IllegalArgumentException("Obstacle already exists on this square.");
		
		this.obstacle = obstacle;
	};
	
	@Override
	public String toString() {
		String s = "Square [ ";
		s += "Obstacle: " + obstacle;
		s += "Inv: " + this.getInventory();
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
