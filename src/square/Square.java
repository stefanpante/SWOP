package square;

import effect.Effect;
import item.Item;
import item.inter.ItemContainer;
import notnullcheckweaver.NotNull;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Square class
 *
 * @author Dieter Castel, Jonas Devlieghere en Stefan Pante
 */
@NotNull
public class Square extends GridElement implements ItemContainer {

	/**
	 * List of items on this square
	 */
	private ArrayList<Item> items;

	/**
	 * The effects of this Square.
	 */
	private ArrayList<Effect> effects;




	public Square(){
		this.items = new ArrayList<Item>();
		this.effects = new ArrayList<Effect>();
	}

	/**
	 * Adds the given effect to the list of effects.
	 */
	public void addEffect(Effect effect) throws IllegalArgumentException{
		if(effect == null)
			throw new IllegalArgumentException("The effect can not be null.");
		effects.add(effect);
	}

	/**
	 * Removes the given effect from the list of effects.
	 */
	public void removeEffect(Effect effect) throws IllegalArgumentException{
        if(effect == null)
            throw new NoSuchElementException("The effect can not be null.");
		if(!effects.contains(effect))
			throw new NoSuchElementException("The square does not contain the given effect.");
		effects.remove(effect);
	}

	public ArrayList<Effect> getAllEffects(){
		return new ArrayList<Effect>(this.effects);
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
		return filterItemsByType(item) != null;
	}

	@Override
	public ArrayList<Item> filterItemsByType(Item item) {
        ArrayList<Item> result = new ArrayList<>();
		for(Item it : getAllItems()){
			if(item.isSameType(it))
				result.add(it);
		}
		return result;
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

    @Override
    public boolean isObstacle() {
        return false;
    }

    @Override
    public boolean isSameType(GridElement gridElement) {
        return gridElement instanceof Square;
    }
}
