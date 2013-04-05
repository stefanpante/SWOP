package item.visitor;

import item.inventory.*;

/**
 * Visitable interface for items.
 * 
 * @author Dieter
 */
public interface VisitableItem {
	
	public void acceptAddPlayerInventory(PlayerInventory playerInventory) throws IllegalStateException;
	
	public void acceptRemovePlayerInventory(PlayerInventory playerInventory) throws IllegalStateException;
		
	public void acceptAddSquareInventory(SquareInventory squareInventory) throws IllegalStateException;
		
	public void acceptRemoveSquareInventory(SquareInventory squareInventory) throws IllegalStateException;
}