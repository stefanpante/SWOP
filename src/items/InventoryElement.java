package items;

public interface InventoryElement {
	
	
	public void accept(InventoryElementVisitor visitor);
}
