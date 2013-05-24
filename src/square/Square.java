package square;

import effect.Effect;
import effect.EffectPriority;
import effect.imp.EffectMediator;
import game.Player;
import item.IdentityDisc;
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
     * The effect mediator of this square.
     */
    private EffectMediator effectMediator;
	/**
	 * List of items on this square
	 */
	private ArrayList<Item> items;

	/**
	 * The effects of this Square.
	 */
	private ArrayList<Effect> effects;

	public Square(){
		this.items = new ArrayList<>();
		this.effects = new ArrayList<>();
        this.effectMediator = new EffectMediator();
	}

	/**
	 * Adds the given effect to the list of effects.
	 */
	public void addSquareEffect(Effect effect) throws IllegalArgumentException{
		if(effect == null)
			throw new IllegalArgumentException("The effect cannot be null.");
		effects.add(effect);
	}

	/**
	 * Removes the given effect from the list of effects.
	 */
	public void removeSquareEffect(Effect effect) throws IllegalArgumentException{
        if(effect == null)
            throw new NoSuchElementException("The effect to be removed cannot be null.");
		if(!effects.contains(effect))
			throw new NoSuchElementException("Cannot remove " +effect+ " from " + this);
		effects.remove(effect);
	}

	public ArrayList<Effect> getAllSquareEffects(){
		return new ArrayList<Effect>(this.effects);
	}

    public ArrayList<Effect> getAllItemEffects(){
        ArrayList<Effect> result = new ArrayList<>();
        for(Item i : getAllItems()){
            result.addAll(i.getEffects());
        }
        return result;
    }

    private ArrayList<Effect> getAllEffects(){
        ArrayList<Effect> result = new ArrayList<>(this.getAllSquareEffects());
        result.addAll(getAllItemEffects());
        return result;
    }

    private ArrayList<Effect> getResultingEffects(){
        return effectMediator.getResultingEffects(getAllEffects());
    }

    public void affect(Player player){
        for(Effect e: getResultingEffects()){
            e.execute(player);
        }
    }

    public void affect(IdentityDisc identityDisc){
        for(Effect e: getResultingEffects()){
            e.execute(identityDisc);
        }
    }

	@Override
	public String toString() {
		String s = "Square (" +hashCode()+ ") [ ";
		s += items;
		s += ", ";
        s += getAllItemEffects();
        s += ", ";
		s += effects;
		s += " ]";
        s += "(isObstructed: "+isObstructed()+")";
		return s;
	}

	@Override
	public ArrayList<Item> getAllItems(){
		return new ArrayList<Item>(items);
	}

    @Override
    public boolean isSameType(ItemContainer itemContainer) {
        return itemContainer instanceof Square;
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
		return filterItemsByType(item).size() > 0;
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

    public boolean isObstructed(){
        return effectMediator.getResultingEffectsWithPriorityAbove(getAllEffects(), EffectPriority.Move).size() > 0;
    }

    @Override
    public boolean isSameType(GridElement gridElement) {
        return gridElement instanceof Square;
    }
}