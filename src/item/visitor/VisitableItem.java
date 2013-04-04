package item.visitor;

import item.inventory.*;

public interface VisitableItem {
	
	public void acceptAddPlayerInventory(PlayerInventory plInv) throws IllegalStateException;
	
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException;
	
	public void acceptRemovePlayerInventory(PlayerInventory plInv) throws IllegalStateException;
	
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException;
}
