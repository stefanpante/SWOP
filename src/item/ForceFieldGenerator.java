package item;

import effect.player.PlayerEffect;
import game.Player;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ForceFieldGenerator extends Item implements PlayerEffect {

	private boolean on;
	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	public boolean isOn(){
		return on;
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
