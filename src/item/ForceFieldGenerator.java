package item;

import effect.player.PlayerEffect;
import game.Player;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ForceFieldGenerator extends Item {

	private boolean active;
	
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
}
