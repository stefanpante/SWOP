package item.visitor;

import item.inventory.*;

/**
 * Visitable interface for items.
 * 
 * @author Dieter
 */
public interface VisitableItem {
		
	public void acceptAddSquareInventory(SquareInventory sqInv) throws IllegalStateException;
		
	public void acceptRemoveSquareInventory(SquareInventory sqInv) throws IllegalStateException;
}