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
		assertFalse(inventory.canHaveAsItemIndex(inventory.getAllItems().size()));
		assertFalse(inventory.canHaveAsItemIndex(-2));
	}
	
	@Test
	public void testGetItem(){
		Inventory inventory = new Inventory();
		Item item = null;
		for(int i = 0; i < 10; i ++){
			item = new LightGrenade();
			inventory.addItem(item);
		}
		
		try{
			inventory.getItem(-1);
			fail("Index shouldn't take negative values");
		}
		catch(Exception e){}
		
		try{
			inventory.getItem(10);
			fail("shouldnt be able to get item");
		}
		catch(Exception e){}

		try{
			inventory.getItem(9);
			inventory.getItem(0);
			inventory.getItem(4);
		}
		catch(Exception e){ 
			fail("Given indices should be in range");
		}
	}
	
	@Test 
	public void testIsValidItem(){
		Item item = null;
		item = new LightGrenade();
		
		assertFalse(Inventory.isValidItem(null));
		assertTrue(Inventory.isValidItem(item));
		item.activate();
		assertFalse(Inventory.isValidItem(item));
	}
	
	@Test
	public void testIsValidMaximumSize(){
		assertFalse(Inventory.isValidMaximumSize(-1));
		assertTrue(Inventory.isValidMaximumSize(0));
		assertTrue(Inventory.isValidMaximumSize(10));
	}
	
	@Test
	public void testSetMaximumSize(){
		Inventory inventory = new Inventory(20);
		try{
			inventory.setMaximumSize(-1);
			fail("Size should not be negative");
		} catch(Exception e){}
		
		try{
			inventory.setMaximumSize(25);
		}catch(Exception e){
			fail("Setting maximum size to a greater size should be allowed");
		}
		for(int i = 0; i < 24; i ++){
			Item item = new LightGrenade();
			inventory.addItem(item);
		}
		try{
			inventory.setMaximumSize(20);
			fail("Should not succeed when there are more elements in the inventory than the desired maximum size");
		} 
		catch(Exception e){}
		
	}
		
	


}
