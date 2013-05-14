package item;

import effect.Effect;
import game.Player;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import move.Movable;

/**
 * Represents a flag item. Belongs to a certain player.
 * @author Stefan
 *
 */
public class Flag extends Item {

	private Player player;
	
	
	public Flag(){
		
	}
	public Flag(Player player){
		this.player = player;
	}
	@Override
	public void acceptAddPlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.addFlag(this);

	}

	@Override
	public void acceptRemovePlayerInventory(PlayerInventory plInv)
			throws IllegalStateException {
		plInv.removeFlag(this);

	}

	@Override
	public void acceptAddSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.addFlag(this);

	}

	@Override
	public void acceptRemoveSquareInventory(SquareInventory sqInv)
			throws IllegalStateException {
		sqInv.addFlag(this);

	}

	@Override
	public boolean isSameType(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item copy() {
		return new Flag();
	}
	
	public Player getPlayer(){
		return this.player;
	}
	@Override
	public Effect getEffect() {
		// TODO Auto-generated method stub
		return null;
	}

}
