package item;

import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;

public class ForceFieldGenerator extends Item {

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

}
