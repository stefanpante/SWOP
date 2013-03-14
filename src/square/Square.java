package square;

import items.SquareInventory;

import square.obstacles.IObstacle;
import square.state.PowerFailureState;
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
public class Square implements Penalty{
		
	/**
	 *  Contains all items which were used on this square.
	 */
	private SquareInventory inventory;
	
	/**
	 * State of the current square. May be a power failure.
	 */
	private State state;
	// Sets for how many turns the current state will be active
	private int remainingTurns;
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
		this.state = RegularState.getInstance();
		this.remainingTurns = 0;

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
	 * @param	regularState
	 * @throws	IllegalArgumentException	When the given state is null.
	 */
	public void setState(State state) throws IllegalArgumentException {
		if(!isValidState(state))
			throw new IllegalArgumentException("State cannot be null.");
		this.state = state;
	}
	
	/**
	 * In order for a state to be valid it must not be null.
	 * 
	 * @param	state
	 * @return	True	If state is not null.
	 * 			False	If state is null.
	 */
	public boolean isValidState(State state) {
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
	public IObstacle getObstacle() {
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
	public void setObstacle(IObstacle obstacle) throws IllegalArgumentException {
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
	
	/**
	 * Ends the turn. Checks if the state of the square needs to change
	 */
	public void endTurn(){
		if(remainingTurns == 0){
			this.state = RegularState.getInstance();
		}
		else if(remainingTurns > 0){
			remainingTurns--;
		}
	}
	
	public void powerFail(){
		remainingTurns = PowerFailureState.TURNS_ACTIVE;
		setState(PowerFailureState.getInstance());
	}
	
	public int getPenalty() {
		int res = 0;
		if(this.getInventory().hasActiveLightGrenade())
			res = this.getInventory().getLightGrenade().getPenalty();
		return res + state.getPenalty(); 
	}

	public boolean hasPenalty() {
		return state.hasPenalty() || this.getInventory().hasActiveLightGrenade();
	}
}
