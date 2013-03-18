package items;

import static org.junit.Assert.*;

import  org.junit.Assert.*;

import org.junit.Test;

public class TestInventory {

	@Test
	public void testEmptyInventory() {
		SquareInventory si = new SquareInventory();
		PlayerInventory pi = new PlayerInventory();
		
		Item item = new LightGrenade();
		
		assertTrue(si.isEmpty());
		assertTrue(pi.isEmpty());
		
		si.addItem(item);
		pi.addItem(item);
		assertFalse(si.isEmpty());
		assertFalse(pi.isEmpty());
	}
	
	@Test
	public void testSize() {
		Inventory inventory = new PlayerInventory();
		Item item = new LightGrenade();
		
		assertEquals(inventory.getSize(), 0);
		
		inventory.addItem(item);
		assertEquals(inventory.getSize(), 1);
	}
	
	@Test
	public void testMaximumSize() {
		Inventory inventory = new SquareInventory(10);
		
		assertEquals(inventory.getMaximumSize(), 10);
	}
	
	@Test
	public void testAddingItem() {
		Item item = new LightGrenade();
		Inventory inventory = new PlayerInventory();
		
		inventory.addItem(item);
	}
	 
	@Test(expected=IllegalStateException.class)
	public void testAddingOverCapacity() {
		Inventory inventory = new SquareInventory(1);
		
		inventory.addItem(new Item());
		inventory.addItem(new Item());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddingDuplicate() {
		Inventory inventory = new PlayerInventory();
		Item item = new LightGrenade();
		
		inventory.addItem(item);
		inventory.addItem(item);
	}
	
	@Test
	public void testCanHaveAsItem() {
		SquareInventory si = new SquareInventory();
		PlayerInventory pi = new PlayerInventory();
		Item item = new LightGrenade();
		
		assertTrue(si.canHaveAsItem(item));
		si.addItem(item);
		assertFalse(si.canHaveAsItem(item));
		
		assertTrue(pi.canHaveAsItem(item));
		pi.addItem(item);
		assertFalse(pi.canHaveAsItem(item));
	}
	
	@Test
	public void testHasItem() {
		Inventory inventory = new PlayerInventory();
		Item item = new LightGrenade();
		
		assertFalse(inventory.hasItem(item));
		inventory.addItem(item);
		assertTrue(inventory.hasItem(item));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testTakeItem() {
		Inventory inventory = new PlayerInventory();
		Item item = new LightGrenade();
		
		inventory.addItem(item);
		assertTrue(inventory.getAllItems().contains(item));
		
		inventory.take(item);
		assertFalse(inventory.getAllItems().contains(item));
		
		inventory.take(item);
	}
	
	@Test
	public void testCanHaveAsItemIndex(){
		Inventory inventory = new SquareInventory();
		Item item = null;
		for(int i = 0; i < 10; i ++){
			item = new Item();
			inventory.addItem(item);
		}
		
		assertTrue(inventory.canHaveAsItemIndex(0));
		assertTrue(inventory.canHaveAsItemIndex(inventory.getAllItems().size() - 1));
		assertFalse(inventory.canHaveAsItemIndex(inventory.getAllItems().size()));
		assertFalse(inventory.canHaveAsItemIndex(-2));
	}
	
	@Test
	public void testGetItem(){
		Inventory inventory = new SquareInventory();
		Item item = null;
		for(int i = 0; i < 10; i ++){
			item = new Item();
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
		Item item = new LightGrenade();
		Item item2 = new Item();
		
		assertFalse(Inventory.isValidItem(null));
		assertTrue(Inventory.isValidItem(item));
		assertTrue(Inventory.isValidItem(item2));
	}
	
	@Test
	public void testIsValidMaximumSize(){
		assertFalse(Inventory.isValidMaximumSize(-1));
		assertTrue(Inventory.isValidMaximumSize(0));
		assertTrue(Inventory.isValidMaximumSize(10));
	}
	
	@Test
	public void testSetMaximumSize(){
		Inventory inventory = new SquareInventory(20);
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
			Item item = new Item();
			inventory.addItem(item);
		} 
		
	}
}
