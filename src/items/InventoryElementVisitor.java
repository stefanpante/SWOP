package items;

public interface InventoryElementVisitor {
	
	void visit(LightGrenade lightGrenade);
	
	void visit(Item item);
	
}
