package square;


import effect.Effect;
import item.inventory.SquareInventory;

import square.obstacle.Obstacle;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
@NotNull
public class Square{
	
	private final long id;
		
	/**
	 *  Contains all items which were used on this square.
	 */
	private SquareInventory inventory;
	
	/**
	 * State of the current square. May be a power failure.
	 */
	private Power power;
	
	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private Obstacle obstacle;
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.inventory = new SquareInventory();
		this.power = new Power();
		this.id = System.nanoTime();
	}
	
	/**
	 * Returns the state of the square.
	 */
	public Power getPower() {
		return this.power;
	}
	
	/**
	 * In order for a state to be valid it must not be null.
	 * 
	 * @param	state
	 * @return	True	If state is not null.
	 * 			False	If state is null.
	 */
	public boolean isValidState(Power state) {
		if(state == null)
			return false;
		else
			return true;
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
	public Obstacle getObstacle() {
		return obstacle;
	};

	/**
	 * Sets the value of the obstacle of Square if the given value is valid. 
	 * 
	 * @param 	obstacle
	 *			The obstacle to set.
	 * @post 	The given value is the current value of the obstacle of this Square.
	 */
	@Nullable
	public void setObstacle(Obstacle obstacle) throws IllegalArgumentException {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Square other = (Square) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
}