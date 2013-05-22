package square;

import effect.Effect;
import effect.NewEffect;
import item.Item;
import item.inter.ItemContainer;

import item.inter.Movable;
import notnullcheckweaver.NotNull;
import util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Square class
 *
 * @author Dieter Castel, Jonas Devlieghere en Stefan Pante
 */
@NotNull
public class Square implements ItemContainer {

    /**
     * List of items on this square
     */
    private ArrayList<Item> items;

    /**
     * The effects of this Square.
     */
    private ArrayList<NewEffect> effects;

    /**
     * Contains the neighbors of this square
     */
    private  HashMap<Direction, Square> neighbors;


    public Square(){
        this.items = new ArrayList<Item>();
        this.effects = new ArrayList<NewEffect>();
    }

    /**
     * Adds the given effect to the list of effects.
     */
    public void addEffect(NewEffect effect) throws IllegalArgumentException{
        if(effect == null)
            throw new IllegalArgumentException("The effect can not be null.");
        effects.add(effect);
    }

    /**
     * Removes the given effect from the list of effects.
     */
    public void removeField(NewEffect effect) throws IllegalArgumentException{
        if(effects.contains(effect))
            throw new IllegalArgumentException("The effect can not be null.");
        effects.remove(effect);
    }

    public ArrayList<NewEffect> getAllEffects(){
        return new ArrayList<NewEffect>(this.effects);
    }

    @Override
    public String toString() {
        String s = "Square [ ";
        s += items;
        s += ", ";
        s += effects;
        s += " ]";
        return s;
    }

    /**
     * Sets all the neighbors of the square instance
     *
     * @param   neighbors
     *          The neighbors to be set.
     */
    public void setNeighbors(HashMap<Direction, Square> neighbors){
        this.neighbors = neighbors;
    }

    /**
     * Returns the neighbors of the square.
     *
     * @return  The neighbors of this square.
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
            throw new IllegalArgumentException("The given item is not valid (cannot be null)");
        if(!item.canAddTo(this))
            throw new IllegalArgumentException("Cannot add " +item+ " to " + this);
        items.add(item);
        item.setContainer(this);
    }

    @Override
    public void removeItem(Item item){
        if(!isValidItem(item))
            throw new IllegalArgumentException("The given item is not valid (cannot be null)");
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
     * Check whether the given item is valid.
     *
     * @param   item
     *          The item to be checked
     * @return  True if and only if the item is not null.
     */
    private boolean isValidItem(Item item) {
        return item != null;
    }

    /**
     * Accepts the moving of a movable onto a square and gives the movable its effects.
     *
     * @param   movable
     *          The movable which will be affected.
     */
    public void acceptMove(Movable movable){
        for(Item it: items){
            it.affect(movable);
        }
        for(NewEffect effect: getAllEffects()){
            effect.onMoveToEffect(movable);
        }
    }

    /**
     * Check whether this square is obstructed
     *
     * @return  True if and only if none of the effect blocks
     *          movement.
     */
    public boolean isObstructed(){
        boolean obstructed = false;
        for(NewEffect effect : getAllEffects()){
            if(effect.canMoveTo())
                obstructed = true;
        }
        return obstructed;
    }
}