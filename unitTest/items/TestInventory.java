package items;

import static org.junit.Assert.*;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.inventory.Inventory;
import item.inventory.PlayerInventory;
import item.inventory.SquareInventory;
import item.launchable.ChargedIdentityDisc;
import item.launchable.IdentityDisc;

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
		
		inventory.addItem(new Teleport());
		inventory.addItem(new LightGrenade());
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
		
		inventory.removeItem(item);
		assertFalse(inventory.getAllItems().contains(item));
		
		inventory.removeItem(item);
	}
	
	@Test
	public void testGetItem(){
		Inventory inventory = new SquareInventory();
		Item item1 = new IdentityDisc();
		Integer hash1 = item1.hashCode();
		Item item2 = new IdentityDisc();
		Integer hash2 = item1.hashCode();
		Item item3 = new LightGrenade();
		Integer hash3 = item1.hashCode();
		Item item4 = new ChargedIdentityDisc();
		Integer hash4 = item1.hashCode();
		Item item5 = new Teleport();
		Integer hash5 = item1.hashCode();
		
		inventory.addItem(item1);
		inventory.addItem(item2);
		inventory.addItem(item3);
		inventory.addItem(item4);
		inventory.addItem(item5);
		
		assertEquals(item1, inventory.getItem(hash1));
		assertEquals(item1, inventory.getItem(hash2));
		assertEquals(item1, inventory.getItem(hash3));
		assertEquals(item1, inventory.getItem(hash4));
		assertEquals(item1, inventory.getItem(hash5));		
	}
	
	@Test 
	public void testIsValidItem(){
		Item item = new LightGrenade();
		Item item2 = new IdentityDisc();
		
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
			Item item = new IdentityDisc();
			inventory.addItem(item);
		} 
		
	}
}
