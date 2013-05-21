package item;

import game.Player;
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
public class ForceFieldGenerator extends Item{

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
		this.activate();
	}

	@Override
	public boolean isSameType(Item item) {
        return item instanceof ForceFieldGenerator;
    }

    public boolean isActive() {
        return this.active;
    }

    public void activate(){
        this.active = true;
    }

    @Override
    public void affect(Player player) throws IllegalStateException {
         //TODO: see if there is something to do here or if it is handled elsewhere.
    }

    @Override
    public void affect(IdentityDisc identityDisc) throws IllegalStateException {
          //Nothing specific to do here.
    }
}
