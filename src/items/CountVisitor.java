package items;

public class CountVisitor implements InventoryElementVisitor {
	private int lightGrendadeCount;
	private int itemCount;

	public CountVisitor() {
		
	}

	public void visit(LightGrenade lightGrenade) {
		
	}

	public void visit(Item item) {
		itemCount++;
	}
	
	

}
