package items;

import static org.junit.Assert.*;
import items.inventory.PlayerInventory;

import org.junit.AfterClass;
import org.junit.Test;

public class TestPlayerInventory {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPlayerInventoryMaximumSize() {
		PlayerInventory pi1 = new PlayerInventory();
		PlayerInventory pi2 = new PlayerInventory();
		PlayerInventory pi3 = new PlayerInventory();

		assertEquals(6, pi1.getMaximumSize());
		assertEquals(6, pi2.getMaximumSize());
		assertEquals(6, pi3.getMaximumSize());
	}
	
	@Test
	public void testPlayerInventorySize() {
		PlayerInventory pi1 = new PlayerInventory();
		
		LightGrenade lg1 = new LightGrenade();
		LightGrenade lg2 = new LightGrenade();
		LightGrenade lg3 = new LightGrenade();

		Item it1 = new Item();
		Item it2 = new Item();
		Item it3 = new Item();

		assertEquals(0, pi1.getSize());
		pi1.addItem(it1);
		assertEquals(1, pi1.getSize());
		pi1.addItem(lg1);
		assertEquals(2, pi1.getSize());
		pi1.addItem(it2);
		assertEquals(3, pi1.getSize());
		pi1.addItem(lg2);
		assertEquals(4, pi1.getSize());
		pi1.addItem(it3);
		assertEquals(5, pi1.getSize());
		pi1.addItem(lg3);
		assertEquals(6, pi1.getSize());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPlayerInventorySize2() {
		PlayerInventory pi1 = new PlayerInventory();
		
		LightGrenade lg1 = new LightGrenade();
		LightGrenade lg2 = new LightGrenade();
		LightGrenade lg3 = new LightGrenade();

		Item it1 = new Item();
		Item it2 = new Item();
		Item it3 = new Item();
		Item it4 = new Item();

		assertEquals(0, pi1.getSize());
		pi1.addItem(it1);
		assertEquals(1, pi1.getSize());
		pi1.addItem(lg1);
		assertEquals(2, pi1.getSize());
		pi1.addItem(it2);
		assertEquals(3, pi1.getSize());
		pi1.addItem(lg2);
		assertEquals(4, pi1.getSize());
		pi1.addItem(it3);
		assertEquals(5, pi1.getSize());
		pi1.addItem(lg3);
		assertEquals(6, pi1.getSize());

		pi1.addItem(it4);
		//Should throw exception cause limit is 6.
	}

}
