package square;

import effect.Effect;
import item.Item;
import item.inter.ItemContainer;

import item.inter.Movable;
import square.field.Field;
import square.obstacle.Obstacle;
import notnullcheckweaver.NotNull;
import notnullcheckweaver.Nullable;
import util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Square class
 * 
 * @author Dieter Castel, Jonas Devlieghere   en Stefan Pante
 */
@NotNull
public class Square implements ItemContainer {

    /**
     * List of items on this square
     */
    private ArrayList<Item> items;

	/**
	 * The obstacle of this Square object.
	 */
	@Nullable
	private Obstacle obstacle;

	/**
	 * The effects of this Square.
	 */
	private ArrayList<Effect> effects;
	
	/**
	 * Contains the neighbors of this square
	 */
	private  HashMap<Direction, Square> neighbors;

	
	public Square(){
		this.items = new ArrayList<Item>();
        this.effects = new ArrayList<Effect>();
	}
	/**
	 * Returns the value of the obstacle of this Square as an Obstacle.
	 *
	 * @return 	An object of the Obstacle class.
	 * 			| Obstacle
	 */
	public Obstacle getObstacle() {
		return obstacle;
	}

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
	}

	public void addEffect(Effect effect){
		effects.add(effect);
	}

	public void removeField(Effect effect){
        effects.remove(effect);
	}

	public ArrayList<Effect> getAllEffects(){
		return new ArrayList<Effect>(this.effects);
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
	    return isCoveredByObstacle();
	}
	
	public boolean isCoveredByObstacle(){
		return obstacle != null;
	}

	/**
	 * Sets all the neighbors of the square instance
	 * @param neighbors	the neighbors to be set.
	 */
	public void setNeighbors(HashMap<Direction, Square> neighbors){
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
	public Square getNeighbor(Direction direction) throws NoSuchElementException {
        if(!neighbors.containsKey(direction))
            throw new NoSuchElementException("There is no neighbor in the given direction (" + direction + ")");
		return neighbors.get(direction);
	}

    @Override
    public ArrayList<Item> getAllItems(){
        return new ArrayList<Item>(items);
    }

    @Override
    public void addItem(Item item){
        if(!isValidItem(item))
            throw new IllegalArgumentException("The item cannot be null");
        if(!item.canAddTo(this))
            throw new IllegalArgumentException("Cannot add " +item+ " to " + this);
        items.add(item);
        item.setContainer(this);
    }

    private boolean isValidItem(Item item) {
        return item != null;
    }

    @Override
    public void removeItem(Item item){
        if(!isValidItem(item))
            throw new IllegalArgumentException("The item cannot be null");
        if(!hasItem(item))
            throw new IllegalArgumentException("Cannot remove" +item+ " from " + this);
        items.remove(item);
    }

    @Override
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

    /**
     * Accepts the moving of a movable onto a square and gives the movable its effects.
     *
     * @param   movable
     *          The movable which will be affected.
     */
    public void acceptMove(Movable movable){
        for(Item it: items){
            it.onMoveToEffect(movable);
        }
//       µ
    }
}