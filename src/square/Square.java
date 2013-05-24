package square;

import effect.Effect;
import effect.imp.EffectMediator;
import game.Player;
import item.IdentityDisc;
import item.Item;
import item.inter.ItemContainer;
import notnullcheckweaver.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull
    private final EffectMediator effectMediator;
	/**
	 * List of items on this square
	 */
	@NotNull
    private final ArrayList<Item> items;

	/**
	 * The effects of this Square.
	 */
	@NotNull
    private final ArrayList<Effect> effects;

	public Square(){
		this.items = new ArrayList<>();
		this.effects = new ArrayList<>();
        this.effectMediator = new EffectMediator();
	}

	/**
	 * Adds the given effect to the list of effects.
	 */
	public void addSquareEffect(@Nullable Effect effect) throws IllegalArgumentException{
		if(effect == null)
			throw new IllegalArgumentException("The effect cannot be null.");
		effects.add(effect);
	}

	/**
	 * Removes the given effect from the list of effects.
	 */
	public void removeSquareEffect(@Nullable Effect effect) throws IllegalArgumentException{
        if(effect == null)
            throw new NoSuchElementException("The effect to be removed cannot be null.");
		if(!effects.contains(effect))
			throw new NoSuchElementException("Cannot remove " +effect+ " from " + this);
		effects.remove(effect);
	}

	@NotNull
    public ArrayList<Effect> getAllSquareEffects(){
		return new ArrayList<>(this.effects);
	}

    @NotNull
    ArrayList<Effect> getAllItemEffects(){
        ArrayList<Effect> result = new ArrayList<>();
        for(Item i : getAllItems()){
            result.addAll(i.getEffects());
        }
        return result;
    }

    @NotNull
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

	@NotNull
    @Override
	public String toString() {
		String s = "Square (" +hashCode()+ ") [ ";
		s += items;
		s += ", ";
        s += getAllItemEffects();
        s += ", ";
		s += effects;
		s += " ]";
		return s;
	}

	@NotNull
    @Override
	public ArrayList<Item> getAllItems(){
		return new ArrayList<>(items);
	}

    @Override
    public boolean isSameType(ItemContainer itemContainer) {
        return itemContainer instanceof Square;
    }

    @Override
	public void addItem(@NotNull Item item){
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
	public boolean hasType(@NotNull Item item) {
		return filterItemsByType(item).size() > 0;
	}

	@NotNull
    @Override
	public ArrayList<Item> filterItemsByType(@NotNull Item item) {
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
	private boolean isValidItem(@Nullable Item item) {
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