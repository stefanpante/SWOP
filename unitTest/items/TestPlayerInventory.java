package items;

import static org.junit.Assert.*;

import item.ChargedIdentityDisc;
import item.Flag;
import item.IdentityDisc;
import item.Item;
import item.LightGrenade;
import item.Teleport;
import item.inventory.PlayerInventory;

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

		double size = 6;
		
		assertTrue(pi1.getMaximumSize() == size);
		assertTrue(pi2.getMaximumSize() == size);
		assertTrue(pi3.getMaximumSize() == size);
	}
	
	@Test
	public void testPlayerInventorySize() {
		PlayerInventory pi1 = new PlayerInventory();
		
		LightGrenade lg1 = new LightGrenade();
		LightGrenade lg2 = new LightGrenade();
		LightGrenade lg3 = new LightGrenade();

		Item it1 = new IdentityDisc();
		Item it2 = new ChargedIdentityDisc();
		Item it3 = new IdentityDisc();

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

		Item it1 = new IdentityDisc();
		Item it2 = new ChargedIdentityDisc();
		Item it3 = new IdentityDisc();
		Item it4 = new IdentityDisc();

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

	@Test(expected=IllegalStateException.class)
	public void testAddTeleport(){
		PlayerInventory pi1 = new PlayerInventory();
		pi1.addItem(new Teleport());
	}
	
	@Test
	public void testAddFlag(){
		PlayerInventory plInv = new PlayerInventory();
		Flag flag = new Flag();
		plInv.addItem(flag);
		assertTrue(plInv.hasItem(flag));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAddMoreFlags(){
		PlayerInventory plInv = new PlayerInventory();
		Flag flag = new Flag();
		plInv.addItem(flag);
		assertTrue(plInv.hasItem(flag));
		
		plInv.addItem(new Flag());
	}
}
