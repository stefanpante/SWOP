package square;


import move.MovableEffect;
import game.Player;
import item.IdentityDisc;
import item.inventory.SquareInventory;

import move.Movable;
import square.field.Field;
import square.obstacle.Obstacle;
import square.power.Power;
import square.power.RegularPower;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

import java.util.ArrayList;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers en Stefan Pante
 */
@NotNull
public class Square implements MovableEffect {
		
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
     * The fields of this Square.
     */
    ArrayList<Field> fields = new ArrayList<Field>();
	
	/**
	 * Zero argument constructor for a square.
	 */
	public Square (){
		this.inventory = new SquareInventory();
		this.setPower(new RegularPower());
	}
	
	/**
	 * Returns the state of the square.
	 */
	public Power getPower() {
		return this.power;
	}
	
	/**
	 * Sets the power of a square.
	 * @param power
	 */
	public void setPower(Power power) {
		this.power = power;
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

    public void addField(Field field){
        fields.add(field);
    }

    public void removeField(Field field){
        fields.remove(field);
    }

    public ArrayList<Field> getAllFields(){
        return new ArrayList<Field>(this.fields);
    }
	
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
     *          True    if there is a field
	 * 			False	If there is no obstacle.
	 */
	public boolean isObstructed(){
			return obstacle != null || isCoveredByField();
	}

    public boolean isCoveredByField(){
        return getAllFields().size() > 0;
    }

    @Override
    public void affect(Movable movable) {
        getInventory().affect(movable);
        getPower().affect(movable);
    }

    @Override
    public void affect(Player player) {
        // Double dispatch is called on the lower level
    }

    @Override
    public void affect(IdentityDisc identityDisc) {
        // Double dispatch is called on the lower level
    }


}