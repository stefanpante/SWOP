package item;

import item.inter.Movable;
import game.Player;
import square.Square;


/**
 * The ForceFieldGenerator generates a line segment between
 * another force field generator if it is within an appropriate distance
 * 
 * The Generator holds a reference to the line segment.
 * This line segment switches on and off.
 * 
 * @author Dieter Castel, Jonas Devlieghere and Stefan Pante
 */
public class ForceFieldGenerator extends Item{

    private boolean active;

    @Override
    public boolean canAddTo(Square square) {
        return !square.hasItem(this);
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
	public void onMoveToEffect(Movable movable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveToEffect(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveToEffect(IdentityDisc identityDisc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(Movable movable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStandOnEffect(IdentityDisc identityDisc) {
		// TODO Auto-generated method stub
		
	}
}
