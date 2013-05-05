package item;

import game.Player;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import move.Movable;

public class Flag extends Item {

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

	@Override
	public void affect(Movable movable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void affect(Player player) throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void affect(IdentityDisc identityDisc) throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSameType(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

}
