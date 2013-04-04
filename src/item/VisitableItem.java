package item;

import item.inventory.*;

public interface VisitableItem {
	
	public void acceptPlayerInventory(PlayerInventory plInv);
	
	public void acceptSquareInventory(SquareInventory sqInv);
}
