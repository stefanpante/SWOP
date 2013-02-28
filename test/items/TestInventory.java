package items;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestInventory {

	@Test
	public void testEmptyInventory() {
		Inventory inventory = new Inventory();
		
		Item item = new LightGrenade();
		
		assertTrue(inventory.isEmpty());
		
		inventory.addItem(item);
		assertFalse(inventory.isEmpty());
	}
	
	@Test
	public void testSize() {
		Inventory inventory = new Inventory();
		Item item = new LightGrenade();
		
		assertEquals(inventory.getSize(), 0);
		
		inventory.addItem(item);
		assertEquals(inventory.getSize(), 1);
	}
	
	@Test
	public void testMaximumSize() {
		Inventory inventory = new Inventory(10);
		
		assertEquals(inventory.getMaximumSize(), 10);
	}
	
	@Test
	public void testAddingItem() {
		Item item = new LightGrenade();
		Inventory inventory = new Inventory();
		
		inventory.addItem(item);
	}
	 
	@Test(expected=IllegalStateException.class)
	public void testAddingOverCapacity() {
		Inventory inventory = new Inventory(1);
		
		inventory.addItem(new LightGrenade());
		inventory.addItem(new LightGrenade());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddingDuplicate() {
		Inventory inventory = new Inventory();
		Item item = new LightGrenade();
		
		inventory.addItem(item);
		inventory.addItem(item);
	}
	
	@Test
	public void testCanHaveAsItem() {
		Inventory inventory = new Inventory();
		Item item = new LightGrenade();
		
		assertTrue(inventory.canHaveAsItem(item));
		inventory.addItem(item);
		assertFalse(inventory.canHaveAsItem(item));
	}
	
	@Test
	public void testHasItem() {
		Inventory inventory = new Inventory();
		Item item = new LightGrenade();
		
		assertFalse(inventory.hasItem(item));
		inventory.addItem(item);
		assertTrue(inventory.hasItem(item));
	}
	
	@Test
	public void testTakeItem() {
		Inventory inventory = new Inventory();
		Item item = new LightGrenade();
		
		inventory.addItem(item);
		assertTrue(inventory.getAllItems().contains(item));
		
		inventory.take(item);
		assertFalse(inventory.getAllItems().contains(item));
		
		try{
			inventory.take(item);
			fail("You shouldn't be able to take an item that isn't in the inventory");
		} catch(Exception e){}
		
		
	}
	
	@Test
	public void testCanHaveAsItemIndex(){
		Inventory inventory = new Inventory();
		Item item = null;
		for(int i = 0; i < 10; i ++){
			item = new LightGrenade();
			inventory.addItem(item);
		}
		
		assertTrue(inventory.canHaveAsItemIndex(0));
		assertTrue(inventory.canHaveAsItemIndex(inventory.getAllItems().size() - 1));
		assertTrue(inventory.canHaveAsItemIndex(inventory.getAllItems().size()));
		assertFalse(inventory.canHaveAsItemIndex(-2));
	}
		
	


}