package item;

import game.Player;
import item.inter.Activatable;
import square.Square;


/**
 * The ForceFieldGenerator generates a line segment between
 * another force field generator if it is within an appropriate distance
 * 
 * The Generator holds a reference to the line segment.
 * This line segment switches on and off.
 * 
 * @author vincentreniers
 */
public class ForceFieldGenerator extends Item implements Activatable {

    private boolean dropped;
    private boolean active;

    @Override
    public boolean canAddTo(Square square) {
        return (!square.hasItem(this) && !square.hasType(this));
    }

    /**
	 * If the item is used, the state is changed to used.
	 */
	@Override
	public void notifyUse() {
		this.drop();
	}

	@Override
	public boolean isSameType(Item item) {
        return item instanceof ForceFieldGenerator;
    }
	
	/**
	 * Checks if the item given is a force field generator.
	 */
	public static boolean isForceFieldGenerator(Item item){
        return item instanceof ForceFieldGenerator;
    }

    @Override
    public boolean isDropped() {
        return this.dropped;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void drop() throws IllegalStateException {
        this.dropped = true;
    }

    @Override
    public void activate() throws IllegalStateException {
        this.active = true;
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
        // TODO: Nothing
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
        // TODO: Nothing
    }
}
