package square;


import item.Item;
import item.ItemContainer;
import move.MovableEffect;
import game.Player;
import item.IdentityDisc;

import move.Movable;
import square.field.Field;
import square.obstacle.Obstacle;
import square.power.Power;

import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere   en Stefan Pante
 */
@NotNull
public class Square implements MovableEffect, ItemContainer {

    /**
     * List of items on this square
     */
    ArrayList<Item> items;


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
	private ArrayList<Field> fields = new ArrayList<Field>();
	
	/**
	 * Contains the neighbors of this square
	 */
	//TODO: SHould be final, but cannot be set if final. Needs to be added in constructor.
	private  HashMap<Direction, Square> neighbors;



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
	return isCoveredByObstacle() || isCoveredByField();
	}
	
	public boolean isCoveredByField(){
		return getAllFields().size() > 0;
	}
	
	public boolean isCoveredByObstacle(){
		return obstacle != null;
	}

	/**
	 * Sets all the neighbors of the square instance
	 * @param neighbors	the neighbors to be set.
	 */
	public void setNeighbors(final HashMap<Direction, Square> neighbors){
		this.neighbors = neighbors;
	}
	
	/**
	 * Returns the neighbors of the square.
	 * @return
	 */
	public HashMap<Direction, Square> getNeighbors(){
		return new HashMap<Direction, Square>(neighbors);
	}
	
	/**
	 * Returns the neighbor in the given direction
	 * @param direction	The direction of the neighbor.
	 * @return the neighbor in the given direction
	 */
	public Square getNeighbor(Direction direction){
		return neighbors.get(direction);
	}

    public ArrayList<Item> getAllItems(){
        return new ArrayList<Item>(items);
    }



    public void addItem(Item item){
        if(item.canAddTo(this))
            throw new IllegalArgumentException("Cannot add " +item+ " to " + this);
        items.add(item);
        item.setContainer(this);
    }

    public void removeItem(Item item){
        if(!hasItem(item))
            throw new IllegalArgumentException("Cannot remove" +item+ " from " + this);
    }

    public boolean hasItem(Item item){
        return items.contains(item);
    }

    @Override
    public boolean hasType(Item item) {
        return getType(item) != null;
    }

    @Override
    public Item getType(Item item) {
        for(Item it : getAllItems()){
            if(item.isSameType(it))
                return it;
        }
        return null;
    }

    @Override
    public void affect(Movable movable) {
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
    }
}