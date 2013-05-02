package item;

import game.Player;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

import java.util.Observable;
import java.util.Observer;

public class ForceFieldGenerator extends Item implements Observer{

    public static final int ACTIONS_ON = 2;
    public static final int ACTIONS_OFF = 2;
    private static final int TOTAL_ACTIONS = ACTIONS_ON + ACTIONS_OFF;

	private boolean active;
    private int counter;

	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.addForceFieldGenerator(this);

	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.removeForceFieldGenerator(this);

	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.addForceFieldGenerator(this);
	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.removeForceFieldGenerator(this);

	}

	public boolean isActive(){
		return this.active;
	}

    private void setActive(boolean active){
        this.active = active;
    }

	@Override
	public boolean isSameType(Item item) {
		if(item instanceof ForceFieldGenerator)
			return true;
		return false;
	}

    @Override
    public void affect(Player player) {
        // TODO: Implement the effect of a player entering this force field
    }

    @Override
    public void update(Observable o, Object arg) {
        counter++;
        if(counter <= ACTIONS_ON)
            setActive(true);
        if(counter > ACTIONS_ON && counter <= ACTIONS_ON + ACTIONS_OFF)
            setActive(false);
        if(counter >= TOTAL_ACTIONS)
            counter = 0;
    }
}
